package com.anesthesiacalculator.pro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.anesthesiacalculator.pro.R;
import com.anesthesiacalculator.pro.models.PatientRecord;
import com.anesthesiacalculator.pro.utils.ApiHelper;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class SavedPatientAdapter extends RecyclerView.Adapter<SavedPatientAdapter.PatientViewHolder> {
    private List<PatientRecord> patientRecords;
    private Context context;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());

    public SavedPatientAdapter(List<PatientRecord> patientRecords, Context context) {
        this.patientRecords = patientRecords;
        this.context = context;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_saved_patient, parent, false);
        return new PatientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        PatientRecord record = patientRecords.get(position);
        
        holder.tvPatientName.setText(record.getPatientName());
        holder.tvPatientInfo.setText(String.format("%d years, %.1f kg - %s", 
                record.getPatientAge(), record.getPatientWeight(), record.getProcedureType()));
        holder.tvDate.setText(dateFormat.format(record.getDateCreated()));
        
        // Show number of drugs used
        int drugCount = record.getSelectedDrugNames() != null ? record.getSelectedDrugNames().size() : 0;
        holder.tvDrugCount.setText(String.format("%d drugs used", drugCount));
        
        // Set up delete button
        holder.btnDelete.setOnClickListener(v -> showDeleteConfirmation(record, position));
        
        // Set up click listener for viewing details
        holder.itemView.setOnClickListener(v -> showPatientDetails(record));
    }

    @Override
    public int getItemCount() {
        return patientRecords.size();
    }

    private void showDeleteConfirmation(PatientRecord record, int position) {
        new AlertDialog.Builder(context)
                .setTitle("Delete Patient Record")
                .setMessage("Are you sure you want to delete " + record.getPatientName() + "'s record?")
                .setPositiveButton("Delete", (dialog, which) -> deleteRecord(record, position))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteRecord(PatientRecord record, int position) {
        ApiHelper.getInstance().deletePatientRecord(context, record.getRecordId(), new ApiHelper.DataCallback<String>() {
            @Override
            public void onSuccess(String result) {
                patientRecords.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(context, "Record deleted successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(context, "Failed to delete record: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showPatientDetails(PatientRecord record) {
        StringBuilder details = new StringBuilder();
        details.append("Patient: ").append(record.getPatientName()).append("\n");
        details.append("Age: ").append(record.getPatientAge()).append(" years\n");
        details.append("Weight: ").append(record.getPatientWeight()).append(" kg\n");
        details.append("Procedure: ").append(record.getProcedureType()).append("\n\n");
        
        if (record.getSelectedDrugNames() != null && !record.getSelectedDrugNames().isEmpty()) {
            details.append("Drugs Used:\n");
            for (String drugName : record.getSelectedDrugNames()) {
                details.append("• ").append(drugName).append("\n");
            }
        }

        new AlertDialog.Builder(context)
                .setTitle("Patient Record Details")
                .setMessage(details.toString())
                .setPositiveButton("OK", null)
                .show();
    }

    static class PatientViewHolder extends RecyclerView.ViewHolder {
        TextView tvPatientName, tvPatientInfo, tvDate, tvDrugCount;
        MaterialButton btnDelete;

        PatientViewHolder(View itemView) {
            super(itemView);
            tvPatientName = itemView.findViewById(R.id.tv_patient_name);
            tvPatientInfo = itemView.findViewById(R.id.tv_patient_info);
            tvDate = itemView.findViewById(R.id.tv_date);
            tvDrugCount = itemView.findViewById(R.id.tv_drug_count);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
