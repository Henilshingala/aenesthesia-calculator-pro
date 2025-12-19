package com.anesthesiacalculator.pro.models;

import java.io.Serializable;

public class Drug implements Serializable {
    private long id;
    private String drugName;
    private double minDose; // mg/kg
    private double maxDose; // mg/kg
    private double concentration; // mg/ml
    private String unit;
    private boolean selected;

    // Default constructor
    public Drug() {
        this.selected = false;
    }

    // Constructor with all fields
    public Drug(long id, String drugName, double minDose, double maxDose, double concentration, String unit) {
        this.id = id;
        this.drugName = drugName;
        this.minDose = minDose;
        this.maxDose = maxDose;
        this.concentration = concentration;
        this.unit = unit;
        this.selected = false;
    }

    // Legacy constructor for backward compatibility
    public Drug(String name, double minDose, double maxDose, double concentration, String unit) {
        this.drugName = name;
        this.minDose = minDose;
        this.maxDose = maxDose;
        this.concentration = concentration;
        this.unit = unit;
        this.selected = false;
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    
    public String getDrugName() { return drugName; }
    public void setDrugName(String drugName) { this.drugName = drugName; }
    
    // Legacy getName method for backward compatibility
    public String getName() { return drugName; }
    public void setName(String name) { this.drugName = name; }
    
    public double getMinDose() { return minDose; }
    public void setMinDose(double minDose) { this.minDose = minDose; }
    
    public double getMaxDose() { return maxDose; }
    public void setMaxDose(double maxDose) { this.maxDose = maxDose; }
    
    public double getConcentration() { return concentration; }
    public void setConcentration(double concentration) { this.concentration = concentration; }
    
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    
    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }
}
