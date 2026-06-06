# Design Decisions — MediTrack

## 1. Inheritance: Person → Doctor / Patient
`Doctor` and `Patient` both extend `Person` because they **are** persons. This avoids field duplication and demonstrates the IS-A relationship. Common fields (id, name, address, age, sex) live in `Person`.

## 2. IdGenerator — Singleton with Double-Checked Locking
`IdGenerator` is a lazy Singleton with double-checked locking for thread safety. `AtomicInteger` ensures counter increments are atomic, preventing duplicate IDs under concurrent access.

## 3. DataStore<T extends Identifiable>
A generic `DataStore<T>` eliminates CRUD duplication across `Patient`, `Doctor`, and `Appointment` storage. The `Identifiable` interface bounds `T` to guarantee `getId()` is available.

## 4. Validator — Centralized Validation
All validation rules live in `Validator`. Setters and constructors call `Validator` methods, ensuring rules are enforced at both creation and update time. Changing a rule requires editing one place.

## 5. Enums over Strings
`Sex`, `Specialization`, and `AppointmentStatus` are enums. This prevents invalid string values at compile time and makes code more readable and type-safe.

## 6. Immutable BillSummary
`BillSummary` uses `final` fields and has no setters. Once created, it cannot be modified — making it inherently thread-safe and safe to share across threads.

## 7. Deep Copy — Cloneable
`Patient` and `Appointment` implement `Cloneable`. `Appointment.clone()` also clones the nested `Patient` object, demonstrating true deep copy semantics.

## 8. Interfaces — Searchable, Payable, Identifiable
- `Identifiable`: contract for `getId()`, enables `DataStore<T>`
- `Searchable<T>`: contract for search operations with a default helper method
- `Payable`: contract for billing with a default currency formatter

## 9. Java Streams
Used in `DoctorService` (average fee), `AppointmentService` (stats, filtering by patient/doctor), and search operations — demonstrating Java 8+ functional style alongside traditional OOP.
