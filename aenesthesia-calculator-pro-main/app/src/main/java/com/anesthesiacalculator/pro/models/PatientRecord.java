package com.anesthesiacalculator.pro.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Model class for saving complete patient records to Firebase
 * Contains patient info, selected drugs, and calculated results
 */
public class PatientRecord implements Serializable {
    private String recordId;
    private String patientName;
    private int patientAge;
    private double patientWeight;
    private String procedureType;
    private Date dateCreated;
    private List<String> selectedDrugNames;
    private List<DosageResult> dosageResults;
    private String notes;

    // Default constructor required for Firebase
    public PatientRecord() {
        this.selectedDrugNames = new java.util.ArrayList<>();
        this.dosageResults = new java.util.ArrayList<>();
    }

    public PatientRecord(Patient patient, List<Drug> selectedDrugs, List<DosageResult> results) {
        this.patientName = patient.getName();
        this.patientAge = patient.getAge();
        this.patientWeight = patient.getWeight();
        this.procedureType = patient.getProcedureType();
        this.dateCreated = new Date();
        this.dosageResults = results;
        
        // Extract drug names from selected drugs
        if (selectedDrugs != null) {
            this.selectedDrugNames = new java.util.ArrayList<>();
            for (Drug drug : selectedDrugs) {
                this.selectedDrugNames.add(drug.getDrugName());
            }
        }
    }

    // Getters and Setters
    public String getRecordId() { return recordId; }
    public void setRecordId(String recordId) { this.recordId = recordId; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public int getPatientAge() { return patientAge; }
    public void setPatientAge(int patientAge) { this.patientAge = patientAge; }

    public double getPatientWeight() { return patientWeight; }
    public void setPatientWeight(double patientWeight) { this.patientWeight = patientWeight; }

    public String getProcedureType() { return procedureType; }
    public void setProcedureType(String procedureType) { this.procedureType = procedureType; }

    public Date getDateCreated() { return dateCreated; }
    public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }

    public List<String> getSelectedDrugNames() { return selectedDrugNames; }
    public void setSelectedDrugNames(List<String> selectedDrugNames) { this.selectedDrugNames = selectedDrugNames; }

    public List<DosageResult> getDosageResults() { return dosageResults; }
    public void setDosageResults(List<DosageResult> dosageResults) { this.dosageResults = dosageResults; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
