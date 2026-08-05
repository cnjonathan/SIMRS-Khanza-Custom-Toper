@echo off
title SIMRS Khanza - RSUD Kartini Karanganyar

cd /d "%~dp0"

:: Path ke Liberica JDK 15 Full (javaw tanpa CMD)
set "JAVA_EXE=C:\Program Files\BellSoft\LibericaJDK-15-Full\bin\javaw.exe"

if not exist "%JAVA_EXE%" (
    set "JAVA_EXE=javaw"
)

:: Menjalankan KhanzaLauncher.jar secara silent tanpa jendela CMD
start "" "%JAVA_EXE%" -Xss2m -Xms32m -Xmx1024m -XX:MetaspaceSize=32m -XX:MaxMetaspaceSize=2048m -jar KhanzaLauncher.jar
exit
