#Requires -Version 5.1
param(
    [Parameter(Position = 0)]
    [ValidateSet('start', 'dev', 'stop', 'restart', 'status', 'logs')]
    [string]$Command
)

$ErrorActionPreference = 'Stop'

$AppName = 'hmmp-admin.jar'
$AppModule = 'hmmp-admin'
$AppPort = if ($env:APP_PORT) { $env:APP_PORT } else { '8080' }
$AppArgs = if ($env:APP_ARGS) { $env:APP_ARGS } else { "--server.port=$AppPort" }
$MavenCmd = if ($env:MAVEN_CMD) { $env:MAVEN_CMD } else { 'mvn' }
$AppHome = Split-Path -Parent $MyInvocation.MyCommand.Path
$LogDir = Join-Path $AppHome 'logs'
$LogPath = Join-Path $LogDir "$AppName.log"
$DevLogPath = Join-Path $LogDir 'dev.log'
# Use forward slashes so -Xlog file=...:options is not broken by Windows drive letters.
$GcLogPath = ((Join-Path $LogDir 'gc.log') -replace '\\', '/')

$JvmOpts = @(
    "-Dname=$AppName"
    '-Duser.timezone=Asia/Shanghai'
    '-Xms512m'
    '-Xmx1024m'
    '-XX:MetaspaceSize=128m'
    '-XX:MaxMetaspaceSize=512m'
    '-XX:+HeapDumpOnOutOfMemoryError'
    "-Xlog:gc*:file=$GcLogPath`:time,uptime,level,tags:filecount=5,filesize=20M"
    '-XX:NewRatio=1'
    '-XX:SurvivorRatio=30'
    '-XX:+UseParallelGC'
)

function Show-Usage {
    Write-Host "Usage: server.ps1 {start|dev|stop|restart|status|logs}"
    Write-Host "       server.cmd  {start|dev|stop|restart|status|logs}"
}

function Write-Step {
    param([string]$Message)
    Write-Host "[INFO] $Message"
}

function Write-Ok {
    param([string]$Message)
    Write-Host "[OK]   $Message"
}

function Write-Fail {
    param([string]$Message)
    Write-Host "[FAIL] $Message"
}

function Test-PortListening {
    param([int]$Port)
    try {
        $listeners = Get-NetTCPConnection -LocalPort $Port -State Listen -ErrorAction SilentlyContinue
        return [bool]($listeners)
    } catch {
        $lines = netstat -ano -p tcp 2>$null
        foreach ($line in $lines) {
            if ($line -match "[:.]$Port\s+\S+\s+LISTENING\s+\d+\s*$") {
                return $true
            }
        }
        return $false
    }
}

function Write-NewLogContent {
    param(
        [string]$LogFile,
        [ref]$Offset
    )
    if (-not (Test-Path -LiteralPath $LogFile)) {
        return
    }
    try {
        $fs = [System.IO.File]::Open($LogFile, [System.IO.FileMode]::Open, [System.IO.FileAccess]::Read, [System.IO.FileShare]::ReadWrite)
        try {
            if ($fs.Length -lt $Offset.Value) {
                $Offset.Value = 0
            }
            if ($fs.Length -le $Offset.Value) {
                return
            }
            $fs.Position = $Offset.Value
            $reader = New-Object System.IO.StreamReader($fs, [System.Text.Encoding]::UTF8, $true, 1024, $true)
            $chunk = $reader.ReadToEnd()
            if ($chunk) {
                # Avoid splitting mid-line noise: print as-is
                [Console]::Write($chunk)
                if (-not $chunk.EndsWith("`n") -and -not $chunk.EndsWith("`r")) {
                    Write-Host ''
                }
            }
            $Offset.Value = $fs.Position
        } finally {
            $fs.Close()
        }
    } catch {
        # ignore transient file lock
    }
}

function Wait-ServiceReady {
    param(
        [int]$Port,
        [string]$LogFile,
        [int]$TimeoutSec = 180
    )
    Write-Step "Waiting for service ready on port $Port (timeout ${TimeoutSec}s). Streaming log:"
    Write-Host "---------- log begin ($LogFile) ----------"
    $offset = [long]0
    if (Test-Path -LiteralPath $LogFile) {
        $offset = [long](Get-Item -LiteralPath $LogFile).Length
    }
    $elapsed = 0
    while ($elapsed -lt $TimeoutSec) {
        Write-NewLogContent -LogFile $LogFile -Offset ([ref]$offset)
        if (Test-PortListening -Port $Port) {
            Write-NewLogContent -LogFile $LogFile -Offset ([ref]$offset)
            Write-Host "---------- log end ----------"
            return $true
        }
        if (Test-Path -LiteralPath $LogFile) {
            $tail = Get-Content -LiteralPath $LogFile -Tail 40 -ErrorAction SilentlyContinue | Out-String
            if ($tail -match 'BUILD FAILURE|APPLICATION FAILED TO START|Unable to find a suitable main class') {
                Write-NewLogContent -LogFile $LogFile -Offset ([ref]$offset)
                Write-Host "---------- log end ----------"
                return $false
            }
        }
        Start-Sleep -Seconds 1
        $elapsed++
        if (($elapsed % 15) -eq 0) {
            Write-Step "still waiting... ${elapsed}s / ${TimeoutSec}s"
        }
    }
    Write-NewLogContent -LogFile $LogFile -Offset ([ref]$offset)
    Write-Host "---------- log end ----------"
    return (Test-PortListening -Port $Port)
}

function Wait-ServiceStopped {
    param(
        [int]$Port,
        [int]$TimeoutSec = 30
    )
    Write-Step "Waiting for stop complete (port $Port free, processes gone)..."
    $elapsed = 0
    while ($elapsed -lt $TimeoutSec) {
        $pids = Merge-Pids (Get-AppPids) (Get-PortPids)
        $listening = Test-PortListening -Port $Port
        if ($pids.Count -eq 0 -and -not $listening) {
            return $true
        }
        if (($elapsed % 5) -eq 0 -and $elapsed -gt 0) {
            Write-Step "still stopping... pid: $($pids -join ' ') listening=$listening"
        }
        Start-Sleep -Seconds 1
        $elapsed++
    }
    return $false
}

function Test-Java17 {
    param([string]$JavaExe)
    if (-not $JavaExe -or -not (Test-Path -LiteralPath $JavaExe)) {
        return $false
    }
    # java -version writes to stderr; with $ErrorActionPreference=Stop that becomes a terminating error.
    $prevEap = $ErrorActionPreference
    $ErrorActionPreference = 'Continue'
    try {
        $output = & $JavaExe -version 2>&1 | ForEach-Object { "$_" } | Out-String
        return [bool]($output -match 'version\s+"17(\.|")')
    } catch {
        return $false
    } finally {
        $ErrorActionPreference = $prevEap
    }
}

function Find-Java17 {
    if ($env:JAVA_CMD -and (Test-Java17 $env:JAVA_CMD)) {
        return $env:JAVA_CMD
    }

    if ($env:JAVA_HOME) {
        $candidate = Join-Path $env:JAVA_HOME 'bin\java.exe'
        if (Test-Java17 $candidate) {
            return $candidate
        }
    }

    $javaOnPath = Get-Command java -ErrorAction SilentlyContinue
    if ($javaOnPath -and (Test-Java17 $javaOnPath.Source)) {
        return $javaOnPath.Source
    }

    $searchRoots = @(
        "$env:ProgramFiles\Java"
        "$env:ProgramFiles\Eclipse Adoptium"
        "$env:ProgramFiles\Microsoft"
        "$env:ProgramFiles\Amazon Corretto"
        "${env:ProgramFiles(x86)}\Java"
        'C:\Java'
        'C:\jdk'
    ) | Where-Object { $_ -and (Test-Path -LiteralPath $_) }

    foreach ($root in $searchRoots) {
        $candidates = Get-ChildItem -Path $root -Recurse -Filter 'java.exe' -ErrorAction SilentlyContinue |
            Where-Object { $_.FullName -match '\\bin\\java\.exe$' } |
            Select-Object -ExpandProperty FullName
        foreach ($candidate in $candidates) {
            if (Test-Java17 $candidate) {
                return $candidate
            }
        }
    }

    return $null
}

function Require-Java17 {
    $script:JavaBin = Find-Java17
    if (-not $script:JavaBin) {
        Write-Error 'JDK 17 not found. Set JAVA_HOME to JDK 17 or set JAVA_CMD to the JDK 17 java.exe.'
        exit 1
    }
}

function Get-AppPids {
    $pids = New-Object System.Collections.Generic.List[int]
    Get-CimInstance Win32_Process -ErrorAction SilentlyContinue | ForEach-Object {
        $cmd = $_.CommandLine
        if (-not $cmd) { return }
        $matchApp = $cmd -match [regex]::Escape($AppName)
        $matchModule = $cmd -match [regex]::Escape($AppModule)
        $matchMaven = ($cmd -match 'mvn(\.cmd)?') -or ($cmd -match 'spring-boot:run') -or ($cmd -match '-DskipTests')
        if ($matchApp -or ($matchModule -and $matchMaven)) {
            $pids.Add([int]$_.ProcessId)
        }
    }
    return $pids
}

function Get-PortPids {
    $pids = New-Object System.Collections.Generic.List[int]
    try {
        $listeners = Get-NetTCPConnection -LocalPort ([int]$AppPort) -State Listen -ErrorAction SilentlyContinue
        foreach ($conn in $listeners) {
            if ($conn.OwningProcess -gt 0) {
                $pids.Add([int]$conn.OwningProcess)
            }
        }
    } catch {
        $lines = netstat -ano -p tcp 2>$null
        foreach ($line in $lines) {
            if ($line -match "[:.]$AppPort\s+\S+\s+LISTENING\s+(\d+)\s*$") {
                $pids.Add([int]$Matches[1])
            }
        }
    }
    return $pids
}

function Merge-Pids {
    param([int[]]$Left, [int[]]$Right)
    $set = New-Object 'System.Collections.Generic.HashSet[int]'
    foreach ($procId in @($Left) + @($Right)) {
        if ($procId -gt 0) { [void]$set.Add($procId) }
    }
    return @($set)
}

function Wait-ProcessExit {
    param(
        [int[]]$ProcessIds,
        [int]$TimeoutSec = 20
    )
    $remaining = @($ProcessIds | Where-Object { $_ -gt 0 })
    $elapsed = 0
    while ($elapsed -lt $TimeoutSec) {
        $stillRunning = @()
        foreach ($procId in $remaining) {
            if (Get-Process -Id $procId -ErrorAction SilentlyContinue) {
                $stillRunning += $procId
            }
        }
        if ($stillRunning.Count -eq 0) {
            return @()
        }
        Start-Sleep -Seconds 1
        $elapsed++
        $remaining = $stillRunning
    }
    return $remaining
}

function Stop-ProcessTree {
    param([int[]]$ProcessIds, [string]$Label)
    $procIds = @(Merge-Pids $ProcessIds @())
    if ($procIds.Count -eq 0) {
        return
    }
    Write-Step "$Label pid: $($procIds -join ' ')"
    foreach ($procId in $procIds) {
        Write-Step "killing pid $procId ..."
        Stop-Process -Id $procId -Force -ErrorAction SilentlyContinue
        cmd /c "taskkill /PID $procId /T /F >nul 2>&1"
    }
    $left = Wait-ProcessExit -ProcessIds $procIds -TimeoutSec 20
    if ($left.Count -gt 0) {
        Write-Step "Force kill remaining process. pid: $($left -join ' ')"
        foreach ($procId in $left) {
            cmd /c "taskkill /PID $procId /T /F >nul 2>&1"
        }
    }
}

function Clear-HistoryProcesses {
    $pids = Merge-Pids (Get-AppPids) (Get-PortPids)
    if ($pids.Count -eq 0) {
        Write-Step "No history process found for $AppModule or port $AppPort."
        return
    }
    Stop-ProcessTree -ProcessIds $pids -Label 'Clean history process.'
    if (Wait-ServiceStopped -Port ([int]$AppPort) -TimeoutSec 20) {
        Write-Ok "History process cleaned. port $AppPort is free."
    } else {
        Write-Fail "History process may still remain. pid: $((Merge-Pids (Get-AppPids) (Get-PortPids)) -join ' ')"
    }
}

function Ensure-LogDir {
    if (-not (Test-Path -LiteralPath $LogDir)) {
        New-Item -ItemType Directory -Path $LogDir | Out-Null
    }
}

function Start-Dev {
    Write-Step "Starting $AppModule in dev mode..."
    Require-Java17
    Write-Step "Using java: $script:JavaBin"
    Clear-HistoryProcesses
    Ensure-LogDir
    Set-Location $AppHome

    $javaHome = Split-Path (Split-Path $script:JavaBin -Parent) -Parent
    $javaBinDir = Split-Path $script:JavaBin -Parent

    $maven = Get-Command $MavenCmd -ErrorAction SilentlyContinue
    if (-not $maven) {
        Write-Fail "Maven command not found: $MavenCmd"
        Write-Error "Maven command not found: $MavenCmd"
        exit 1
    }
    Write-Step "Using maven: $($maven.Source)"

    # Root pom also declares spring-boot-maven-plugin. Using -am with spring-boot:run
    # pulls the parent into the reactor and fails with "Unable to find a suitable main class".
    # Build+install dependencies first (-am install), then run only on hmmp-admin.
    # Must use install (not package): spring-boot:run resolves sibling modules from ~/.m2.
    $jvmArgsJoined = ($JvmOpts -join ' ')
    $mavenQuoted = '"{0}"' -f $maven.Source
    $buildCmd = "call $mavenQuoted -pl $AppModule -am -DskipTests install"
    $runCmd = "call $mavenQuoted -pl $AppModule spring-boot:run `"-Dspring-boot.run.jvmArguments=$jvmArgsJoined`" `"-Dspring-boot.run.arguments=$AppArgs`""
    $cmdLine = "set `"JAVA_HOME=$javaHome`" && set `"PATH=$javaBinDir;%PATH%`" && ($buildCmd && $runCmd) >> `"$DevLogPath`" 2>&1"

    Write-Step "Log file: $DevLogPath"
    Write-Step "Launch command: $buildCmd && $runCmd"
    # Truncate previous log so streamed output is for this run only
    if (Test-Path -LiteralPath $DevLogPath) {
        Clear-Content -LiteralPath $DevLogPath -ErrorAction SilentlyContinue
    }

    $proc = Start-Process -FilePath 'cmd.exe' `
        -ArgumentList @('/c', $cmdLine) `
        -WorkingDirectory $AppHome `
        -WindowStyle Hidden `
        -PassThru
    Write-Step "Launcher pid: $($proc.Id). Waiting until port $AppPort is ready..."

    if (Wait-ServiceReady -Port ([int]$AppPort) -LogFile $DevLogPath -TimeoutSec 180) {
        $pids = Merge-Pids (Get-AppPids) (Get-PortPids)
        Write-Ok "Start $AppModule COMPLETE. port: $AppPort pid: $($pids -join ' ') java: $($script:JavaBin)"
        Write-Ok "Log: $DevLogPath"
    } else {
        Write-Fail "Start $AppModule FAILED or timeout. port $AppPort is not listening."
        Write-Fail "Check log: $DevLogPath"
        if (Test-Path -LiteralPath $DevLogPath) {
            Write-Host "---------- last 40 log lines ----------"
            Get-Content -LiteralPath $DevLogPath -Tail 40
            Write-Host "---------- end ----------"
        }
        exit 1
    }
}

function Stop-App {
    Write-Step "Stopping $AppModule ..."
    $pids = Merge-Pids (Get-AppPids) (Get-PortPids)
    if ($pids.Count -eq 0 -and -not (Test-PortListening -Port ([int]$AppPort))) {
        Write-Ok "$AppModule already stopped. port $AppPort is free."
        return
    }
    Stop-ProcessTree -ProcessIds $pids -Label "Stop $AppModule."
    if (Wait-ServiceStopped -Port ([int]$AppPort) -TimeoutSec 30) {
        Write-Ok "Stop $AppModule COMPLETE. port $AppPort is free."
    } else {
        $left = Merge-Pids (Get-AppPids) (Get-PortPids)
        Write-Fail "Stop $AppModule INCOMPLETE. remaining pid: $($left -join ' ') listening=$(Test-PortListening -Port ([int]$AppPort))"
        exit 1
    }
}

function Show-Status {
    $pids = Merge-Pids (Get-AppPids) (Get-PortPids)
    $listening = Test-PortListening -Port ([int]$AppPort)
    if ($pids.Count -gt 0 -or $listening) {
        Write-Ok "$AppModule is running. pid: $($pids -join ' ') port: $AppPort listening=$listening"
    } else {
        Write-Step "$AppModule is not running. port: $AppPort listening=$listening"
    }
}

function Show-Logs {
    if (-not (Test-Path -LiteralPath $DevLogPath)) {
        Write-Fail "$DevLogPath not found."
        exit 1
    }
    Write-Step "Tailing $DevLogPath (Ctrl+C to stop)..."
    Get-Content -LiteralPath $DevLogPath -Wait -Tail 80
}

if (-not $Command) {
    Show-Usage
    exit 1
}

switch ($Command) {
    'start' { Start-Dev }
    'dev' { Start-Dev }
    'stop' { Stop-App }
    'restart' { Start-Dev }
    'status' { Show-Status }
    'logs' { Show-Logs }
}
