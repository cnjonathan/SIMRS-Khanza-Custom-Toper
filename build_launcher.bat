@echo off
echo [Build] Mengkompilasi KhanzaLauncher.java...

if not exist "build_launcher" mkdir build_launcher

javac -encoding UTF-8 -d build_launcher src/launcher/KhanzaLauncher.java

if %ERRORLEVEL% NEQ 0 (
    echo [Error] Gagal mengkompilasi KhanzaLauncher.java!
    exit /b %ERRORLEVEL%
)

echo [Build] Membuat file executable KhanzaLauncher.jar...
jar cfe KhanzaLauncher.jar launcher.KhanzaLauncher -C build_launcher .

if %ERRORLEVEL% EQU 0 (
    echo [Sukses] File KhanzaLauncher.jar berhasil dibuat!
) else (
    echo [Error] Gagal membuat file KhanzaLauncher.jar!
)
