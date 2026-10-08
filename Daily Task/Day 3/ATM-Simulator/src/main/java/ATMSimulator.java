import java.util.Scanner;

public class ATMSimulator {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int correctPin = 1234;
        int maxAttempts = 3;
        int attempts = 0;
        boolean authenticated = false;

        String environment = System.getProperty("app.environment","UNKNOWN");

        System .out.println("============================");
        System .out.println("ATM Simulator");
        System.out.println("Environment : " + environment);

        // PIN Authentication
        while (attempts < maxAttempts) {


            System.out.print("Enter your PIN: ");
            int enteredPin = scanner.nextInt();

            attempts++;

            if (enteredPin == correctPin) {
                authenticated = true;
                System.out.println("PIN verified successfully.");
                break;
            } else {
                System.out.println("Incorrect PIN.");

                if (attempts < maxAttempts) {
                    System.out.println("Please try again.");
                }
            }
        }

        // Account lock
        if (!authenticated) {
            System.out.println("Account locked.");
            scanner.close();
            return;
        }

        double balance = 5000.00;

        String[] transactions = {"Initial balance : ₹5000.00"};

        int choice;

        // ATM Menu
        do {

            System.out.println();
            System.out.println("===== ATM MENU =====");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Mini Statement");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    System.out.println("Current Balance: ₹" + balance);
                    break;

                case 2:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = scanner.nextDouble();

                    if (depositAmount <= 0) {
                        System.out.println("Invalid deposit amount.");
                        continue;
                    }

                    balance += depositAmount;

                    System.out.println(
                            "Deposit successful. New balance: ₹" + balance
                    );
                    break;

                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawalAmount = scanner.nextDouble();

                    if (withdrawalAmount <= 0) {
                        System.out.println("Invalid withdrawal amount.");
                        continue;
                    }

                    if (withdrawalAmount > balance) {
                        System.out.println("Insufficient balance.");
                        continue;
                    }

                    balance -= withdrawalAmount;

                    System.out.println(
                            "Withdrawal successful. New balance: ₹" + balance
                    );
                    break;

                case 4:
                    System.out.println("==================");
                    System.out.println("Mini Statement");
                    System.out.println("==================");
                    for (String transaction : transactions)
                        System.out.println(transaction);
                    break;

                case 5:
                    System.out.println("Thank you for using the ATM.");
                    System .out.print("============================");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 5);

        scanner.close();
    }
}