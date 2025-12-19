package com.anesthesiacalculator.pro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.anesthesiacalculator.pro.R;
import com.anesthesiacalculator.pro.models.Patient;
import com.anesthesiacalculator.pro.models.Surgery;
import com.anesthesiacalculator.pro.utils.ApiHelper;
import java.util.List;

public class PatientInputActivity extends AppCompatActivity {
    private TextInputEditText etPatientName, etAge, etWeight;
    private AutoCompleteTextView etProcedureType;
    private MaterialButton btnNext, btnRefreshSurgeries;
    private ProgressBar progressSurgery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_input);

        initViews();
        setupProcedureTypeDropdown();
    }

    private void initViews() {
        etPatientName = findViewById(R.id.et_patient_name);
        etAge = findViewById(R.id.et_age);
        etWeight = findViewById(R.id.et_weight);
        etProcedureType = findViewById(R.id.et_procedure_type);
        btnNext = findViewById(R.id.btn_next);
        btnRefreshSurgeries = findViewById(R.id.btn_refresh_surgeries);
        progressSurgery = findViewById(R.id.progress_surgery);

        btnNext.setOnClickListener(v -> validateAndProceed());
        btnRefreshSurgeries.setOnClickListener(v -> {
            Toast.makeText(this, "Refreshing surgery list from database...", Toast.LENGTH_SHORT).show();
            setupProcedureTypeDropdown();
        });
    }

    private void setupProcedureTypeDropdown() {
        progressSurgery.setVisibility(View.VISIBLE);
        ApiHelper.getInstance().fetchSurgeries(new ApiHelper.DataCallback<List<Surgery>>() {
            @Override
            public void onSuccess(List<Surgery> result) {
                progressSurgery.setVisibility(View.GONE);
                
                String[] surgeryNames = new String[result.size()];
                for (int i = 0; i < result.size(); i++) {
                    surgeryNames[i] = result.get(i).getSurgeryName();
                }
                
                ArrayAdapter<String> adapter = new ArrayAdapter<>(PatientInputActivity.this,
                        android.R.layout.simple_dropdown_item_1line, surgeryNames);
                etProcedureType.setAdapter(adapter);
            }

            @Override
            public void onError(Exception e) {
                progressSurgery.setVisibility(View.GONE);
                Toast.makeText(PatientInputActivity.this, "Failed to load surgeries from database: " + e.getMessage() + ". Please check your internet connection.", Toast.LENGTH_LONG).show();
                
                etProcedureType.setEnabled(false);
                etProcedureType.setHint("Database connection required");
            }
        });
    }
    

    private void validateAndProceed() {
        String name = etPatientName.getText().toString().trim();
        String ageStr = etAge.getText().toString().trim();
        String weightStr = etWeight.getText().toString().trim();
        String procedure = etProcedureType.getText().toString().trim();

        if (name.isEmpty()) {
            etPatientName.setError("Patient name is required");
            return;
        }

        if (ageStr.isEmpty()) {
            etAge.setError("Age is required");
            return;
        }

        if (weightStr.isEmpty()) {
            etWeight.setError("Weight is required");
            return;
        }

        if (procedure.isEmpty()) {
            etProcedureType.setError("Procedure type is required");
            return;
        }

        try {
            int age = Integer.parseInt(ageStr);
            double weight = Double.parseDouble(weightStr);

            if (age < 0 || age > 120) {
                etAge.setError("Please enter a valid age (0-120)");
                return;
            }

            if (weight < 0.5 || weight > 300) {
                etWeight.setError("Please enter a valid weight (0.5-300 kg)");
                return;
            }

            Patient patient = new Patient(name, age, weight, procedure);
            Intent intent = new Intent(this, DrugSelectionActivity.class);
            intent.putExtra("patient", patient);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numeric values", Toast.LENGTH_SHORT).show();
        }
    }
}