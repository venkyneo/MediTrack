package com.airtribe.meditrack.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Singleton IdGenerator using double-checked locking for thread safety.
 * Uses AtomicInteger for thread-safe counter increments.
 */
public class IdGenerator {

    // Lazy singleton instance
    private static volatile IdGenerator instance;

    // Eager example (commented out to show both concepts)
    // private static final IdGenerator EAGER_INSTANCE = new IdGenerator();

    private final AtomicInteger doctorCounter = new AtomicInteger(1);
    private final AtomicInteger patientCounter = new AtomicInteger(1);
    private final AtomicInteger appointmentCounter = new AtomicInteger(1);
    private final AtomicInteger billCounter = new AtomicInteger(1);

    private IdGenerator() {}

    // Lazy singleton with double-checked locking
    public static IdGenerator getInstance() {
        if (instance == null) {
            synchronized (IdGenerator.class) {
                if (instance == null) {
                    instance = new IdGenerator();
                }
            }
        }
        return instance;
    }

    public String getDoctorId() {
        return "DOC-" + doctorCounter.getAndIncrement();
    }

    public String getPatientId() {
        return "PAT-" + patientCounter.getAndIncrement();
    }

    public String getAppointmentId() {
        return "APT-" + appointmentCounter.getAndIncrement();
    }

    public String getBillId() {
        return "BILL-" + billCounter.getAndIncrement();
    }
}
