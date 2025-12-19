package com.anesthesiacalculator.pro.models;

import java.io.Serializable;
import java.util.Date;

public class Patient implements Serializable {
    private long id;
    private String name;
    private int age;
    private double weight;
    private String procedureType;
    private Date dateCreated;

    public Patient() {}

    public Patient(String name, int age, double weight, String procedureType) {
        this.name = name;
        this.age = age;
        this.weight = weight;
        this.procedureType = procedureType;
        this.dateCreated = new Date();
    }

    // Getters and Setters
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }
    
    public String getProcedureType() { return procedureType; }
    public void setProcedureType(String procedureType) { this.procedureType = procedureType; }
    
    public Date getDateCreated() { return dateCreated; }
    public void setDateCreated(Date dateCreated) { this.dateCreated = dateCreated; }
}
