@echo off
setlocal
powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0front-dev.ps1" %*
exit /b %ERRORLEVEL%
