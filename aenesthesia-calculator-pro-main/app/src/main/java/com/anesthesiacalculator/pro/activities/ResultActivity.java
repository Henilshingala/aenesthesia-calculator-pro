package com.anesthesiacalculator.pro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.anesthesiacalculator.pro.R;
import com.anesthesiacalculator.pro.models.DosageResult;
import com.anesthesiacalculator.pro.models.Drug;
import com.anesthesiacalculator.pro.models.Patient;
import com.anesthesiacalculator.pro.models.PatientRecord;
import com.anesthesiacalculator.pro.utils.ApiHelper;
import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    private LinearLayout resultContainer;
    private MaterialButton btnSave, btnRecalculate;
    private Patient patient;
    private List<Drug> selectedDrugs;
    private List<DosageResult> dosageResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        // Get data from intent
        patient = (Patient) getIntent().getSerializableExtra("patient");
        selectedDrugs = (List<Drug>) getIntent().getSerializableExtra("selectedDrugs");

        initViews();
        calculateDosages();
        displayResults();
    }

    private void initViews() {
        resultContainer = findViewById(R.id.result_container);
        btnSave = findViewById(R.id.btn_save);
        btnRecalculate = findViewById(R.id.btn_recalculate);

        btnSave.setOnClickListener(v -> savePatientToFirebase());
        btnRecalculate.setOnClickListener(v -> recalculate());
    }

    private void calculateDosages() {
        dosageResults = new ArrayList<>();
        
        for (Drug drug : selectedDrugs) {
            // Calculate dose based on patient weight and drug parameters
            double dosePerKg = (drug.getMinDose() + drug.getMaxDose()) / 2; // Use average dose
            double totalDoseMg = dosePerKg * patient.getWeight();
            double volumeMl = totalDoseMg / drug.getConcentration();
            
            // Check safety
            boolean isSafe = totalDoseMg <= (drug.getMaxDose() * patient.getWeight() * 1.2);
            String warning = isSafe ? null : "Dose exceeds recommended maximum!";
            
            DosageResult result = new DosageResult(drug.getDrugName(), totalDoseMg, volumeMl, isSafe, warning);
            dosageResults.add(result);
        }
    }

    private void displayResults() {
        resultContainer.removeAllViews();
        
        for (DosageResult result : dosageResults) {
            View resultCard = createResultCard(result);
            resultContainer.addView(resultCard);
        }
    }

    private View createResultCard(DosageResult result) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View cardView = inflater.inflate(R.layout.card_dosage_result, resultContainer, false);
        
        TextView tvDrugName = cardView.findViewById(R.id.tv_drug_name);
        TextView tvDosage = cardView.findViewById(R.id.tv_dosage);
        TextView tvVolume = cardView.findViewById(R.id.tv_volume);
        TextView tvWarning = cardView.findViewById(R.id.tv_warning);
        MaterialCardView cardContainer = cardView.findViewById(R.id.card_container);
        
        tvDrugName.setText(result.getDrugName());
        tvDosage.setText(String.format("%.2f mg", result.getDoseMg()));
        tvVolume.setText(String.format("%.2f ml", result.getVolumeMl()));
        
        if (!result.isSafe() && result.getWarning() != null) {
            tvWarning.setText(result.getWarning());
            tvWarning.setVisibility(View.VISIBLE);
            cardContainer.setStrokeColor(getColor(R.color.error_color));
        } else {
            tvWarning.setVisibility(View.GONE);
            cardContainer.setStrokeColor(getColor(R.color.success_color));
        }
        
        return cardView;
    }

    private void savePatientToFirebase() {
        // Disable save button to prevent multiple saves
        btnSave.setEnabled(false);
        btnSave.setText("Saving...");
        
        // Create patient record with all data
        PatientRecord patientRecord = new PatientRecord(patient, selectedDrugs, dosageResults);
        
        // Ensure date is set
        if (patientRecord.getDateCreated() == null) {
            patientRecord.setDateCreated(new java.util.Date());
        }
        
        // Save to Firebase with device-specific context
        ApiHelper.getInstance().savePatientRecord(this, patientRecord, new ApiHelper.DataCallback<String>() {
            @Override
            public void onSuccess(String result) {
                runOnUiThread(() -> {
                    btnSave.setEnabled(true);
                    btnSave.setText("💾 Save Patient Record");
                    Toast.makeText(ResultActivity.this, "✅ " + result, Toast.LENGTH_LONG).show();
                });
            }

            @Override
            public void onError(Exception e) {
                runOnUiThread(() -> {
                    btnSave.setEnabled(true);
                    btnSave.setText("💾 Save Patient Record");
                    Toast.makeText(ResultActivity.this, "❌ Failed to save: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
            }
        });
    }


    private void recalculate() {
        Intent intent = new Intent(this, PatientInputActivity.class);
        startActivity(intent);
        finish();
    }
}
