@echo off
echo ========================================
echo   ANESTHESIA CALCULATOR PRO
echo   FINAL PROJECT VERIFICATION
echo ========================================
echo.

set /a total=0
set /a found=0

echo [GRADLE BUILD FILES]
if exist "build.gradle" (echo ✓ build.gradle & set /a found+=1) else (echo ✗ build.gradle MISSING)
if exist "settings.gradle" (echo ✓ settings.gradle & set /a found+=1) else (echo ✗ settings.gradle MISSING)
if exist "gradle.properties" (echo ✓ gradle.properties & set /a found+=1) else (echo ✗ gradle.properties MISSING)
if exist "app\build.gradle" (echo ✓ app\build.gradle & set /a found+=1) else (echo ✗ app\build.gradle MISSING)
if exist "app\proguard-rules.pro" (echo ✓ app\proguard-rules.pro & set /a found+=1) else (echo ✗ app\proguard-rules.pro MISSING)
set /a total+=5
echo.

echo [ANDROID MANIFEST]
if exist "app\src\main\AndroidManifest.xml" (echo ✓ AndroidManifest.xml & set /a found+=1) else (echo ✗ AndroidManifest.xml MISSING)
set /a total+=1
echo.

echo [JAVA SOURCE FILES]
if exist "app\src\main\java\com\anesthesiacalculator\pro\Application.java" (echo ✓ Application.java & set /a found+=1) else (echo ✗ Application.java MISSING)
if exist "app\src\main\java\com\anesthesiacalculator\pro\models\Patient.java" (echo ✓ Patient.java & set /a found+=1) else (echo ✗ Patient.java MISSING)
if exist "app\src\main\java\com\anesthesiacalculator\pro\models\Drug.java" (echo ✓ Drug.java & set /a found+=1) else (echo ✗ Drug.java MISSING)
if exist "app\src\main\java\com\anesthesiacalculator\pro\models\DosageResult.java" (echo ✓ DosageResult.java & set /a found+=1) else (echo ✗ DosageResult.java MISSING)
if exist "app\src\main\java\com\anesthesiacalculator\pro\activities\SplashActivity.java" (echo ✓ SplashActivity.java & set /a found+=1) else (echo ✗ SplashActivity.java MISSING)
if exist "app\src\main\java\com\anesthesiacalculator\pro\activities\MainActivity.java" (echo ✓ MainActivity.java & set /a found+=1) else (echo ✗ MainActivity.java MISSING)
if exist "app\src\main\java\com\anesthesiacalculator\pro\activities\PatientInputActivity.java" (echo ✓ PatientInputActivity.java & set /a found+=1) else (echo ✗ PatientInputActivity.java MISSING)
if exist "app\src\main\java\com\anesthesiacalculator\pro\activities\DrugSelectionActivity.java" (echo ✓ DrugSelectionActivity.java & set /a found+=1) else (echo ✗ DrugSelectionActivity.java MISSING)
if exist "app\src\main\java\com\anesthesiacalculator\pro\activities\ResultActivity.java" (echo ✓ ResultActivity.java & set /a found+=1) else (echo ✗ ResultActivity.java MISSING)
if exist "app\src\main\java\com\anesthesiacalculator\pro\activities\SavedPatientsActivity.java" (echo ✓ SavedPatientsActivity.java & set /a found+=1) else (echo ✗ SavedPatientsActivity.java MISSING)
if exist "app\src\main\java\com\anesthesiacalculator\pro\activities\SettingsActivity.java" (echo ✓ SettingsActivity.java & set /a found+=1) else (echo ✗ SettingsActivity.java MISSING)
if exist "app\src\main\java\com\anesthesiacalculator\pro\activities\AboutActivity.java" (echo ✓ AboutActivity.java & set /a found+=1) else (echo ✗ AboutActivity.java MISSING)
if exist "app\src\main\java\com\anesthesiacalculator\pro\adapters\DrugAdapter.java" (echo ✓ DrugAdapter.java & set /a found+=1) else (echo ✗ DrugAdapter.java MISSING)
if exist "app\src\main\java\com\anesthesiacalculator\pro\adapters\SavedPatientAdapter.java" (echo ✓ SavedPatientAdapter.java & set /a found+=1) else (echo ✗ SavedPatientAdapter.java MISSING)
if exist "app\src\main\java\com\anesthesiacalculator\pro\utils\DatabaseHelper.java" (echo ✓ DatabaseHelper.java & set /a found+=1) else (echo ✗ DatabaseHelper.java MISSING)
if exist "app\src\main\java\com\anesthesiacalculator\pro\utils\ThemeManager.java" (echo ✓ ThemeManager.java & set /a found+=1) else (echo ✗ ThemeManager.java MISSING)
if exist "app\src\main\java\com\anesthesiacalculator\pro\utils\PDFGenerator.java" (echo ✓ PDFGenerator.java & set /a found+=1) else (echo ✗ PDFGenerator.java MISSING)
if exist "app\src\main\java\com\anesthesiacalculator\pro\utils\Constants.java" (echo ✓ Constants.java & set /a found+=1) else (echo ✗ Constants.java MISSING)
set /a total+=18
echo.

echo [LAYOUT FILES]
if exist "app\src\main\res\layout\activity_splash.xml" (echo ✓ activity_splash.xml & set /a found+=1) else (echo ✗ activity_splash.xml MISSING)
if exist "app\src\main\res\layout\activity_main.xml" (echo ✓ activity_main.xml & set /a found+=1) else (echo ✗ activity_main.xml MISSING)
if exist "app\src\main\res\layout\activity_patient_input.xml" (echo ✓ activity_patient_input.xml & set /a found+=1) else (echo ✗ activity_patient_input.xml MISSING)
if exist "app\src\main\res\layout\activity_drug_selection.xml" (echo ✓ activity_drug_selection.xml & set /a found+=1) else (echo ✗ activity_drug_selection.xml MISSING)
if exist "app\src\main\res\layout\activity_result.xml" (echo ✓ activity_result.xml & set /a found+=1) else (echo ✗ activity_result.xml MISSING)
if exist "app\src\main\res\layout\activity_saved_patients.xml" (echo ✓ activity_saved_patients.xml & set /a found+=1) else (echo ✗ activity_saved_patients.xml MISSING)
if exist "app\src\main\res\layout\activity_settings.xml" (echo ✓ activity_settings.xml & set /a found+=1) else (echo ✗ activity_settings.xml MISSING)
if exist "app\src\main\res\layout\activity_about.xml" (echo ✓ activity_about.xml & set /a found+=1) else (echo ✗ activity_about.xml MISSING)
if exist "app\src\main\res\layout\item_drug.xml" (echo ✓ item_drug.xml & set /a found+=1) else (echo ✗ item_drug.xml MISSING)
if exist "app\src\main\res\layout\item_saved_patient.xml" (echo ✓ item_saved_patient.xml & set /a found+=1) else (echo ✗ item_saved_patient.xml MISSING)
if exist "app\src\main\res\layout\card_dosage_result.xml" (echo ✓ card_dosage_result.xml & set /a found+=1) else (echo ✗ card_dosage_result.xml MISSING)
set /a total+=11
echo.

echo [RESOURCE FILES]
if exist "app\src\main\res\values\colors.xml" (echo ✓ colors.xml & set /a found+=1) else (echo ✗ colors.xml MISSING)
if exist "app\src\main\res\values\strings.xml" (echo ✓ strings.xml & set /a found+=1) else (echo ✗ strings.xml MISSING)
if exist "app\src\main\res\values\themes.xml" (echo ✓ themes.xml & set /a found+=1) else (echo ✗ themes.xml MISSING)
if exist "app\src\main\res\xml\data_extraction_rules.xml" (echo ✓ data_extraction_rules.xml & set /a found+=1) else (echo ✗ data_extraction_rules.xml MISSING)
if exist "app\src\main\res\xml\backup_rules.xml" (echo ✓ backup_rules.xml & set /a found+=1) else (echo ✗ backup_rules.xml MISSING)
set /a total+=5
echo.

echo [DRAWABLE FILES]
if exist "app\src\main\res\drawable\gradient_background.xml" (echo ✓ gradient_background.xml & set /a found+=1) else (echo ✗ gradient_background.xml MISSING)
if exist "app\src\main\res\drawable\ic_calculator.xml" (echo ✓ ic_calculator.xml & set /a found+=1) else (echo ✗ ic_calculator.xml MISSING)
if exist "app\src\main\res\drawable\ic_patients.xml" (echo ✓ ic_patients.xml & set /a found+=1) else (echo ✗ ic_patients.xml MISSING)
if exist "app\src\main\res\drawable\ic_settings.xml" (echo ✓ ic_settings.xml & set /a found+=1) else (echo ✗ ic_settings.xml MISSING)
if exist "app\src\main\res\drawable\ic_info.xml" (echo ✓ ic_info.xml & set /a found+=1) else (echo ✗ ic_info.xml MISSING)
if exist "app\src\main\res\drawable\ic_launcher_foreground.xml" (echo ✓ ic_launcher_foreground.xml & set /a found+=1) else (echo ✗ ic_launcher_foreground.xml MISSING)
set /a total+=6
echo.

echo [ANIMATION FILES]
if exist "app\src\main\res\anim\fade_in.xml" (echo ✓ fade_in.xml & set /a found+=1) else (echo ✗ fade_in.xml MISSING)
if exist "app\src\main\res\anim\fade_out.xml" (echo ✓ fade_out.xml & set /a found+=1) else (echo ✗ fade_out.xml MISSING)
if exist "app\src\main\res\anim\slide_in_right.xml" (echo ✓ slide_in_right.xml & set /a found+=1) else (echo ✗ slide_in_right.xml MISSING)
if exist "app\src\main\res\anim\slide_out_left.xml" (echo ✓ slide_out_left.xml & set /a found+=1) else (echo ✗ slide_out_left.xml MISSING)
if exist "app\src\main\res\anim\slide_up.xml" (echo ✓ slide_up.xml & set /a found+=1) else (echo ✗ slide_up.xml MISSING)
set /a total+=5
echo.

echo [LAUNCHER ICONS]
if exist "app\src\main\res\mipmap-mdpi\ic_launcher.xml" (echo ✓ ic_launcher.xml & set /a found+=1) else (echo ✗ ic_launcher.xml MISSING)
if exist "app\src\main\res\mipmap-mdpi\ic_launcher_round.xml" (echo ✓ ic_launcher_round.xml & set /a found+=1) else (echo ✗ ic_launcher_round.xml MISSING)
set /a total+=2
echo.

echo [ASSETS]
if exist "app\src\main\assets\medical_animation.json" (echo ✓ medical_animation.json & set /a found+=1) else (echo ✗ medical_animation.json MISSING)
set /a total+=1
echo.

echo ========================================
echo VERIFICATION SUMMARY
echo ========================================
echo Total Expected Files: %total%
echo Files Found: %found%
set /a missing=%total%-%found%
echo Missing Files: %missing%
echo.

if %found%==%total% (
    echo 🎉 SUCCESS: All files are present!
    echo Your Android project is ready to build!
) else (
    echo ⚠️  WARNING: Some files are missing.
    echo Please check the missing files above.
)

echo.
echo ========================================
echo NEXT STEPS:
echo 1. Open Android Studio
echo 2. Import project from: %CD%
echo 3. Sync Gradle files
echo 4. Build and run the application
echo ========================================
pause
