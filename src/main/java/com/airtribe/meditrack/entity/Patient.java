package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;

/**
 * Patient extends Person.
 * Implements Cloneable for deep copy demonstration.
 */
public class Patient extends Person implements Cloneable {

    private String medicalIssue;
    private String bloodGroup;

    public Patient(String firstName, String lastName, String address, int age, Sex sex,
                   String medicalIssue, String bloodGroup) throws InvalidDataException {
        super(IdGenerator.getInstance().getPatientId(), firstName, lastName, address, age, sex);
        Validator.validateNotNull(medicalIssue, "Medical issue");
        this.medicalIssue = medicalIssue;
        this.bloodGroup = bloodGroup;
    }

    public String getMedicalIssue() { return medicalIssue; }
    public String getBloodGroup() { return bloodGroup; }

    public void setMedicalIssue(String medicalIssue) throws InvalidDataException {
        Validator.validateNotNull(medicalIssue, "Medical issue");
        this.medicalIssue = medicalIssue;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    /**
     * Deep copy — clones the Patient object.
     * Demonstrates deep vs shallow copy concept.
     */
    @Override
    public Patient clone() {
        try {
            return (Patient) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning failed", e);
        }
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Issue: %s | Blood Group: %s",
                medicalIssue, bloodGroup != null ? bloodGroup : "N/A");
    }
}
