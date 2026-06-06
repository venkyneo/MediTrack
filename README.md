MediTrack - Clinic and Appointment Management System

MediTrack is a console-based Clinic and Appointment Management System built using Core Java.
It models real-world clinic operations including patient registration, doctor management,
appointment booking, and billing. The project demonstrates strong object-oriented design,
SOLID principles, and standard Java features.


PROJECT OVERVIEW

This system allows clinic staff to manage patients and doctors, book and cancel appointments,
generate bills, and search records. It is built entirely in Core Java with no external frameworks
or databases. All data is stored in memory during the session.


FEATURES

Patient Management
Register new patients with personal and medical details.
Search patients by ID, name, or age.
Update patient address and medical issue.
Remove patients from the system.

Doctor Management
Register doctors with specialization and consultation fee.
Search doctors by ID, name, or specialization.
Update doctor experience and fee.
Remove doctors from the system.

Appointment Management
Book appointments between a patient and a doctor.
View all appointments or filter by patient or doctor.
Cancel appointments with status update.

Billing
Generate a bill for any confirmed appointment.
Bill includes base consultation fee, 18 percent tax, and total amount.
Immutable bill summary is created after generation.

Analytics
View average consultation fee across all doctors.
View appointment count per doctor using Java Streams.


TECHNOLOGIES USED

Java 17 or above
Core Java only - no Spring, no Hibernate, no external libraries
IntelliJ IDEA as the recommended IDE
Git and GitHub for version control


PROJECT STRUCTURE

src/main/java/com/airtribe/meditrack/
    Main.java - Entry point with menu-driven console UI
    constants/
        Constants.java - Tax rate, file paths, app-wide config
    entity/
        Person.java - Abstract base class for all persons
        Doctor.java - Extends Person, adds specialization and fee
        Patient.java - Extends Person, adds medical issue and blood group
        Appointment.java - Links patient and doctor with date and status
        Bill.java - Billing logic with tax calculation
        BillSummary.java - Immutable summary of a generated bill
        Sex.java - Enum for gender
        Specialization.java - Enum for doctor specialization
        AppointmentStatus.java - Enum for appointment state
    service/
        PatientService.java - Business logic for patient CRUD and search
        DoctorService.java - Business logic for doctor CRUD and search
        AppointmentService.java - Appointment lifecycle and billing
    util/
        IdGenerator.java - Singleton for generating unique IDs
        DataStore.java - Generic storage class for any Identifiable entity
        Validator.java - Centralized validation for all inputs
        DateUtil.java - Date parsing and formatting utilities
    exception/
        InvalidDataException.java - Thrown for bad input data
        AppointmentNotFoundException.java - Thrown when appointment is missing
    interfaces/
        Identifiable.java - Contract for getId method
        Searchable.java - Contract for search operations
        Payable.java - Contract for billing operations
    test/
        TestRunner.java - Manual test runner without JUnit

docs/
    JVM_Report.md - JVM internals explanation
    Setup_Instructions.md - How to run the project
    Design_Decisions.md - Architecture and design choices


OBJECT ORIENTED CONCEPTS DEMONSTRATED

Inheritance
Person is the parent class. Doctor and Patient extend Person.
Common fields like id, name, address, age, and sex live in Person.
Subclasses add their own specific fields via constructor chaining using super.

Encapsulation
All fields are private. Getters are provided for all fields.
Setters exist only for fields that can change after creation.
The id field has no setter and cannot be changed once assigned.

Polymorphism
Search is overloaded in services - by ID, by name, and by age.
toString is overridden in Person, Doctor, Patient, Appointment, and Bill.
Searchable interface enables dynamic dispatch across services.

Abstraction
Person is an abstract class providing common structure.
Interfaces Searchable, Payable, and Identifiable define contracts.
Default methods in interfaces provide shared utility behavior.

Immutability
BillSummary is a fully immutable class.
All fields are final and no setters are provided.
Once created it cannot be modified making it thread-safe.

Cloning
Patient implements Cloneable for deep copy support.
Appointment clones itself and also clones the nested Patient object.
This demonstrates the difference between shallow and deep copy.

Enums
Sex, Specialization, and AppointmentStatus are defined as enums.
This prevents invalid string values and makes the code type-safe.

Generics
DataStore is a generic class bounded by the Identifiable interface.
It can store Patient, Doctor, or Appointment without code duplication.

Singleton Pattern
IdGenerator uses lazy initialization with double-checked locking.
AtomicInteger is used for thread-safe counter increments.
Only one instance exists throughout the application lifecycle.

Java Streams and Lambdas
Used in services to filter, group, and compute analytics.
Average consultation fee and appointments per doctor use stream operations.

Exception Handling
Custom exceptions InvalidDataException and AppointmentNotFoundException.
Validator throws exceptions centrally so services stay clean.
Try-catch blocks are used throughout service and main layers.


HOW TO RUN

1. Install JDK 17 or above
2. Open IntelliJ IDEA
3. Click File then Open and select the MediTrack folder
4. Go to File then Project Structure and set the SDK to JDK 17
5. Open Main.java inside com.airtribe.meditrack
6. Right-click and select Run Main.main()
7. The console menu will appear with sample data preloaded

To run tests open TestRunner.java and run it the same way.


SAMPLE DATA

The application loads sample data on startup including three doctors
across Cardiology, Dermatology, and Orthopedics specializations,
three patients from the Thane and Kalyan area, and three appointments
linking them. This lets you explore all features immediately without
manual data entry.


AUTHOR

Developed as part of the Airtribe Java mentorship program.
Project: MediTrack - Clinic and Appointment Management System.
