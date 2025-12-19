package com.anesthesiacalculator.pro.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;
import java.util.UUID;

/**
 * Manages device-specific identification for Firebase data isolation
 * Ensures patient data is only visible on the device where it was created
 */
public class DeviceManager {
    private static final String TAG = "DeviceManager";
    private static final String PREFS_NAME = "device_prefs";
    private static final String KEY_DEVICE_ID = "device_id";
    private static DeviceManager instance;
    private String deviceId;

    private DeviceManager() {}

    public static synchronized DeviceManager getInstance() {
        if (instance == null) {
            instance = new DeviceManager();
        }
        return instance;
    }

    /**
     * Get unique device ID for this specific device
     * Creates one if it doesn't exist and stores it persistently
     */
    public String getDeviceId(Context context) {
        if (deviceId == null) {
            SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            deviceId = prefs.getString(KEY_DEVICE_ID, null);
            
            if (deviceId == null) {
                // Generate new unique device ID
                deviceId = generateUniqueDeviceId(context);
                
                // Save it persistently
                prefs.edit().putString(KEY_DEVICE_ID, deviceId).apply();
                Log.d(TAG, "Generated new device ID: " + deviceId);
            } else {
                Log.d(TAG, "Using existing device ID: " + deviceId);
            }
        }
        return deviceId;
    }

    /**
     * Generate a unique device identifier
     * Uses Android ID + UUID for maximum uniqueness
     */
    private String generateUniqueDeviceId(Context context) {
        try {
            // Get Android ID (unique per device/user combination)
            String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            
            // Combine with UUID for extra uniqueness
            String uuid = UUID.randomUUID().toString();
            
            // Create hash-like ID (shorter and cleaner)
            return "device_" + Math.abs((androidId + uuid).hashCode());
            
        } catch (Exception e) {
            // Fallback to pure UUID if Android ID fails
            return "device_" + UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        }
    }

    /**
     * Get Firebase path for device-specific patient records
     */
    public String getDevicePatientRecordsPath(Context context) {
        return "patient_records/" + getDeviceId(context);
    }

    /**
     * Clear device ID (for testing purposes only)
     */
    public void clearDeviceId(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().remove(KEY_DEVICE_ID).apply();
        deviceId = null;
    }
}
