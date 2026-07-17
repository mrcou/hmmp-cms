#Requires -Version 5.1
param(
    [Parameter(Position = 0)]
    [ValidateSet('start', 'stop', 'restart', 'status', 'logs')]
    [string]$Command
)

$ErrorActionPreference = 'Stop'

$FrontDir = 'hmmp-vben'
$FrontPort = $env:FRONT_PORT
$AppHome = Split-Path -Parent $MyInvocation.MyCommand.Path
$LogDir = Join-Path $AppHome 'logs'
$LogPath = Join-Path $LogDir 'front-dev.log'
$FrontHome = Join-Path $AppHome $FrontDir

function Show-Usage {
    Write-Host "Usage: front-dev.ps1 {start|stop|restart|status|logs}"
    Write-Host "       front-dev.cmd  {start|stop|restart|status|logs}"
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
        [int]$TimeoutSec = 60
    )
    Write-Step "Waiting for frontend ready on port $Port (timeout ${TimeoutSec}s). Streaming log:"
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
            if ($tail -match 'error when starting|EADDRINUSE|Error:') {
                Write-NewLogContent -LogFile $LogFile -Offset ([ref]$offset)
                Write-Host "---------- log end ----------"
                return $false
            }
        }
        Start-Sleep -Seconds 1
        $elapsed++
        if (($elapsed % 10) -eq 0) {
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
        [int]$TimeoutSec = 20
    )
    Write-Step "Waiting for stop complete (port $Port free, processes gone)..."
    $elapsed = 0
    while ($elapsed -lt $TimeoutSec) {
        $pids = Merge-Pids (Get-DevPids) (Get-PortPids)
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

function Load-Port {
    if ($FrontPort) {
        return
    }

    $envFile = Join-Path $FrontHome '.env.development'
    if (Test-Path -LiteralPath $envFile) {
        $line = Get-Content -LiteralPath $envFile -ErrorAction SilentlyContinue |
            Where-Object { $_ -match '^\s*VITE_PORT\s*=' } |
            Select-Object -First 1
        if ($line -match '^\s*VITE_PORT\s*=\s*(.+)\s*$') {
            $script:FrontPort = $Matches[1].Trim()
        }
    }

    if (-not $script:FrontPort) {
        $script:FrontPort = '5999'
    }
}

function Get-PortPids {
    Load-Port
    $pids = New-Object System.Collections.Generic.List[int]
    try {
        $listeners = Get-NetTCPConnection -LocalPort ([int]$script:FrontPort) -State Listen -ErrorAction SilentlyContinue
        foreach ($conn in $listeners) {
            if ($conn.OwningProcess -gt 0) {
                $pids.Add([int]$conn.OwningProcess)
            }
        }
    } catch {
        $lines = netstat -ano -p tcp 2>$null
        foreach ($line in $lines) {
            if ($line -match "[:.]$($script:FrontPort)\s+\S+\s+LISTENING\s+(\d+)\s*$") {
                $pids.Add([int]$Matches[1])
            }
        }
    }
    return $pids
}

function Get-DevPids {
    $pids = New-Object System.Collections.Generic.List[int]
    Get-CimInstance Win32_Process -ErrorAction SilentlyContinue | ForEach-Object {
        $cmd = $_.CommandLine
        if (-not $cmd) { return }
        $inFront = $cmd -match [regex]::Escape($FrontDir)
        $isDev = ($cmd -match 'pnpm') -or ($cmd -match 'vite')
        if ($inFront -and $isDev) {
            $pids.Add([int]$_.ProcessId)
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
        [int]$TimeoutSec = 15
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
    $left = Wait-ProcessExit -ProcessIds $procIds -TimeoutSec 15
    if ($left.Count -gt 0) {
        Write-Step "Force kill remaining process. pid: $($left -join ' ')"
        foreach ($procId in $left) {
            cmd /c "taskkill /PID $procId /T /F >nul 2>&1"
        }
    }
}

function Clear-HistoryProcesses {
    Load-Port
    $pids = Merge-Pids (Get-DevPids) (Get-PortPids)
    if ($pids.Count -eq 0) {
        Write-Step "No frontend dev process found for $FrontDir or port $($script:FrontPort)."
        return
    }
    Stop-ProcessTree -ProcessIds $pids -Label 'Clean frontend dev process.'
    if (Wait-ServiceStopped -Port ([int]$script:FrontPort) -TimeoutSec 15) {
        Write-Ok "History process cleaned. port $($script:FrontPort) is free."
    } else {
        Write-Fail "History process may still remain. pid: $((Merge-Pids (Get-DevPids) (Get-PortPids)) -join ' ')"
    }
}

function Require-FrontendEnv {
    $packageJson = Join-Path $FrontHome 'package.json'
    if (-not (Test-Path -LiteralPath $packageJson)) {
        Write-Fail "$FrontDir/package.json not found."
        Write-Error "$FrontDir/package.json not found."
        exit 1
    }

    $nodeModules = Join-Path $FrontHome 'node_modules'
    if (-not (Test-Path -LiteralPath $nodeModules)) {
        Write-Fail "$FrontDir/node_modules not found. Run dependency installation before starting dev server."
        Write-Error "$FrontDir/node_modules not found. Run dependency installation before starting dev server."
        exit 1
    }

    $viteCmd = Join-Path $FrontHome 'node_modules\.bin\vite.cmd'
    $viteJs = Join-Path $FrontHome 'node_modules\vite\bin\vite.js'
    if (-not (Test-Path -LiteralPath $viteCmd) -and -not (Test-Path -LiteralPath $viteJs)) {
        Write-Fail "$FrontDir/node_modules/.bin/vite not found."
        Write-Error "$FrontDir/node_modules/.bin/vite not found."
        exit 1
    }
}

function Ensure-LogDir {
    if (-not (Test-Path -LiteralPath $LogDir)) {
        New-Item -ItemType Directory -Path $LogDir | Out-Null
    }
}

function Start-Front {
    Write-Step "Starting $FrontDir dev server..."
    Load-Port
    Require-FrontendEnv
    Clear-HistoryProcesses
    Ensure-LogDir

    $viteCmd = Join-Path $FrontHome 'node_modules\.bin\vite.cmd'
    $viteJs = Join-Path $FrontHome 'node_modules\vite\bin\vite.js'
    $argsText = "--mode development --host 0.0.0.0 --port $($script:FrontPort)"

    if (Test-Path -LiteralPath $viteCmd) {
        $cmdLine = "call `"$viteCmd`" $argsText >> `"$LogPath`" 2>&1"
        Write-Step "Launch command: $viteCmd $argsText"
    } else {
        $cmdLine = "node `"$viteJs`" $argsText >> `"$LogPath`" 2>&1"
        Write-Step "Launch command: node $viteJs $argsText"
    }

    Write-Step "Log file: $LogPath"
    if (Test-Path -LiteralPath $LogPath) {
        Clear-Content -LiteralPath $LogPath -ErrorAction SilentlyContinue
    }

    $proc = Start-Process -FilePath 'cmd.exe' `
        -ArgumentList @('/c', $cmdLine) `
        -WorkingDirectory $FrontHome `
        -WindowStyle Hidden `
        -PassThru

    Write-Step "Launcher pid: $($proc.Id). Waiting until port $($script:FrontPort) is ready..."

    if (Wait-ServiceReady -Port ([int]$script:FrontPort) -LogFile $LogPath -TimeoutSec 60) {
        $pids = Merge-Pids (Get-DevPids) (Get-PortPids)
        Write-Ok "Start $FrontDir COMPLETE. port: $($script:FrontPort) pid: $($pids -join ' ')"
        Write-Ok "Log: $LogPath"
    } else {
        Write-Fail "Start $FrontDir FAILED or timeout. port $($script:FrontPort) is not listening."
        Write-Fail "Check log: $LogPath"
        if (Test-Path -LiteralPath $LogPath) {
            Write-Host "---------- last 40 log lines ----------"
            Get-Content -LiteralPath $LogPath -Tail 40
            Write-Host "---------- end ----------"
        }
        exit 1
    }
}

function Stop-Front {
    Write-Step "Stopping $FrontDir ..."
    Load-Port
    $pids = Merge-Pids (Get-DevPids) (Get-PortPids)
    if ($pids.Count -eq 0 -and -not (Test-PortListening -Port ([int]$script:FrontPort))) {
        Write-Ok "$FrontDir already stopped. port $($script:FrontPort) is free."
        return
    }
    Stop-ProcessTree -ProcessIds $pids -Label "Stop $FrontDir dev."
    if (Wait-ServiceStopped -Port ([int]$script:FrontPort) -TimeoutSec 20) {
        Write-Ok "Stop $FrontDir COMPLETE. port $($script:FrontPort) is free."
    } else {
        $left = Merge-Pids (Get-DevPids) (Get-PortPids)
        Write-Fail "Stop $FrontDir INCOMPLETE. remaining pid: $($left -join ' ') listening=$(Test-PortListening -Port ([int]$script:FrontPort))"
        exit 1
    }
}

function Show-Status {
    Load-Port
    $pids = Merge-Pids (Get-DevPids) (Get-PortPids)
    $listening = Test-PortListening -Port ([int]$script:FrontPort)
    if ($pids.Count -gt 0 -or $listening) {
        Write-Ok "$FrontDir is running. pid: $($pids -join ' ') port: $($script:FrontPort) listening=$listening"
    } else {
        Write-Step "$FrontDir is not running. port: $($script:FrontPort) listening=$listening"
    }
}

function Show-Logs {
    if (-not (Test-Path -LiteralPath $LogPath)) {
        Write-Fail "$LogPath not found."
        Write-Error "$LogPath not found."
        exit 1
    }
    Write-Step "Tailing $LogPath (Ctrl+C to stop)..."
    Get-Content -LiteralPath $LogPath -Wait -Tail 50
}

if (-not $Command) {
    Show-Usage
    exit 1
}

switch ($Command) {
    'start' { Start-Front }
    'stop' { Stop-Front }
    'restart' { Start-Front }
    'status' { Show-Status }
    'logs' { Show-Logs }
}
