# MySQL Setup Guide

## 1. PHPMyAdmin Setup
- Create a database named `anesthesia_calculator`.
- Import the SQL schema located at `backend/database/schema.sql`.

## 2. API Configuration
- Copy the `backend` folder to your server's root (e.g., `htdocs` for XAMPP).
- Update the database credentials in `backend/api/config.php`.

## 3. Android App Configuration
- Update the `BASE_URL` in `app/src/main/java/com/anesthesiacalculator/pro/utils/ApiConfig.java`.
- For locally hosted development on emulator, use `http://10.0.2.2/backend/api/`.
- For real devices, use your computer's local IP address.
