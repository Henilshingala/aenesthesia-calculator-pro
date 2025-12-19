package com.anesthesiacalculator.pro.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.anesthesiacalculator.pro.R;
import com.anesthesiacalculator.pro.models.Drug;
import java.util.List;

public class DrugAdapter extends RecyclerView.Adapter<DrugAdapter.DrugViewHolder> {
    private List<Drug> drugs;

    public DrugAdapter(List<Drug> drugs) {
        this.drugs = drugs;
    }

    @NonNull
    @Override
    public DrugViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_drug, parent, false);
        return new DrugViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrugViewHolder holder, int position) {
        Drug drug = drugs.get(position);
        holder.tvDrugName.setText(drug.getName());
        holder.tvDosageRange.setText(String.format("%.1f - %.1f %s", 
                drug.getMinDose(), drug.getMaxDose(), drug.getUnit()));
        holder.switchSelect.setChecked(drug.isSelected());
        
        holder.switchSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
            drug.setSelected(isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return drugs.size();
    }

    static class DrugViewHolder extends RecyclerView.ViewHolder {
        TextView tvDrugName, tvDosageRange;
        SwitchMaterial switchSelect;

        DrugViewHolder(View itemView) {
            super(itemView);
            tvDrugName = itemView.findViewById(R.id.tv_drug_name);
            tvDosageRange = itemView.findViewById(R.id.tv_dosage_range);
            switchSelect = itemView.findViewById(R.id.switch_select);
        }
    }
}
