package com.anesthesiacalculator.pro.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.anesthesiacalculator.pro.models.Patient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "anesthesia_calculator.db";
    private static final int DATABASE_VERSION = 1;

    // Table and column names
    private static final String TABLE_PATIENTS = "patients";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_PROCEDURE = "procedure_type";
    private static final String COLUMN_DATE = "date_created";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_PATIENTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_AGE + " INTEGER NOT NULL, " +
                COLUMN_WEIGHT + " REAL NOT NULL, " +
                COLUMN_PROCEDURE + " TEXT NOT NULL, " +
                COLUMN_DATE + " INTEGER NOT NULL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENTS);
        onCreate(db);
    }

    public long insertPatient(Patient patient) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        
        values.put(COLUMN_NAME, patient.getName());
        values.put(COLUMN_AGE, patient.getAge());
        values.put(COLUMN_WEIGHT, patient.getWeight());
        values.put(COLUMN_PROCEDURE, patient.getProcedureType());
        values.put(COLUMN_DATE, new Date().getTime());

        long id = db.insert(TABLE_PATIENTS, null, values);
        db.close();
        return id;
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.query(TABLE_PATIENTS, null, null, null, null, null, 
                COLUMN_DATE + " DESC");

        if (cursor.moveToFirst()) {
            do {
                Patient patient = new Patient();
                patient.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                patient.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                patient.setAge(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE)));
                patient.setWeight(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_WEIGHT)));
                patient.setProcedureType(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROCEDURE)));
                patient.setDateCreated(new Date(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DATE))));
                
                patients.add(patient);
            } while (cursor.moveToNext());
        }
        
        cursor.close();
        db.close();
        return patients;
    }
}
