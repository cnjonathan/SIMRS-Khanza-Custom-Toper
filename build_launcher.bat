@echo off
echo [Build] Mengkompilasi KhanzaLauncher.java...

if not exist "build_launcher" mkdir build_launcher

javac -source 8 -target 8 -encoding UTF-8 -d build_launcher src/launcher/KhanzaLauncher.java

if %ERRORLEVEL% NEQ 0 (
    echo [Error] Gagal mengkompilasi KhanzaLauncher.java!
    exit /b %ERRORLEVEL%
)

echo [Build] Membuat file executable KhanzaLauncher.jar...
jar cfe KhanzaLauncher.jar launcher.KhanzaLauncher -C build_launcher .

if %ERRORLEVEL% EQU 0 (
    echo [Sukses] File KhanzaLauncher.jar berhasil dibuat!
    
    if exist "..\Dist Khanza" (
        copy /Y KhanzaLauncher.jar "..\Dist Khanza\KhanzaLauncher.jar" >nul
        echo [Sync] Menyalin KhanzaLauncher.jar ke Dist Khanza...
    )
    
    if exist "C:\SIMRSKhanzaStaging" (
        copy /Y KhanzaLauncher.jar "C:\SIMRSKhanzaStaging\KhanzaLauncher.jar" >nul
        echo [Sync] Menyalin KhanzaLauncher.jar ke C:\SIMRSKhanzaStaging...
    )
) else (
    echo [Error] Gagal membuat file KhanzaLauncher.jar!
)
