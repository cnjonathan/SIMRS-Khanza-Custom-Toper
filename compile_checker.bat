@echo off
set "JAVA_HOME=C:\Program Files\BellSoft\LibericaJDK-15-Full"
echo [Compiling] AutoUpdaterChecker.java, koneksiDB.java, frmUtama.java...

"%JAVA_HOME%\bin\javac.exe" -cp "src;build/classes;lib/*" -sourcepath "" -d "build/classes" "src/fungsi/koneksiDB.java" "src/simrskhanza/AutoUpdaterChecker.java" "src/simrskhanza/frmUtama.java"

if %ERRORLEVEL% EQU 0 (
    echo [OK] Compiled successfully!
    
    powershell -NoProfile -Command "Set-Location 'build\classes'; & '%JAVA_HOME%\bin\jar.exe' uf '..\..\dist\SIMRSKhanza.jar' (Get-ChildItem -Path 'simrskhanza', 'fungsi' -Recurse -File | Where-Object { $_.Name -match '^(AutoUpdaterChecker|koneksiDB|frmUtama).*\.class$' } | ForEach-Object { $_.FullName.Substring((Resolve-Path .).Path.Length + 1).Replace('\', '/') })"
    
    copy /y "dist\SIMRSKhanza.jar" "SIMRSKhanza.jar"
    copy /y "dist\SIMRSKhanza.jar" "C:\Users\Christopher Jonathan\Documents\Project\Dist Khanza\SIMRSKhanza.jar"
    if exist "C:\SIMRSKhanzaStaging" (
        copy /y "dist\SIMRSKhanza.jar" "C:\SIMRSKhanzaStaging\SIMRSKhanza.jar"
    )
    
    echo [OK] SIMRSKhanza.jar updated everywhere!
) else (
    echo [FAILED] Compilation failed.
)
