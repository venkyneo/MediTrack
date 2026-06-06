package com.airtribe.meditrack.entity;

/**
 * Immutable BillSummary class.
 * All fields are final, no setters, thread-safe by design.
 * Demonstrates immutability concept.
 */
public final class BillSummary {

    private final String billId;
    private final String patientName;
    private final String doctorName;
    private final double baseAmount;
    private final double taxAmount;
    private final double totalAmount;
    private final String generatedAt;

    public BillSummary(String billId, String patientName, String doctorName,
                       double baseAmount, double taxAmount, double totalAmount, String generatedAt) {
        this.billId = billId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.baseAmount = baseAmount;
        this.taxAmount = taxAmount;
        this.totalAmount = totalAmount;
        this.generatedAt = generatedAt;
    }

    public String getBillId() { return billId; }
    public String getPatientName() { return patientName; }
    public String getDoctorName() { return doctorName; }
    public double getBaseAmount() { return baseAmount; }
    public double getTaxAmount() { return taxAmount; }
    public double getTotalAmount() { return totalAmount; }
    public String getGeneratedAt() { return generatedAt; }

    @Override
    public String toString() {
        return String.format("BillSummary[%s] | %s | Dr.%s | Total: ₹%.2f | At: %s",
                billId, patientName, doctorName, totalAmount, generatedAt);
    }
}
