package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Sex;
import com.airtribe.meditrack.entity.Specialization;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.interfaces.Searchable;
import com.airtribe.meditrack.util.DataStore;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DoctorService handles all business logic for Doctor CRUD and search.
 */
public class DoctorService implements Searchable<Doctor> {

    private final DataStore<Doctor> doctorStore = new DataStore<>();

    // CREATE
    public Doctor addDoctor(String firstName, String lastName, String address,
                            int age, Sex sex, Specialization specialization,
                            int experience, double consultationFee) throws InvalidDataException {
        Doctor doctor = new Doctor(firstName, lastName, address, age, sex, specialization, experience, consultationFee);
        doctorStore.add(doctor);
        System.out.println("Doctor added: Dr. " + doctor.getFullName() + " [" + doctor.getId() + "]");
        return doctor;
    }

    // READ
    @Override
    public Doctor findById(String id) {
        Doctor doctor = doctorStore.findById(id);
        if (doctor == null) {
            System.out.println("No doctor found with ID: " + id);
        }
        return doctor;
    }

    @Override
    public List<Doctor> findByName(String name) {
        return doctorStore.findAll().stream()
                .filter(d -> d.getFullName().equalsIgnoreCase(name) ||
                             d.getFirstName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    // Search by specialization — demonstrates overloading
    public List<Doctor> findBySpecialization(Specialization specialization) {
        return doctorStore.findAll().stream()
                .filter(d -> d.getSpecialization() == specialization)
                .collect(Collectors.toList());
    }

    // UPDATE
    public void updateDoctor(String id, Integer experience, Double fee) throws InvalidDataException {
        Doctor doctor = doctorStore.findById(id);
        if (doctor == null) throw new InvalidDataException("Doctor not found: " + id);
        if (experience != null) doctor.setExperience(experience);
        if (fee != null) doctor.setConsultationFee(fee);
        doctorStore.update(doctor);
        System.out.println("Doctor updated: " + id);
    }

    // DELETE
    public void removeDoctor(String id) throws InvalidDataException {
        if (!doctorStore.exists(id)) throw new InvalidDataException("Doctor not found: " + id);
        doctorStore.remove(id);
        System.out.println("Doctor removed: " + id);
    }

    public List<Doctor> getAllDoctors() {
        return doctorStore.findAll();
    }

    // Java Streams — average consultation fee
    public double getAverageConsultationFee() {
        return doctorStore.findAll().stream()
                .mapToDouble(Doctor::getConsultationFee)
                .average()
                .orElse(0.0);
    }

    public void displayAllDoctors() {
        List<Doctor> all = doctorStore.findAll();
        if (all.isEmpty()) {
            System.out.println("No doctors registered.");
            return;
        }
        System.out.println("\n===== ALL DOCTORS =====");
        all.forEach(d -> System.out.println(d));
        System.out.println("=======================\n");
    }
}
