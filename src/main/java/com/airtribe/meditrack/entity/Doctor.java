package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;

/**
 * Doctor extends Person — demonstrates inheritance and constructor chaining.
 */
public class Doctor extends Person {

    private Specialization specialization;
    private int experience;
    private double consultationFee;

    public Doctor(String firstName, String lastName, String address, int age, Sex sex,
                  Specialization specialization, int experience, double consultationFee) throws InvalidDataException {
        super(IdGenerator.getInstance().getDoctorId(), firstName, lastName, address, age, sex);
        Validator.validateNotNull(specialization, "Specialization");
        Validator.validateExperience(experience);
        Validator.validateFee(consultationFee);

        this.specialization = specialization;
        this.experience = experience;
        this.consultationFee = consultationFee;
    }

    public Specialization getSpecialization() { return specialization; }
    public int getExperience() { return experience; }
    public double getConsultationFee() { return consultationFee; }

    public void setSpecialization(Specialization specialization) throws InvalidDataException {
        Validator.validateNotNull(specialization, "Specialization");
        this.specialization = specialization;
    }

    public void setExperience(int experience) throws InvalidDataException {
        Validator.validateExperience(experience);
        this.experience = experience;
    }

    public void setConsultationFee(double consultationFee) throws InvalidDataException {
        Validator.validateFee(consultationFee);
        this.consultationFee = consultationFee;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(" | Specialization: %s | Experience: %d yrs | Fee: ₹%.2f",
                specialization, experience, consultationFee);
    }
}
