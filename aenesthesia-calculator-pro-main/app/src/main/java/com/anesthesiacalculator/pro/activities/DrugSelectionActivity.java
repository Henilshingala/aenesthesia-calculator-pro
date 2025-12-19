package com.anesthesiacalculator.pro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.google.android.material.button.MaterialButton;
import com.anesthesiacalculator.pro.R;
import com.anesthesiacalculator.pro.adapters.DrugAdapter;
import com.anesthesiacalculator.pro.models.Drug;
import com.anesthesiacalculator.pro.models.Patient;
import com.anesthesiacalculator.pro.utils.ThemeManager;
import com.anesthesiacalculator.pro.utils.ApiHelper;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class DrugSelectionActivity extends AppCompatActivity {
    private RecyclerView rvDrugs;
    private SwipeRefreshLayout swipeRefresh;
    private MaterialButton btnCalculate;
    private DrugAdapter adapter;
    private List<Drug> drugList;
    private Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        ThemeManager.initialize(this);
        
        setContentView(R.layout.activity_drug_selection);

        patient = (Patient) getIntent().getSerializableExtra("patient");
        
        initViews();
        setupRecyclerView();
        fetchDrugs();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null) {
            Toast.makeText(this, "Checking for database updates...", Toast.LENGTH_SHORT).show();
            fetchDrugs();
        }
    }

    private void initViews() {
        rvDrugs = findViewById(R.id.rv_drugs);
        swipeRefresh = findViewById(R.id.swipe_refresh);
        btnCalculate = findViewById(R.id.btn_calculate);
        
        btnCalculate.setOnClickListener(v -> calculateDosages());
        
        swipeRefresh.setOnRefreshListener(() -> {
            Toast.makeText(this, "Refreshing drug data from database...", Toast.LENGTH_SHORT).show();
            fetchDrugs();
        });
        
        swipeRefresh.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        );
    }

    private void fetchDrugs() {
        drugList = new ArrayList<>();
        ApiHelper.getInstance().fetchDrugs(new ApiHelper.DataCallback<List<Drug>>() {
            @Override
            public void onSuccess(List<Drug> result) {
                drugList.clear();
                drugList.addAll(result);
                adapter = new DrugAdapter(drugList);
                rvDrugs.setAdapter(adapter);
                swipeRefresh.setRefreshing(false);
                
                Toast.makeText(DrugSelectionActivity.this, 
                    "Loaded " + result.size() + " drugs from database. Check if list shows below.", 
                    Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(DrugSelectionActivity.this, 
                    "Failed to load drugs from database: " + e.getMessage() + ". Please check your internet connection.", 
                    Toast.LENGTH_LONG).show();
                swipeRefresh.setRefreshing(false);
                
                btnCalculate.setEnabled(false);
                btnCalculate.setText("Database connection required");
            }
        });
    }
    

    private void setupRecyclerView() {
        if (drugList == null) drugList = new ArrayList<>();
        adapter = new DrugAdapter(drugList);
        rvDrugs.setLayoutManager(new LinearLayoutManager(this));
        rvDrugs.setAdapter(adapter);
    }

    private void calculateDosages() {
        List<Drug> selectedDrugs = new ArrayList<>();
        for (Drug drug : drugList) {
            if (drug.isSelected()) {
                selectedDrugs.add(drug);
            }
        }

        if (selectedDrugs.isEmpty()) {
            // Show error message
            return;
        }

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("patient", patient);
        intent.putExtra("selectedDrugs", (ArrayList<Drug>) selectedDrugs);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}