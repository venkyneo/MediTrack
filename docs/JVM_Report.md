# JVM Report — MediTrack

## 1. Class Loader
The Class Loader is responsible for loading `.class` files into the JVM at runtime. It works in three phases:
- **Loading**: Reads the `.class` bytecode file from disk/network
- **Linking**: Verifies bytecode, prepares static variables, resolves symbolic references
- **Initialization**: Executes static blocks and initializes static variables

In MediTrack, when `Main.java` starts, the ClassLoader loads `Person`, `Doctor`, `Patient`, etc. The `static` block in `Person` and `Constants` demonstrates class initialization order.

## 2. Runtime Data Areas

| Area | Description | MediTrack Example |
|------|-------------|-------------------|
| **Heap** | Stores all object instances | `new Patient(...)`, `new Doctor(...)` objects live here |
| **Stack** | Stores method call frames, local variables | Each method call (e.g., `addPatient()`) creates a stack frame |
| **Method Area** | Stores class metadata, static variables, bytecode | `IdGenerator.instance` (static) lives here |
| **PC Register** | Points to the current instruction being executed | One per thread |
| **Native Method Stack** | Used for native (non-Java) method calls | Used internally by JVM |

## 3. Execution Engine
The Execution Engine reads bytecode and executes it. It contains:
- **Interpreter**: Reads and executes bytecode line by line (slower, immediate)
- **JIT Compiler**: Compiles frequently-used bytecode into native machine code (faster over time)
- **Garbage Collector**: Automatically reclaims memory from unreachable objects

## 4. JIT Compiler vs Interpreter

| | Interpreter | JIT Compiler |
|---|---|---|
| Speed | Slower — interprets each instruction | Faster — compiles hot code to native |
| Startup | Fast startup | Slight warmup time |
| Use case | Code run once | Frequently executed code (loops, etc.) |

In MediTrack, the main menu loop runs repeatedly — JIT would optimize this hot path over time.

## 5. "Write Once, Run Anywhere"
Java compiles source `.java` files to `.class` bytecode (platform-independent). The JVM on each OS (Windows, Linux, macOS) interprets/compiles this same bytecode to native machine instructions. This means MediTrack compiled on Windows runs identically on Linux without recompilation.

```
MediTrack.java → [javac] → MediTrack.class → [JVM on any OS] → runs!
```
