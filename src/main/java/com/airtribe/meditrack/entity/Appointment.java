package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.interfaces.Identifiable;
import com.airtribe.meditrack.util.IdGenerator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Appointment entity linking Patient and Doctor.
 * Implements Cloneable for deep copy demonstration.
 */
public class Appointment implements Identifiable, Cloneable {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    private final String id;
    private Patient patient;
    private Doctor doctor;
    private LocalDateTime dateTime;
    private AppointmentStatus status;
    private String reason;

    public Appointment(Patient patient, Doctor doctor, LocalDateTime dateTime, String reason) {
        this.id = IdGenerator.getInstance().getAppointmentId();
        this.patient = patient;
        this.doctor = doctor;
        this.dateTime = dateTime;
        this.status = AppointmentStatus.PENDING;
        this.reason = reason;
    }

    @Override
    public String getId() { return id; }

    public Patient getPatient() { return patient; }
    public Doctor getDoctor() { return doctor; }
    public LocalDateTime getDateTime() { return dateTime; }
    public AppointmentStatus getStatus() { return status; }
    public String getReason() { return reason; }

    public void setStatus(AppointmentStatus status) { this.status = status; }
    public void setDateTime(LocalDateTime dateTime) { this.dateTime = dateTime; }
    public void setReason(String reason) { this.reason = reason; }

    /**
     * Deep clone — also clones nested Patient and Doctor references.
     */
    @Override
    public Appointment clone() {
        try {
            Appointment cloned = (Appointment) super.clone();
            cloned.patient = this.patient.clone();
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning failed", e);
        }
    }

    @Override
    public String toString() {
        return String.format("Appointment[%s] | Patient: %s | Doctor: Dr. %s | Date: %s | Status: %s | Reason: %s",
                id,
                patient.getFullName(),
                doctor.getFullName(),
                dateTime.format(FORMATTER),
                status,
                reason);
    }
}
