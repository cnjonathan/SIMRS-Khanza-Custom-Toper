@echo off
set "JAVA_HOME=C:\Program Files\BellSoft\LibericaJDK-15-Full"
echo [Compiling] AutoUpdaterChecker.java & frmUtama.java...
"%JAVA_HOME%\bin\javac.exe" -cp "src;build/classes;lib/*" -sourcepath "" -d "build/classes" "src/simrskhanza/AutoUpdaterChecker.java" "src/simrskhanza/frmUtama.java"
if %ERRORLEVEL% EQU 0 (
    echo [OK] Compiled successfully!
    "%JAVA_HOME%\bin\jar.exe" uf dist/SIMRSKhanza.jar -C build/classes simrskhanza/AutoUpdaterChecker.class -C build/classes simrskhanza/frmUtama.class
    copy /y "dist\SIMRSKhanza.jar" "SIMRSKhanza.jar"
    echo [OK] SIMRSKhanza.jar updated!
) else (
    echo [FAILED] Compilation failed.
)
