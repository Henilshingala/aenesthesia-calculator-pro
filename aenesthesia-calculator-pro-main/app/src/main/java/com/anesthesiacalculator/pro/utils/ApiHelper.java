package com.anesthesiacalculator.pro.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.anesthesiacalculator.pro.models.Drug;
import com.anesthesiacalculator.pro.models.PatientRecord;
import com.anesthesiacalculator.pro.models.Surgery;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * API Helper for MySQL/PHPMyAdmin backend
 * Replaces Firebase functionality with HTTP requests
 */
public class ApiHelper {
    private static final String TAG = "ApiHelper";
    private static ApiHelper instance;
    private ExecutorService executorService;
    private Handler mainHandler;

    private ApiHelper() {
        executorService = Executors.newFixedThreadPool(3);
        mainHandler = new Handler(Looper.getMainLooper());
    }

    public static synchronized ApiHelper getInstance() {
        if (instance == null) {
            instance = new ApiHelper();
        }
        return instance;
    }

    public interface DataCallback<T> {
        void onSuccess(T result);
        void onError(Exception e);
    }

    /**
     * Fetch all surgeries from the backend
     */
    public void fetchSurgeries(DataCallback<List<Surgery>> callback) {
        executorService.execute(() -> {
            try {
                URL url = new URL(ApiConfig.SURGERIES_ENDPOINT);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(ApiConfig.CONNECTION_TIMEOUT);
                conn.setReadTimeout(ApiConfig.READ_TIMEOUT);

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    in.close();

                    JSONObject jsonResponse = new JSONObject(response.toString());
                    List<Surgery> surgeries = new ArrayList<>();

                    if (jsonResponse.getBoolean("success")) {
                        JSONArray data = jsonResponse.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject surgeryObj = data.getJSONObject(i);
                            surgeries.add(new Surgery(
                                surgeryObj.getLong("id"),
                                surgeryObj.getString("surgery_name")
                            ));
                        }
                        mainHandler.post(() -> callback.onSuccess(surgeries));
                    } else {
                        String errorMessage = jsonResponse.optString("message", "Unknown server error");
                        mainHandler.post(() -> callback.onError(new Exception(errorMessage)));
                    }
                } else {
                    mainHandler.post(() -> callback.onError(new Exception("Server returned code: " + responseCode)));
                }
                conn.disconnect();
            } catch (Exception e) {
                Log.e(TAG, "Error fetching surgeries: " + e.getMessage());
                mainHandler.post(() -> callback.onError(e));
            }
        });
    }

    /**
     * Fetch all drugs from the backend
     */
    public void fetchDrugs(DataCallback<List<Drug>> callback) {
        executorService.execute(() -> {
            try {
                URL url = new URL(ApiConfig.DRUGS_ENDPOINT);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(ApiConfig.CONNECTION_TIMEOUT);
                conn.setReadTimeout(ApiConfig.READ_TIMEOUT);

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    in.close();

                    JSONObject jsonResponse = new JSONObject(response.toString());
                    List<Drug> drugs = new ArrayList<>();

                    if (jsonResponse.getBoolean("success")) {
                        JSONArray data = jsonResponse.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject drugObj = data.getJSONObject(i);
                            drugs.add(new Drug(
                                drugObj.getLong("id"),
                                drugObj.getString("drug_name"),
                                drugObj.getDouble("min_dose"),
                                drugObj.getDouble("max_dose"),
                                drugObj.getDouble("concentration"),
                                drugObj.getString("unit")
                            ));
                        }
                        mainHandler.post(() -> callback.onSuccess(drugs));
                    } else {
                        String errorMessage = jsonResponse.optString("message", "Unknown server error");
                        mainHandler.post(() -> callback.onError(new Exception(errorMessage)));
                    }
                } else {
                    mainHandler.post(() -> callback.onError(new Exception("Server returned code: " + responseCode)));
                }
                conn.disconnect();
            } catch (Exception e) {
                Log.e(TAG, "Error fetching drugs: " + e.getMessage());
                mainHandler.post(() -> callback.onError(e));
            }
        });
    }

    /**
     * Save patient record to backend
     */
    public void savePatientRecord(Context context, PatientRecord patientRecord, DataCallback<String> callback) {
        executorService.execute(() -> {
            try {
                URL url = new URL(ApiConfig.PATIENT_RECORDS_ENDPOINT);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setConnectTimeout(ApiConfig.CONNECTION_TIMEOUT);
                conn.setReadTimeout(ApiConfig.READ_TIMEOUT);
                conn.setDoOutput(true);

                // Prepare JSON data
                JSONObject jsonData = new JSONObject();
                jsonData.put("device_id", DeviceManager.getInstance().getDeviceId(context));
                jsonData.put("patient_name", patientRecord.getPatientName());
                jsonData.put("patient_age", patientRecord.getPatientAge());
                jsonData.put("patient_weight", patientRecord.getPatientWeight());
                jsonData.put("procedure_type", patientRecord.getProcedureType());
                jsonData.put("selected_drug_names", new JSONArray(patientRecord.getSelectedDrugNames()));
                
                // Convert dosage results to JSON
                JSONArray dosageArray = new JSONArray();
                if (patientRecord.getDosageResults() != null) {
                    for (int i = 0; i < patientRecord.getDosageResults().size(); i++) {
                        JSONObject resultObj = new JSONObject();
                        // Add dosage result fields here
                        dosageArray.put(resultObj);
                    }
                }
                jsonData.put("dosage_results", dosageArray);
                jsonData.put("notes", patientRecord.getNotes() != null ? patientRecord.getNotes() : "");

                // Send request
                OutputStream os = conn.getOutputStream();
                os.write(jsonData.toString().getBytes("UTF-8"));
                os.flush();
                os.close();

                int responseCode = conn.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                if (responseCode == HttpURLConnection.HTTP_CREATED && jsonResponse.getBoolean("success")) {
                    String recordId = jsonResponse.getString("record_id");
                    patientRecord.setRecordId(recordId);
                    mainHandler.post(() -> callback.onSuccess("Patient record saved successfully!"));
                } else {
                    String errorMessage = jsonResponse.optString("message", "Unknown server error");
                    mainHandler.post(() -> callback.onError(new Exception(errorMessage)));
                }
                conn.disconnect();
            } catch (Exception e) {
                Log.e(TAG, "Error saving patient record: " + e.getMessage());
                mainHandler.post(() -> callback.onError(e));
            }
        });
    }

    /**
     * Fetch patient records for the current device
     */
    public void fetchPatientRecords(Context context, DataCallback<List<PatientRecord>> callback) {
        executorService.execute(() -> {
            try {
                String deviceId = DeviceManager.getInstance().getDeviceId(context);
                String urlWithParams = ApiConfig.PATIENT_RECORDS_ENDPOINT + "?device_id=" + deviceId;
                
                URL url = new URL(urlWithParams);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(ApiConfig.CONNECTION_TIMEOUT);
                conn.setReadTimeout(ApiConfig.READ_TIMEOUT);

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        response.append(line);
                    }
                    in.close();

                    JSONObject jsonResponse = new JSONObject(response.toString());
                    List<PatientRecord> records = new ArrayList<>();

                    if (jsonResponse.getBoolean("success")) {
                        JSONArray data = jsonResponse.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject recordObj = data.getJSONObject(i);
                            PatientRecord record = new PatientRecord();
                            record.setRecordId(recordObj.getString("record_id"));
                            record.setPatientName(recordObj.getString("patient_name"));
                            record.setPatientAge(recordObj.getInt("patient_age"));
                            record.setPatientWeight(recordObj.getDouble("patient_weight"));
                            record.setProcedureType(recordObj.getString("procedure_type"));
                            
                            // Parse selected drug names
                            JSONArray drugNames = recordObj.optJSONArray("selected_drug_names");
                            if (drugNames != null) {
                                List<String> names = new ArrayList<>();
                                for (int j = 0; j < drugNames.length(); j++) {
                                    names.add(drugNames.getString(j));
                                }
                                record.setSelectedDrugNames(names);
                            }
                            
                            records.add(record);
                        }
                        mainHandler.post(() -> callback.onSuccess(records));
                    } else {
                        String errorMessage = jsonResponse.optString("message", "Unknown server error");
                        mainHandler.post(() -> callback.onError(new Exception(errorMessage)));
                    }
                } else {
                    mainHandler.post(() -> callback.onError(new Exception("Server returned code: " + responseCode)));
                }
                conn.disconnect();
            } catch (Exception e) {
                Log.e(TAG, "Error fetching patient records: " + e.getMessage());
                mainHandler.post(() -> callback.onError(e));
            }
        });
    }

    /**
     * Delete a patient record
     */
    public void deletePatientRecord(Context context, String recordId, DataCallback<String> callback) {
        executorService.execute(() -> {
            try {
                URL url = new URL(ApiConfig.PATIENT_RECORDS_ENDPOINT);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("DELETE");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setConnectTimeout(ApiConfig.CONNECTION_TIMEOUT);
                conn.setReadTimeout(ApiConfig.READ_TIMEOUT);
                conn.setDoOutput(true);

                JSONObject jsonData = new JSONObject();
                jsonData.put("record_id", recordId);

                OutputStream os = conn.getOutputStream();
                os.write(jsonData.toString().getBytes("UTF-8"));
                os.flush();
                os.close();

                int responseCode = conn.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                if (responseCode == HttpURLConnection.HTTP_OK && jsonResponse.getBoolean("success")) {
                    mainHandler.post(() -> callback.onSuccess("Record deleted successfully!"));
                } else {
                    String errorMessage = jsonResponse.optString("message", "Unknown server error");
                    mainHandler.post(() -> callback.onError(new Exception(errorMessage)));
                }
                conn.disconnect();
            } catch (Exception e) {
                Log.e(TAG, "Error deleting patient record: " + e.getMessage());
                mainHandler.post(() -> callback.onError(e));
            }
        });
    }
}
