package com.anesthesiacalculator.pro.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.anesthesiacalculator.pro.R;
import com.anesthesiacalculator.pro.adapters.SavedPatientAdapter;
import com.anesthesiacalculator.pro.models.PatientRecord;
import com.anesthesiacalculator.pro.utils.DeviceManager;
import com.anesthesiacalculator.pro.utils.ApiHelper;
import java.util.List;

public class SavedPatientsActivity extends AppCompatActivity {
    private RecyclerView rvSavedPatients;
    private SwipeRefreshLayout swipeRefresh;
    private ProgressBar progressBar;
    private SavedPatientAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_patients);

        initViews();
        
        // Show device ID for debugging
        String deviceId = DeviceManager.getInstance().getDeviceId(this);
        String devicePath = DeviceManager.getInstance().getDevicePatientRecordsPath(this);
        Toast.makeText(this, "Device ID: " + deviceId + "\nPath: " + devicePath, Toast.LENGTH_LONG).show();
        
        loadSavedPatients();
    }

    private void initViews() {
        rvSavedPatients = findViewById(R.id.rv_saved_patients);
        swipeRefresh = findViewById(R.id.swipe_refresh);
        progressBar = findViewById(R.id.progress_bar);
        
        rvSavedPatients.setLayoutManager(new LinearLayoutManager(this));
        
        // Set up swipe refresh
        swipeRefresh.setOnRefreshListener(this::loadSavedPatients);
    }

    private void loadSavedPatients() {
        if (!swipeRefresh.isRefreshing()) {
            progressBar.setVisibility(View.VISIBLE);
        }
        
        // Show device info for debugging
        String deviceId = DeviceManager.getInstance().getDeviceId(this);
        String devicePath = DeviceManager.getInstance().getDevicePatientRecordsPath(this);
        Toast.makeText(this, "Loading patient records for device: " + deviceId, Toast.LENGTH_SHORT).show();
        
        ApiHelper.getInstance().fetchPatientRecords(this, new ApiHelper.DataCallback<List<PatientRecord>>() {
            @Override
            public void onSuccess(List<PatientRecord> records) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    swipeRefresh.setRefreshing(false);
                    
                    Toast.makeText(SavedPatientsActivity.this, "Found " + records.size() + " patient records", Toast.LENGTH_SHORT).show();
                    
                    // Always update the adapter, even if empty
                    adapter = new SavedPatientAdapter(records, SavedPatientsActivity.this);
                    rvSavedPatients.setAdapter(adapter);
                    
                    if (records.isEmpty()) {
                        // Show more detailed message with device info
                        Toast.makeText(SavedPatientsActivity.this, 
                            "No saved patient records found for this device\n" +
                            "Device ID: " + deviceId + "\n" +
                            "Path: " + devicePath, 
                            Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(() -> {
                    progressBar.setVisibility(View.GONE);
                    swipeRefresh.setRefreshing(false);
                    Toast.makeText(SavedPatientsActivity.this, "Failed to load records: " + e.getMessage() + "\nDevice: " + deviceId, Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh data when activity resumes
        loadSavedPatients();
    }
}
