package com.airtribe.meditrack;

import com.airtribe.meditrack.constants.Constants;
import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;
import com.airtribe.meditrack.util.DateUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Main entry point for MediTrack CLI application.
 * Menu-driven console UI.
 */
public class Main {

    private static final PatientService patientService = new PatientService();
    private static final DoctorService doctorService = new DoctorService();
    private static final AppointmentService appointmentService = new AppointmentService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("===========================================");
        System.out.println("   Welcome to " + Constants.APP_NAME);
        System.out.println("   Version: " + Constants.VERSION);
        System.out.println("===========================================\n");

        loadSampleData();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1 -> patientMenu();
                case 2 -> doctorMenu();
                case 3 -> appointmentMenu();
                case 4 -> billingMenu();
                case 5 -> analyticsMenu();
                case 0 -> {
                    System.out.println("Thank you for using MediTrack. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
        scanner.close();
    }

    // ===================== MENUS =====================

    private static void printMainMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("1. Patient Management");
        System.out.println("2. Doctor Management");
        System.out.println("3. Appointment Management");
        System.out.println("4. Billing");
        System.out.println("5. Analytics");
        System.out.println("0. Exit");
        System.out.println("================================");
    }

    // ===================== PATIENT MENU =====================
    private static void patientMenu() {
        System.out.println("\n--- Patient Management ---");
        System.out.println("1. Add Patient");
        System.out.println("2. View All Patients");
        System.out.println("3. Search Patient by ID");
        System.out.println("4. Search Patient by Name");
        System.out.println("5. Update Patient");
        System.out.println("6. Remove Patient");
        System.out.println("0. Back");
        int choice = readInt("Choice: ");

        try {
            switch (choice) {
                case 1 -> addPatient();
                case 2 -> patientService.displayAllPatients();
                case 3 -> {
                    String id = readString("Enter Patient ID: ");
                    Patient p = patientService.findById(id);
                    if (p != null) System.out.println(p);
                }
                case 4 -> {
                    String name = readString("Enter name: ");
                    List<Patient> results = patientService.findByName(name);
                    if (results.isEmpty()) System.out.println("No patients found.");
                    else results.forEach(System.out::println);
                }
                case 5 -> updatePatient();
                case 6 -> {
                    String id = readString("Enter Patient ID to remove: ");
                    patientService.removePatient(id);
                }
                case 0 -> {}
                default -> System.out.println("Invalid choice.");
            }
        } catch (InvalidDataException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void addPatient() throws InvalidDataException {
        System.out.println("\n-- Add New Patient --");
        String firstName = readString("First Name: ");
        String lastName  = readString("Last Name: ");
        String address   = readString("Address: ");
        int age          = readInt("Age: ");
        Sex sex          = readSex();
        String issue     = readString("Medical Issue: ");
        String blood     = readString("Blood Group (optional, press Enter to skip): ");
        patientService.addPatient(firstName, lastName, address, age, sex, issue, blood.isEmpty() ? null : blood);
    }

    private static void updatePatient() throws InvalidDataException {
        String id      = readString("Patient ID to update: ");
        String address = readString("New Address (Enter to skip): ");
        String issue   = readString("New Medical Issue (Enter to skip): ");
        patientService.updatePatient(id,
                address.isEmpty() ? null : address,
                issue.isEmpty() ? null : issue);
    }

    // ===================== DOCTOR MENU =====================
    private static void doctorMenu() {
        System.out.println("\n--- Doctor Management ---");
        System.out.println("1. Add Doctor");
        System.out.println("2. View All Doctors");
        System.out.println("3. Search Doctor by ID");
        System.out.println("4. Search by Specialization");
        System.out.println("5. Update Doctor");
        System.out.println("6. Remove Doctor");
        System.out.println("0. Back");
        int choice = readInt("Choice: ");

        try {
            switch (choice) {
                case 1 -> addDoctor();
                case 2 -> doctorService.displayAllDoctors();
                case 3 -> {
                    String id = readString("Doctor ID: ");
                    Doctor d = doctorService.findById(id);
                    if (d != null) System.out.println(d);
                }
                case 4 -> {
                    System.out.println("Specializations: " + java.util.Arrays.toString(Specialization.values()));
                    String spec = readString("Enter specialization: ");
                    try {
                        List<Doctor> results = doctorService.findBySpecialization(Specialization.valueOf(spec.toUpperCase()));
                        if (results.isEmpty()) System.out.println("No doctors found.");
                        else results.forEach(System.out::println);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid specialization.");
                    }
                }
                case 5 -> {
                    String id  = readString("Doctor ID to update: ");
                    String exp = readString("New Experience (Enter to skip): ");
                    String fee = readString("New Fee (Enter to skip): ");
                    doctorService.updateDoctor(id,
                            exp.isEmpty() ? null : Integer.parseInt(exp),
                            fee.isEmpty() ? null : Double.parseDouble(fee));
                }
                case 6 -> {
                    String id = readString("Doctor ID to remove: ");
                    doctorService.removeDoctor(id);
                }
                case 0 -> {}
                default -> System.out.println("Invalid choice.");
            }
        } catch (InvalidDataException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void addDoctor() throws InvalidDataException {
        System.out.println("\n-- Add New Doctor --");
        String firstName = readString("First Name: ");
        String lastName  = readString("Last Name: ");
        String address   = readString("Address: ");
        int age          = readInt("Age: ");
        Sex sex          = readSex();
        System.out.println("Specializations: " + java.util.Arrays.toString(Specialization.values()));
        String specStr   = readString("Specialization: ");
        Specialization spec;
        try {
            spec = Specialization.valueOf(specStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid specialization. Defaulting to GENERAL_MEDICINE.");
            spec = Specialization.GENERAL_MEDICINE;
        }
        int experience   = readInt("Years of Experience: ");
        double fee       = Double.parseDouble(readString("Consultation Fee: "));
        doctorService.addDoctor(firstName, lastName, address, age, sex, spec, experience, fee);
    }

    // ===================== APPOINTMENT MENU =====================
    private static void appointmentMenu() {
        System.out.println("\n--- Appointment Management ---");
        System.out.println("1. Book Appointment");
        System.out.println("2. View All Appointments");
        System.out.println("3. View Appointments by Patient");
        System.out.println("4. View Appointments by Doctor");
        System.out.println("5. Cancel Appointment");
        System.out.println("0. Back");
        int choice = readInt("Choice: ");

        try {
            switch (choice) {
                case 1 -> bookAppointment();
                case 2 -> appointmentService.displayAllAppointments();
                case 3 -> {
                    String pid = readString("Patient ID: ");
                    appointmentService.getAppointmentsByPatient(pid).forEach(System.out::println);
                }
                case 4 -> {
                    String did = readString("Doctor ID: ");
                    appointmentService.getAppointmentsByDoctor(did).forEach(System.out::println);
                }
                case 5 -> {
                    String id = readString("Appointment ID to cancel: ");
                    appointmentService.cancelAppointment(id);
                }
                case 0 -> {}
                default -> System.out.println("Invalid choice.");
            }
        } catch (InvalidDataException | AppointmentNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void bookAppointment() throws InvalidDataException, AppointmentNotFoundException {
        String patientId = readString("Patient ID: ");
        Patient patient = patientService.findById(patientId);
        if (patient == null) { System.out.println("Patient not found."); return; }

        String doctorId = readString("Doctor ID: ");
        Doctor doctor = doctorService.findById(doctorId);
        if (doctor == null) { System.out.println("Doctor not found."); return; }

        String dateStr = readString("Date & Time (dd-MM-yyyy HH:mm): ");
        LocalDateTime dateTime;
        try {
            dateTime = DateUtil.parse(dateStr);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format. Use dd-MM-yyyy HH:mm");
            return;
        }
        String reason = readString("Reason: ");
        appointmentService.bookAppointment(patient, doctor, dateTime, reason);
    }

    // ===================== BILLING MENU =====================
    private static void billingMenu() {
        System.out.println("\n--- Billing ---");
        String appointmentId = readString("Enter Appointment ID to generate bill: ");
        try {
            appointmentService.generateBill(appointmentId);
        } catch (AppointmentNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ===================== ANALYTICS MENU =====================
    private static void analyticsMenu() {
        System.out.println("\n--- Analytics ---");
        System.out.printf("Average Consultation Fee: ₹%.2f%n", doctorService.getAverageConsultationFee());
        appointmentService.displayAppointmentStats();
    }

    // ===================== SAMPLE DATA =====================
    private static void loadSampleData() {
        System.out.println("Loading sample data...");
        try {
            Doctor d1 = doctorService.addDoctor("Rajesh", "Sharma", "Mumbai", 45, Sex.MALE, Specialization.CARDIOLOGY, 15, 800.0);
            Doctor d2 = doctorService.addDoctor("Priya", "Mehta", "Thane", 38, Sex.FEMALE, Specialization.DERMATOLOGY, 10, 600.0);
            Doctor d3 = doctorService.addDoctor("Anil", "Patil", "Navi Mumbai", 52, Sex.MALE, Specialization.ORTHOPEDICS, 22, 700.0);

            Patient p1 = patientService.addPatient("Venkatesh", "Rao", "Kalyan", 30, Sex.MALE, "Chest pain", "O+");
            Patient p2 = patientService.addPatient("Sunita", "Desai", "Dombivli", 25, Sex.FEMALE, "Skin rash", "A+");
            Patient p3 = patientService.addPatient("Ramesh", "Kumar", "Ulhasnagar", 40, Sex.MALE, "Back pain", "B+");

            appointmentService.bookAppointment(p1, d1, LocalDateTime.now().plusDays(1), "Routine checkup");
            appointmentService.bookAppointment(p2, d2, LocalDateTime.now().plusDays(2), "Skin consultation");
            appointmentService.bookAppointment(p3, d3, LocalDateTime.now().plusDays(3), "Knee pain");

            System.out.println("Sample data loaded successfully!\n");
        } catch (InvalidDataException e) {
            System.out.println("Sample data error: " + e.getMessage());
        }
    }

    // ===================== HELPERS =====================
    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int readInt(String prompt) {
        System.out.print(prompt);
        try {
            int val = Integer.parseInt(scanner.nextLine().trim());
            return val;
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Defaulting to 0.");
            return 0;
        }
    }

    private static Sex readSex() {
        System.out.print("Sex (MALE/FEMALE/OTHER): ");
        try {
            return Sex.valueOf(scanner.nextLine().trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid sex. Defaulting to OTHER.");
            return Sex.OTHER;
        }
    }
}
