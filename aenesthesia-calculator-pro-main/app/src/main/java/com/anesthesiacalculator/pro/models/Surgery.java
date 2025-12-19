package com.anesthesiacalculator.pro.models;

import java.io.Serializable;

public class Surgery implements Serializable {
    private long id;
    private String surgeryName;

    public Surgery(long id, String surgeryName) {
        this.id = id;
        this.surgeryName = surgeryName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSurgeryName() {
        return surgeryName;
    }

    public void setSurgeryName(String surgeryName) {
        this.surgeryName = surgeryName;
    }
}


