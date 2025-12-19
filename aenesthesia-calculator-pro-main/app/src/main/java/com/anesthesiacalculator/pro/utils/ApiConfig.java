package com.anesthesiacalculator.pro.utils;

/**
 * API Configuration
 * Update the BASE_URL to point to your PHP backend server
 */
public class ApiConfig {
    // For Android Emulator: Use 10.0.2.2 to access localhost
    // For Real Device: Use your computer's IP (e.g., 192.168.1.102)
    // For Online Server: Use your domain URL
    public static final String BASE_URL = "http://10.0.2.2/backend/api/";
    
    public static final String SURGERIES_ENDPOINT = BASE_URL + "surgeries.php";
    public static final String DRUGS_ENDPOINT = BASE_URL + "drugs.php";
    public static final String PATIENT_RECORDS_ENDPOINT = BASE_URL + "patient_records.php";
    
    public static final int CONNECTION_TIMEOUT = 15000; // 15 seconds
    public static final int READ_TIMEOUT = 15000; // 15 seconds
}
