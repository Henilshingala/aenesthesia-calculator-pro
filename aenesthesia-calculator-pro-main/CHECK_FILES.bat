@echo off
echo ========================================
echo   ANESTHESIA CALCULATOR PRO
echo   FILE VERIFICATION SCRIPT
echo ========================================
echo.

echo Checking project structure...
echo.

echo [BUILD FILES]
if exist "build.gradle" (echo ✓ build.gradle) else (echo ✗ build.gradle MISSING)
if exist "settings.gradle" (echo ✓ settings.gradle) else (echo ✗ settings.gradle MISSING)
if exist "gradle.properties" (echo ✓ gradle.properties) else (echo ✗ gradle.properties MISSING)
if exist "app\build.gradle" (echo ✓ app\build.gradle) else (echo ✗ app\build.gradle MISSING)
if exist "app\proguard-rules.pro" (echo ✓ app\proguard-rules.pro) else (echo ✗ app\proguard-rules.pro MISSING)
echo.

echo [MANIFEST]
if exist "app\src\main\AndroidManifest.xml" (echo ✓ AndroidManifest.xml) else (echo ✗ AndroidManifest.xml MISSING)
echo.

echo [JAVA FILES]
if exist "app\src\main\java\com\anesthesiacalculator\pro\Application.java" (echo ✓ Application.java) else (echo ✗ Application.java MISSING)
if exist "app\src\main\java\com\anesthesiacalculator\pro\models\Patient.java" (echo ✓ Patient.java) else (echo ✗ Patient.java MISSING)
if exist "app\src\main\java\com\anesthesiacalculator\pro\models\Drug.java" (echo ✓ Drug.java) else (echo ✗ Drug.java MISSING)
if exist "app\src\main\java\com\anesthesiacalculator\pro\models\DosageResult.java" (echo ✓ DosageResult.java) else (echo ✗ DosageResult.java MISSING)
if exist "app\src\main\java\com\anesthesiacalculator\pro\activities\MainActivity.java" (echo ✓ MainActivity.java) else (echo ✗ MainActivity.java MISSING)
if exist "app\src\main\java\com\anesthesiacalculator\pro\activities\SplashActivity.java" (echo ✓ SplashActivity.java) else (echo ✗ SplashActivity.java MISSING)
if exist "app\src\main\java\com\anesthesiacalculator\pro\utils\ThemeManager.java" (echo ✓ ThemeManager.java) else (echo ✗ ThemeManager.java MISSING)
if exist "app\src\main\java\com\anesthesiacalculator\pro\utils\DatabaseHelper.java" (echo ✓ DatabaseHelper.java) else (echo ✗ DatabaseHelper.java MISSING)
echo.

echo [RESOURCES]
if exist "app\src\main\res\values\strings.xml" (echo ✓ strings.xml) else (echo ✗ strings.xml MISSING)
if exist "app\src\main\res\values\colors.xml" (echo ✓ colors.xml) else (echo ✗ colors.xml MISSING)
if exist "app\src\main\res\values\themes.xml" (echo ✓ themes.xml) else (echo ✗ themes.xml MISSING)
if exist "app\src\main\res\layout\activity_main.xml" (echo ✓ activity_main.xml) else (echo ✗ activity_main.xml MISSING)
if exist "app\src\main\res\drawable\gradient_background.xml" (echo ✓ gradient_background.xml) else (echo ✗ gradient_background.xml MISSING)
if exist "app\src\main\res\drawable\ic_calculator.xml" (echo ✓ ic_calculator.xml) else (echo ✗ ic_calculator.xml MISSING)
echo.

echo ========================================
echo Verification complete!
echo.
echo If you see ✓ marks, the files are created successfully.
echo If you see ✗ marks, those files are missing.
echo.
echo You can now open this project in Android Studio!
echo ========================================
pause
