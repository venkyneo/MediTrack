package com.airtribe.meditrack.constants;

public class Constants {
    public static final double TAX_RATE = 0.18;
    public static final String PATIENT_FILE = "data/patients.csv";
    public static final String DOCTOR_FILE = "data/doctors.csv";
    public static final String APPOINTMENT_FILE = "data/appointments.csv";
    public static final int MIN_AGE = 0;
    public static final int MAX_AGE = 150;
    public static final int MIN_EXPERIENCE = 0;
    public static final int MAX_EXPERIENCE = 60;
    public static final String APP_NAME = "MediTrack - Clinic & Appointment Management System";
    public static final String VERSION = "1.0.0";

    // Static initialization block
    static {
        System.out.println("MediTrack Constants initialized.");
    }

    private Constants() {
        // Utility class, no instantiation
    }
}
