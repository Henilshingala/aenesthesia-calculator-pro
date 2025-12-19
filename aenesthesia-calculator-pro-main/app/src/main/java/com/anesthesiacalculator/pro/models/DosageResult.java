package com.anesthesiacalculator.pro.models;

public class DosageResult {
    private String drugName;
    private double doseMg;
    private double volumeMl;
    private boolean isSafe;
    private String warning;

    // Default constructor required for Firebase
    public DosageResult() {}

    public DosageResult(String drugName, double doseMg, double volumeMl, boolean isSafe, String warning) {
        this.drugName = drugName;
        this.doseMg = doseMg;
        this.volumeMl = volumeMl;
        this.isSafe = isSafe;
        this.warning = warning;
    }

    // Getters and Setters
    public String getDrugName() { return drugName; }
    public void setDrugName(String drugName) { this.drugName = drugName; }
    
    public double getDoseMg() { return doseMg; }
    public void setDoseMg(double doseMg) { this.doseMg = doseMg; }
    
    public double getVolumeMl() { return volumeMl; }
    public void setVolumeMl(double volumeMl) { this.volumeMl = volumeMl; }
    
    public boolean isSafe() { return isSafe; }
    public void setSafe(boolean safe) { isSafe = safe; }
    
    public String getWarning() { return warning; }
    public void setWarning(String warning) { this.warning = warning; }
}
