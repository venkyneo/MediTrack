package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.entity.Sex;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.interfaces.Searchable;
import com.airtribe.meditrack.util.DataStore;

import java.util.List;
import java.util.stream.Collectors;

/**
 * PatientService handles all business logic for Patient CRUD and search.
 * Uses DataStore<Patient> internally.
 * Implements Searchable for polymorphic search.
 */
public class PatientService implements Searchable<Patient> {

    private final DataStore<Patient> patientStore = new DataStore<>();

    // CREATE
    public Patient addPatient(String firstName, String lastName, String address,
                              int age, Sex sex, String medicalIssue, String bloodGroup) throws InvalidDataException {
        Patient patient = new Patient(firstName, lastName, address, age, sex, medicalIssue, bloodGroup);
        patientStore.add(patient);
        System.out.println("Patient added successfully: " + patient.getFullName() + " [" + patient.getId() + "]");
        return patient;
    }

    // READ
    @Override
    public Patient findById(String id) {
        Patient patient = patientStore.findById(id);
        if (patient == null) {
            System.out.println("No patient found with ID: " + id);
        }
        return patient;
    }

    @Override
    public List<Patient> findByName(String name) {
        return patientStore.findAll().stream()
                .filter(p -> p.getFullName().equalsIgnoreCase(name) ||
                             p.getFirstName().equalsIgnoreCase(name) ||
                             p.getLastName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    // Search by age — demonstrates overloading concept
    public List<Patient> findByAge(int age) {
        return patientStore.findAll().stream()
                .filter(p -> p.getAge() == age)
                .collect(Collectors.toList());
    }

    // UPDATE
    public void updatePatient(String id, String address, String medicalIssue) throws InvalidDataException {
        Patient patient = patientStore.findById(id);
        if (patient == null) {
            throw new InvalidDataException("Patient not found with ID: " + id);
        }
        if (address != null && !address.isEmpty()) patient.setAddress(address);
        if (medicalIssue != null && !medicalIssue.isEmpty()) patient.setMedicalIssue(medicalIssue);
        patientStore.update(patient);
        System.out.println("Patient updated: " + patient.getId());
    }

    // DELETE
    public void removePatient(String id) throws InvalidDataException {
        if (!patientStore.exists(id)) {
            throw new InvalidDataException("Patient not found with ID: " + id);
        }
        patientStore.remove(id);
        System.out.println("Patient removed: " + id);
    }

    public List<Patient> getAllPatients() {
        return patientStore.findAll();
    }

    public void displayAllPatients() {
        List<Patient> all = patientStore.findAll();
        if (all.isEmpty()) {
            System.out.println("No patients registered.");
            return;
        }
        System.out.println("\n===== ALL PATIENTS =====");
        all.forEach(p -> System.out.println(p));
        System.out.println("========================\n");
    }
}
