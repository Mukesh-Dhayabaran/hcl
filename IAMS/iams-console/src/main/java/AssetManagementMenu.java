import java.util.Scanner;

public class AssetManagementMenu {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        do {
            System.out.println("\n===== IT ASSET MANAGEMENT SYSTEM =====");
            System.out.println("1. Register an Asset");
            System.out.println("2. Request an Asset");
            System.out.println("3. Assign / Return an Asset");
            System.out.println("4. Check Duplicate Assignment");
            System.out.println("5. Record Asset Maintenance");
            System.out.println("6. Track Software Licences");
            System.out.println("7. Check Warranty Expiry");
            System.out.println("8. View Depreciation Report");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine().trim();

            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("Selected: Register an Asset (FR1)");
                    break;

                case 2:
                    System.out.println("Selected: Request an Asset (FR2)");
                    break;

                case 3:
                    System.out.println("Selected: Assign / Return an Asset (FR3)");
                    break;

                case 4:
                    System.out.println("Selected: Check Duplicate Assignment (FR4)");
                    break;

                case 5:
                    System.out.println("Selected: Record Asset Maintenance (FR5)");
                    break;

                case 6:
                    System.out.println("Selected: Track Software Licences (FR6)");
                    break;

                case 7:
                    System.out.println("Selected: Check Warranty Expiry (FR7)");
                    break;

                case 8:
                    System.out.println("Selected: View Depreciation Report (FR8)");
                    break;

                case 0:
                    System.out.println("Exiting IT Asset Management System. Goodbye!");
                    break;

                default:
                    System.out.println(
                            "Invalid choice! Please select an option from 0 to 8."
                    );
            }

        } while (choice != 0);

        scanner.close();
    }
}