-- Anesthesia Calculator Pro - MySQL Database Schema
-- Create this database in PHPMyAdmin

CREATE DATABASE IF NOT EXISTS anesthesia_calculator;
USE anesthesia_calculator;

-- Table: surgeries
CREATE TABLE IF NOT EXISTS surgeries (
    id INT AUTO_INCREMENT PRIMARY KEY,
    surgery_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: drugs
CREATE TABLE IF NOT EXISTS drugs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    drug_name VARCHAR(255) NOT NULL,
    min_dose DECIMAL(10, 2) NOT NULL,
    max_dose DECIMAL(10, 2) NOT NULL,
    concentration DECIMAL(10, 2) NOT NULL,
    unit VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Table: patient_records
CREATE TABLE IF NOT EXISTS patient_records (
    id INT AUTO_INCREMENT PRIMARY KEY,
    record_id VARCHAR(100) UNIQUE NOT NULL,
    device_id VARCHAR(255),
    patient_name VARCHAR(255) NOT NULL,
    patient_age INT NOT NULL,
    patient_weight DECIMAL(10, 2) NOT NULL,
    procedure_type VARCHAR(255) NOT NULL,
    selected_drug_names TEXT,
    dosage_results LONGTEXT,
    notes TEXT,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_device_id (device_id),
    INDEX idx_record_id (record_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Insert sample surgeries
INSERT INTO surgeries (surgery_name) VALUES
('General Surgery'),
('Orthopedic Surgery'),
('Cardiac Surgery'),
('Neurosurgery'),
('Pediatric Surgery'),
('Gynecological Surgery'),
('Urological Surgery'),
('ENT Surgery'),
('Ophthalmic Surgery'),
('Plastic Surgery'),
('Vascular Surgery'),
('Thoracic Surgery'),
('Emergency Surgery'),
('Dental Surgery'),
('Bariatric Surgery');

-- Insert sample drugs
INSERT INTO drugs (drug_name, min_dose, max_dose, concentration, unit) VALUES
('Propofol', 1.5, 2.5, 10.0, 'mg/kg'),
('Fentanyl', 1.0, 3.0, 0.05, 'mcg/kg'),
('Morphine', 0.05, 0.2, 1.0, 'mg/kg'),
('Atracurium', 0.4, 0.6, 10.0, 'mg/kg'),
('Rocuronium', 0.6, 1.2, 10.0, 'mg/kg'),
('Vecuronium', 0.08, 0.12, 1.0, 'mg/kg'),
('Succinylcholine', 1.0, 1.5, 20.0, 'mg/kg'),
('Midazolam', 0.02, 0.1, 1.0, 'mg/kg'),
('Ketamine', 1.0, 2.0, 50.0, 'mg/kg'),
('Thiopental', 3.0, 5.0, 25.0, 'mg/kg'),
('Etomidate', 0.2, 0.4, 2.0, 'mg/kg'),
('Remifentanil', 0.1, 1.0, 0.05, 'mcg/kg/min'),
('Alfentanil', 10.0, 50.0, 0.5, 'mcg/kg'),
('Cisatracurium', 0.15, 0.2, 2.0, 'mg/kg'),
('Neostigmine', 0.04, 0.07, 0.5, 'mg/kg');
