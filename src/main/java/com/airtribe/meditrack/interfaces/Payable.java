package com.airtribe.meditrack.interfaces;

public interface Payable {
    double calculateTotal();
    void generateBill();

    default String formatAmount(double amount) {
        return String.format("₹%.2f", amount);
    }
}
