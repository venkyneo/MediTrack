package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.interfaces.Identifiable;
import com.airtribe.meditrack.interfaces.Payable;
import com.airtribe.meditrack.util.IdGenerator;

/**
 * Bill entity implementing Payable interface.
 * Demonstrates interface implementation and billing logic.
 */
public class Bill implements Identifiable, Payable {

    private final String id;
    private final Appointment appointment;
    private final double baseAmount;
    private final double taxAmount;
    private final double totalAmount;

    public Bill(Appointment appointment) {
        this.id = IdGenerator.getInstance().getBillId();
        this.appointment = appointment;
        this.baseAmount = appointment.getDoctor().getConsultationFee();
        this.taxAmount = baseAmount * Constants.TAX_RATE;
        this.totalAmount = baseAmount + taxAmount;
    }

    @Override
    public String getId() { return id; }

    public Appointment getAppointment() { return appointment; }
    public double getBaseAmount() { return baseAmount; }
    public double getTaxAmount() { return taxAmount; }

    @Override
    public double calculateTotal() { return totalAmount; }

    @Override
    public void generateBill() {
        System.out.println("\n========== BILL ==========");
        System.out.println("Bill ID     : " + id);
        System.out.println("Patient     : " + appointment.getPatient().getFullName());
        System.out.println("Doctor      : Dr. " + appointment.getDoctor().getFullName());
        System.out.println("Appointment : " + appointment.getId());
        System.out.println("--------------------------");
        System.out.printf("Base Amount : ₹%.2f%n", baseAmount);
        System.out.printf("Tax (18%%)   : ₹%.2f%n", taxAmount);
        System.out.printf("Total       : ₹%.2f%n", totalAmount);
        System.out.println("==========================\n");
    }

    @Override
    public String toString() {
        return String.format("Bill[%s] | Patient: %s | Total: ₹%.2f",
                id, appointment.getPatient().getFullName(), totalAmount);
    }
}
