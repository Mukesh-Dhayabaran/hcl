package org.example;

import org.example.model.*;
import org.example.service.*;
import org.example.exception.*;
import org.example.util.InputValidator;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    private static final AssetService assetService = new AssetService();
    private static final AssignmentService assignmentService =
            new AssignmentService(assetService);

    public static void main(String[] args) {

        boolean running = true;

        System.out.println("==================================");
        System.out.println("     IT ASSET MANAGEMENT SYSTEM");
        System.out.println("==================================");

        while (running) {
            System.out.println("\n1. Asset Registration");
            System.out.println("2. Asset Assignment and Return");
            System.out.println("3. View All Assets");
            System.out.println("4. View All Assignments");
            System.out.println("0. Exit");

            int choice = readInteger("Enter choice: ");

            try {
                switch (choice) {
                    case 1 -> registerAsset();
                    case 2 -> assignmentMenu();
                    case 3 -> viewAssets();
                    case 4 -> viewAssignments();
                    case 0 -> {
                        running = false;
                        System.out.println("Thank you for using IAMS!");
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (AssetNotFoundException |
                     DuplicateAssetException |
                     AssetUnavailableException |
                     IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        scanner.close();
    }

    private static void registerAsset() {

        System.out.println("\n--- Asset Registration ---");
        System.out.println("1. Computer");
        System.out.println("2. Furniture");

        int type = readInteger("Select asset type: ");

        int id = readInteger("Enter asset ID: ");
        String tag = readText("Enter asset tag: ");
        String name = readText("Enter asset name: ");
        double cost = readAmount("Enter purchase cost: ");

        Asset asset;

        switch (type) {
            case 1 -> {
                String processor = readText("Enter processor: ");
                int ram = readInteger("Enter RAM (GB): ");
                int storage = readInteger("Enter storage (GB): ");

                asset = new ComputerAsset(
                        id, tag, name, cost, processor, ram, storage);
            }

            case 2 -> {
                String material = readText("Enter material: ");
                String category = readText("Enter furniture category: ");

                asset = new FurnitureAsset(
                        id, tag, name, cost, material, category);
            }

            default -> {
                System.out.println("Invalid asset type.");
                return;
            }
        }

        assetService.registerAsset(asset);

        System.out.println("Asset registered successfully!");
    }

    private static void assignmentMenu()
            throws AssetNotFoundException {

        System.out.println("\n--- Asset Assignment ---");
        System.out.println("1. Assign Asset");
        System.out.println("2. Return Asset");
        System.out.println("3. View Active Assignments");
        System.out.println("4. Add Pending Asset Request");
        System.out.println("5. Process Next Pending Request");

        int choice = readInteger("Enter choice: ");

        switch (choice) {
            case 1 -> {
                int assetId = readInteger("Enter asset ID: ");
                int employeeId = readInteger("Enter employee ID: ");

                assignmentService.assignAsset(assetId, employeeId);

                System.out.println("Asset assigned successfully!");
            }

            case 2 -> {
                int assetId = readInteger("Enter asset ID: ");

                assignmentService.returnAsset(assetId);

                System.out.println("Asset returned successfully!");
            }

            case 3 -> viewActiveAssignments();

            case 4 -> {
                int assetId = readInteger("Enter asset ID: ");
                assignmentService.addPendingRequest(assetId);
                System.out.println("Request added to queue.");
            }
            case 5 -> {
                int employeeId = readInteger("Enter employee ID: ");

                var nextRequest = assignmentService.getNextPendingRequest();

                if (nextRequest.isPresent()) {
                    assignmentService.assignAsset(nextRequest.get(), employeeId);
                    System.out.println("Pending request processed successfully!");
                } else {
                    System.out.println("No pending requests.");
                }
            }

            default -> System.out.println("Invalid choice.");
        }
    }

    private static void viewAssets()
            throws AssetNotFoundException {

        System.out.println("\n--- Registered Assets ---");

        if (assetService.getAllAssets().isEmpty()) {
            System.out.println("No assets registered.");
            return;
        }

        for (Asset asset : assetService.getAllAssets()) {
            System.out.println("\n-------------------------");
            asset.displayDetails();
        }
    }

    private static void viewAssignments() {

        System.out.println("\n--- Assignment History ---");

        if (assignmentService.getAllAssignments().isEmpty()) {
            System.out.println("No assignment records found.");
            return;
        }

        assignmentService.getAllAssignments()
                .forEach(Assignment::displayDetails);
    }

    private static void viewActiveAssignments() {

        System.out.println("\n--- Active Assignments ---");

        if (assignmentService.getActiveAssignments().isEmpty()) {
            System.out.println("No active assignments.");
            return;
        }

        assignmentService.getActiveAssignments()
                .forEach(Assignment::displayDetails);
    }

    private static int readInteger(String message) {
        System.out.print(message);
        return InputValidator.readPositiveInteger(scanner.nextLine());
    }

    private static double readAmount(String message) {
        System.out.print(message);
        return InputValidator.readPositiveAmount(scanner.nextLine());
    }

    private static String readText(String message) {
        System.out.print(message);
        String value = scanner.nextLine().trim();

        if (value.isEmpty()) {
            throw new IllegalArgumentException(
                    "Input cannot be empty");
        }

        return value;
    }
}
