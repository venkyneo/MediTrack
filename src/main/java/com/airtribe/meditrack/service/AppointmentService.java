package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.DataStore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AppointmentService manages appointment lifecycle.
 * Handles create, view, cancel, and billing.
 */
public class AppointmentService {

    private final DataStore<Appointment> appointmentStore = new DataStore<>();
    private final DataStore<Bill> billStore = new DataStore<>();

    // CREATE
    public Appointment bookAppointment(Patient patient, Doctor doctor,
                                       LocalDateTime dateTime, String reason) throws InvalidDataException {
        if (patient == null) throw new InvalidDataException("Patient cannot be null.");
        if (doctor == null) throw new InvalidDataException("Doctor cannot be null.");
        if (dateTime == null) throw new InvalidDataException("Date/time cannot be null.");

        Appointment appointment = new Appointment(patient, doctor, dateTime, reason);
        appointment.setStatus(AppointmentStatus.CONFIRMED);
        appointmentStore.add(appointment);
        System.out.println("Appointment booked: " + appointment.getId());
        return appointment;
    }

    // READ
    public Appointment findById(String id) throws AppointmentNotFoundException {
        Appointment appointment = appointmentStore.findById(id);
        if (appointment == null) {
            throw new AppointmentNotFoundException("Appointment not found: " + id);
        }
        return appointment;
    }

    // CANCEL
    public void cancelAppointment(String id) throws AppointmentNotFoundException {
        Appointment appointment = findById(id);
        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            System.out.println("Appointment already cancelled.");
            return;
        }
        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentStore.update(appointment);
        System.out.println("Appointment cancelled: " + id);
    }

    // Get appointments by patient
    public List<Appointment> getAppointmentsByPatient(String patientId) {
        return appointmentStore.findAll().stream()
                .filter(a -> a.getPatient().getId().equals(patientId))
                .collect(Collectors.toList());
    }

    // Get appointments by doctor
    public List<Appointment> getAppointmentsByDoctor(String doctorId) {
        return appointmentStore.findAll().stream()
                .filter(a -> a.getDoctor().getId().equals(doctorId))
                .collect(Collectors.toList());
    }

    // BILLING
    public Bill generateBill(String appointmentId) throws AppointmentNotFoundException {
        Appointment appointment = findById(appointmentId);
        Bill bill = new Bill(appointment);
        billStore.add(bill);
        bill.generateBill();
        return bill;
    }

    public void displayAllAppointments() {
        List<Appointment> all = appointmentStore.findAll();
        if (all.isEmpty()) {
            System.out.println("No appointments found.");
            return;
        }
        System.out.println("\n===== ALL APPOINTMENTS =====");
        all.forEach(a -> System.out.println(a));
        System.out.println("============================\n");
    }

    // Stream analytics — appointments per doctor
    public void displayAppointmentStats() {
        System.out.println("\n===== APPOINTMENT STATS =====");
        appointmentStore.findAll().stream()
                .collect(Collectors.groupingBy(a -> a.getDoctor().getFullName(), Collectors.counting()))
                .forEach((doctor, count) -> System.out.println("Dr. " + doctor + ": " + count + " appointments"));
        System.out.println("=============================\n");
    }
}
