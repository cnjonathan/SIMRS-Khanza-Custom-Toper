@echo off
title Builder SIMRSKhanza.jar (Tanpa NetBeans)
echo ====================================================================
echo   BUILDER SIMRS KHANZA JAR - RSUD KARTINI KARANGANYAR
echo ====================================================================
echo.

cd /d "%~dp0"

set "JDK_BIN=C:\Program Files\BellSoft\LibericaJDK-15-Full\bin"
set "JAVAC=%JDK_BIN%\javac.exe"
set "JAR_CMD=%JDK_BIN%\jar.exe"

if not exist "%JAVAC%" (
    echo [ERROR] JDK javac.exe tidak ditemukan di: "%JAVAC%"
    pause
    exit /b 1
)

echo [1/4] Mencari seluruh file sumber Java di folder src...
powershell -NoProfile -Command "Get-ChildItem -Path src -Recurse -Filter '*.java' | ForEach-Object { '\"' + $_.FullName.Replace('\', '/') + '\"' } | Set-Content sources.txt"

if not exist build\classes (
    mkdir build\classes
)

echo [2/4] Mengompilasi seluruh kode sumber Java (mohon tunggu)...
"%JAVAC%" -encoding UTF-8 -cp "lib/*;build/classes" -d build/classes @sources.txt
if %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERROR] Terjadi kesalahan kompilasi Java!
    if exist sources.txt del sources.txt
    pause
    exit /b 1
)
if exist sources.txt del sources.txt

echo [3/4] Menyalin aset gambar dan resource dari src ke build/classes...
powershell -NoProfile -Command "Get-ChildItem -Path src -Recurse -Exclude '*.java' | Where-Object { -not $_.PSIsContainer } | ForEach-Object { $srcRel = $_.FullName.Substring((Resolve-Path src).Path.Length); $dest = (Resolve-Path build\classes).Path + $srcRel; $destDir = Split-Path $dest; if (-not (Test-Path $destDir)) { New-Item -ItemType Directory -Path $destDir -Force | Out-Null }; Copy-Item -Path $_.FullName -Destination $dest -Force }"

echo [4/4] Mengemasi build/classes menjadi SIMRSKhanza.jar...
"%JAR_CMD%" cfm SIMRSKhanza.jar MANIFEST.MF -C build/classes .
if %ERRORLEVEL% EQU 0 (
    echo.
    echo [SUKSES] SIMRSKhanza.jar berhasil dibuat!
    
    if exist "C:\Users\Christopher Jonathan\Documents\Project\Dist Khanza" (
        echo [INFO] Menyalin SIMRSKhanza.jar ke folder Dist Khanza...
        copy /Y SIMRSKhanza.jar "C:\Users\Christopher Jonathan\Documents\Project\Dist Khanza\SIMRSKhanza.jar" >nul
        echo [SUKSES] File di Dist Khanza berhasil diperbarui!
    )
) else (
    echo.
    echo [ERROR] Gagal membuat file SIMRSKhanza.jar!
)

echo.
echo ====================================================================
echo   PROSES KOMPILASI SELESAI!
echo ====================================================================
pause
