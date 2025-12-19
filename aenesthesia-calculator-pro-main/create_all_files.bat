@echo off
echo Creating Android Project Files...

REM Create directory structure
mkdir "app\src\main\java\com\anesthesiacalculator\pro\activities" 2>nul
mkdir "app\src\main\java\com\anesthesiacalculator\pro\adapters" 2>nul
mkdir "app\src\main\java\com\anesthesiacalculator\pro\models" 2>nul
mkdir "app\src\main\java\com\anesthesiacalculator\pro\utils" 2>nul
mkdir "app\src\main\res\layout" 2>nul
mkdir "app\src\main\res\values" 2>nul
mkdir "app\src\main\res\drawable" 2>nul
mkdir "app\src\main\res\anim" 2>nul
mkdir "app\src\main\res\xml" 2>nul
mkdir "app\src\main\assets" 2>nul

echo Directory structure created successfully!
echo.
echo Please check the ACP folder in Windows Explorer.
echo All files should now be visible.
echo.
pause
