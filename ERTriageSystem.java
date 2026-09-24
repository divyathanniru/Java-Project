import java.util.Scanner;

public class ERTriageSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String[] levels = {"Critical", "Urgent", "Non-Urgent"};
        int[] patients = new int[3];
        int totalPatients;

        System.out.println("=== Hospital Emergency Room & Triage System ===");
        System.out.print("Enter total number of patients: ");
        totalPatients = sc.nextInt();

        for (int i = 0; i < totalPatients; i++) {
            System.out.println("\nPatient " + (i + 1) + " - Select triage level:");
            for (int j = 0; j < levels.length; j++) {
                System.out.println((j + 1) + ". " + levels[j]);
            }
            System.out.print("Enter level (1-3): ");
            int level = sc.nextInt();

            if (level >= 1 && level <= 3) {
                patients[level - 1]++;
            } else {
                System.out.println("Invalid level!");
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
    }
}

