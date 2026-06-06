# Setup Instructions — MediTrack

## Prerequisites
- Java JDK 17 or above
- IntelliJ IDEA (Community or Ultimate)

## Step 1: Install Java JDK
1. Download JDK 17+ from https://adoptium.net or https://www.oracle.com/java/technologies/downloads/
2. Run the installer
3. Set `JAVA_HOME` environment variable to JDK install path
4. Add `%JAVA_HOME%\bin` to system PATH
5. Verify: open terminal and run `java -version`

## Step 2: Open Project in IntelliJ IDEA
1. Open IntelliJ IDEA
2. Click **File → Open**
3. Navigate to the extracted `MediTrack` folder and click **OK**
4. IntelliJ will detect it as a Java project

## Step 3: Configure JDK in IntelliJ
1. Go to **File → Project Structure → Project**
2. Set **SDK** to your installed JDK 17+
3. Set **Language Level** to 17 or above
4. Click **Apply → OK**

## Step 4: Run the Application
1. Navigate to `src/main/java/com/airtribe/meditrack/Main.java`
2. Right-click → **Run 'Main.main()'**
3. The console menu will appear

## Step 5: Run Tests
1. Navigate to `src/main/java/com/airtribe/meditrack/test/TestRunner.java`
2. Right-click → **Run 'TestRunner.main()'**
3. View PASS/FAIL results in the console

## Project Structure
```
MediTrack/
├── src/main/java/com/airtribe/meditrack/
│   ├── Main.java                    ← Entry point
│   ├── constants/Constants.java
│   ├── entity/                      ← Person, Doctor, Patient, Appointment, Bill, BillSummary
│   ├── service/                     ← PatientService, DoctorService, AppointmentService
│   ├── util/                        ← IdGenerator, Validator, DataStore, DateUtil
│   ├── exception/                   ← Custom exceptions
│   ├── interfaces/                  ← Searchable, Payable, Identifiable
│   └── test/TestRunner.java
└── docs/
    ├── JVM_Report.md
    ├── Setup_Instructions.md
    └── Design_Decisions.md
```
