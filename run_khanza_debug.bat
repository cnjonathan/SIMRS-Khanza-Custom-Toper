@echo off
title SIMRS Khanza - IT Debug Mode (Live Console Log)

:: Path ke Liberica JDK 15 Full (java.exe dengan jendela CMD)
set "JAVA_EXE=C:\Program Files\BellSoft\LibericaJDK-15-Full\bin\java.exe"

if not exist "%JAVA_EXE%" (
    set "JAVA_EXE=java"
)

echo ====================================================================
echo   SIMRS KHANZA - IT DEBUG MODE (MONITORING CONSOLE STACK TRACE LOG)
echo ====================================================================

"%JAVA_EXE%" -Xss2m -Xms32m -Xmx1024m -XX:MetaspaceSize=32m -XX:MaxMetaspaceSize=2048m -jar KhanzaLauncher.jar
pause
