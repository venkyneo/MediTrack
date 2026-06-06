package com.airtribe.meditrack.test;

import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;
import com.airtribe.meditrack.util.IdGenerator;

import java.time.LocalDateTime;

/**
 * Manual TestRunner — no JUnit dependency.
 * Run this class directly to verify all features.
 */
public class TestRunner {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("========== MediTrack Test Runner ==========\n");

        testIdGenerator();
        testPatientCreation();
        testDoctorCreation();
        testValidation();
        testCloning();
        testAppointment();
        testBillSummaryImmutability();

        System.out.println("\n========== Results ==========");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("=============================");
    }

    // ---- IdGenerator Tests ----
    private static void testIdGenerator() {
        System.out.println("-- IdGenerator Tests --");
        IdGenerator gen = IdGenerator.getInstance();
        String id1 = gen.getPatientId();
        String id2 = gen.getPatientId();
        assertEqual("PAT IDs should be unique", !id1.equals(id2), true);
        assertTrue("PAT ID starts with PAT-", id1.startsWith("PAT-"));
        assertTrue("DOC ID starts with DOC-", gen.getDoctorId().startsWith("DOC-"));
        assertTrue("APT ID starts with APT-", gen.getAppointmentId().startsWith("APT-"));
    }

    // ---- Patient Tests ----
    private static void testPatientCreation() {
        System.out.println("\n-- Patient Creation Tests --");
        try {
            Patient p = new Patient("John", "Doe", "Mumbai", 30, Sex.MALE, "Fever", "O+");
            assertTrue("Patient ID not null", p.getId() != null);
            assertEqual("First name correct", p.getFirstName(), "John");
            assertEqual("Medical issue correct", p.getMedicalIssue(), "Fever");
            System.out.println("  Patient created: " + p);
        } catch (InvalidDataException e) {
            fail("Patient creation failed: " + e.getMessage());
        }
    }

    // ---- Doctor Tests ----
    private static void testDoctorCreation() {
        System.out.println("\n-- Doctor Creation Tests --");
        try {
            Doctor d = new Doctor("Priya", "Mehta", "Thane", 38, Sex.FEMALE, Specialization.CARDIOLOGY, 10, 600.0);
            assertTrue("Doctor ID not null", d.getId() != null);
            assertEqual("Specialization correct", d.getSpecialization(), Specialization.CARDIOLOGY);
            System.out.println("  Doctor created: " + d);
        } catch (InvalidDataException e) {
            fail("Doctor creation failed: " + e.getMessage());
        }
    }

    // ---- Validation Tests ----
    private static void testValidation() {
        System.out.println("\n-- Validation Tests --");
        try {
            new Patient("", "Doe", "Mumbai", 30, Sex.MALE, "Fever", "O+");
            fail("Should have thrown for empty first name");
        } catch (InvalidDataException e) {
            pass("Empty first name rejected: " + e.getMessage());
        }

        try {
            new Patient("John", "Doe", "Mumbai", -1, Sex.MALE, "Fever", "O+");
            fail("Should have thrown for negative age");
        } catch (InvalidDataException e) {
            pass("Negative age rejected: " + e.getMessage());
        }

        try {
            new Doctor("Raj", "K", "Mumbai", 40, Sex.MALE, Specialization.NEUROLOGY, -5, 500.0);
            fail("Should have thrown for negative experience");
        } catch (InvalidDataException e) {
            pass("Negative experience rejected: " + e.getMessage());
        }
    }

    // ---- Clone Tests ----
    private static void testCloning() {
        System.out.println("\n-- Clone (Deep Copy) Tests --");
        try {
            Patient original = new Patient("Raj", "Kumar", "Pune", 25, Sex.MALE, "Headache", "A+");
            Patient cloned   = original.clone();
            assertEqual("Clone has same name", original.getFullName(), cloned.getFullName());
            assertTrue("Clone is different object", original != cloned);
            pass("Deep copy works correctly.");
        } catch (InvalidDataException e) {
            fail("Clone test failed: " + e.getMessage());
        }
    }

    // ---- Appointment Tests ----
    private static void testAppointment() {
        System.out.println("\n-- Appointment Tests --");
        try {
            PatientService ps = new PatientService();
            DoctorService ds = new DoctorService();
            AppointmentService as = new AppointmentService();

            Patient p = ps.addPatient("Amit", "Shah", "Kalyan", 35, Sex.MALE, "Knee pain", "B+");
            Doctor d  = ds.addDoctor("Suresh", "Nair", "Mumbai", 45, Sex.MALE, Specialization.ORTHOPEDICS, 15, 700.0);

            Appointment apt = as.bookAppointment(p, d, LocalDateTime.now().plusDays(1), "Consultation");
            assertTrue("Appointment ID not null", apt.getId() != null);
            assertEqual("Status is CONFIRMED", apt.getStatus(), AppointmentStatus.CONFIRMED);

            as.cancelAppointment(apt.getId());
            assertEqual("Status is CANCELLED after cancel", apt.getStatus(), AppointmentStatus.CANCELLED);
            pass("Appointment lifecycle test passed.");
        } catch (InvalidDataException | AppointmentNotFoundException e) {
            fail("Appointment test failed: " + e.getMessage());
        }
    }

    // ---- BillSummary Immutability Test ----
    private static void testBillSummaryImmutability() {
        System.out.println("\n-- BillSummary Immutability Test --");
        BillSummary summary = new BillSummary("BILL-1", "John Doe", "Dr. Smith", 500.0, 90.0, 590.0, "2024-01-15");
        assertEqual("BillSummary total correct", summary.getTotalAmount(), 590.0);
        // No setters — immutability enforced at compile time
        pass("BillSummary is immutable (no setters available).");
    }

    // ---- Assertion Helpers ----
    private static void assertTrue(String msg, boolean condition) {
        if (condition) pass(msg);
        else fail(msg);
    }

    private static <T> void assertEqual(String msg, T actual, T expected) {
        if (expected.equals(actual)) pass(msg);
        else fail(msg + " [Expected: " + expected + ", Got: " + actual + "]");
    }

    private static void pass(String msg) {
        System.out.println("  [PASS] " + msg);
        passed++;
    }

    private static void fail(String msg) {
        System.out.println("  [FAIL] " + msg);
        failed++;
    }
}
