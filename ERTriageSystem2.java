import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

class Patient {                                             // Module 4: class, private fields, constructor
    private String name;
    private int level;
    private static int count = 0;                           // static member

    Patient(String name, int level) { this.name = name.trim(); this.level = level; count++; }
    String getName() { return name; }                       // accessors (encapsulation)
    int getLevel() { return level; }
    static int getCount() { return count; }
    public String toString() { return name + " (Level " + level + ")"; }
}

class CriticalPatient extends Patient {                     // Module 4: inheritance + overriding
    CriticalPatient(String name) { super(name, 1); }
    @Override public String toString() { return "CRITICAL: " + super.toString(); }
}

public class ERTriageSystem2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] levels = {"Critical", "Urgent", "Non-Urgent"};
        int[] patients = new int[3];
        List<Patient> list = new ArrayList<>();             // Module 6: typed collection
        int totalPatients;

        System.out.println("=== Hospital Emergency Room & Triage System ===");
        System.out.print("Enter total number of patients: ");
        totalPatients = sc.nextInt();

        for (int i = 0; i < totalPatients; i++) {
            try {                                           // Module 4: exception handling
                System.out.print("\nEnter name of patient " + (i + 1) + ": ");
                String name = sc.next();
                System.out.println("Select triage level:");
                for (int j = 0; j < levels.length; j++) {
                    System.out.println((j + 1) + ". " + levels[j]);
                }
                System.out.print("Enter level (1-3): ");
                int level = sc.nextInt();

                if (level >= 1 && level <= 3) {
                    patients[level - 1]++;
                    list.add(level == 1 ? new CriticalPatient(name) : new Patient(name, level));
                } else {
                    System.out.println("Invalid level!");
                    i--;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number for the level!");
                sc.next();                                  // clear the wrong input
                i--;
            }
        }

        System.out.println("\n=== TRIAGE REPORT ===");
        int maxPatients = patients[0];
        int busiest = 0;
        int priority = -1;

        for (int i = 0; i < patients.length; i++) {
            System.out.println(levels[i] + ": " + patients[i] + " patients");
            if (priority == -1 && patients[i] > 0) {
                priority = i;
            }
            if (patients[i] > maxPatients) {
                maxPatients = patients[i];
                busiest = i;
            }
        }

        if (priority == -1) {
            System.out.println("\nNo patients waiting.");
        } else {
            System.out.println("\nTreat first: " + levels[priority] + " (" + patients[priority] + " waiting)");
            System.out.println("Busiest category: " + levels[busiest] + " with " + maxPatients + " patients");

            sc.close();
        }

        // ---------- Module 5: Strings & File I/O ----------
        list.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));    // sort objects with a Comparator
        StringBuilder sb = new StringBuilder("Patients A-Z: ");                // mutable string
        for (Patient p : list) sb.append(p.getName().toUpperCase()).append(" ");
        System.out.println("\n" + sb.toString().trim());

        Path file = Paths.get("patients.txt");
        try {
            List<String> lines = list.stream().map(p -> p.getName() + "," + p.getLevel()).collect(Collectors.toList());
            Files.write(file, lines);                                          // write text file
            for (String line : Files.readAllLines(file)) {                     // read it back line by line
                String[] parts = line.split(",");                              // split the record
                System.out.println("File record -> name: " + parts[0] + ", level: " + parts[1]);
            }
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }

        // ---------- Module 6: Streams & Lambdas ----------
        System.out.println("\nTotal registered: " + Patient.getCount());
        System.out.println("Critical patients: " + list.stream().filter(p -> p.getLevel() == 1).count());
        System.out.println("Count by level: " + list.stream()
                .collect(Collectors.groupingBy(p -> levels[p.getLevel() - 1], Collectors.counting())));
        list.forEach(p -> System.out.println(p));                              // uses toString()
    }
}