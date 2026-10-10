import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import model.Asset;
import model.Assignment;
import model.AssetRequest;
import model.Maintenance;
import model.SoftwareLicense;

public class AssetManagementMenu {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Asset> assets = new ArrayList<>();

        int nextAssetId = 1;

        ArrayList<Assignment> assignments = new ArrayList<>();
        int nextAssignmentId = 1;

        ArrayList<AssetRequest> assetRequests = new ArrayList<>();
        int nextRequestId = 1;

        ArrayList<Maintenance> maintenanceRecords = new ArrayList<>();
        int nextMaintenanceId = 1;

        ArrayList<SoftwareLicense> softwareLicenses = new ArrayList<>();
        int nextLicenseId = 1;

        int choice = 0;

        do {
            System.out.println("\n===== IT ASSET MANAGEMENT SYSTEM =====");
            System.out.println("1. Register an Asset");
            System.out.println("2. Request an Asset");
            System.out.println("3. Assign / Return an Asset");
            System.out.println("4. Check Asset Assignment");
            System.out.println("5. Record Asset Maintenance");
            System.out.println("6. Track Software Licences");
            System.out.println("7. Check Warranty Expiry");
            System.out.println("8. View Depreciation Report");
            System.out.println("9. View All Registered Assets");
            System.out.println("10. Complete Asset Maintenance");
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

                case 1: {
                    System.out.println("\n--- Register an Asset ---");

                    System.out.print("Enter asset tag: ");
                    String assetTag = scanner.nextLine().trim();

                    System.out.print("Enter asset name: ");
                    String assetName = scanner.nextLine().trim();

                    System.out.print("Enter category: ");
                    String category = scanner.nextLine().trim();

                    if (assetTag.isEmpty()
                            || assetName.isEmpty()
                            || category.isEmpty()) {
                        System.out.println(
                                "Error: Asset tag, name, and category cannot be empty."
                        );
                        break;
                    }

                    boolean duplicateTag = false;

                    for (Asset existingAsset : assets) {
                        if (existingAsset.getAssetTag()
                                .equalsIgnoreCase(assetTag)) {
                            duplicateTag = true;
                            break;
                        }
                    }

                    if (duplicateTag) {
                        System.out.println(
                                "Error: An asset with this tag already exists."
                        );
                        break;
                    }

                    Asset asset = new Asset(
                            nextAssetId,
                            assetTag,
                            assetName,
                            category,
                            "AVAILABLE"
                    );

                    assets.add(asset);
                    nextAssetId++;

                    System.out.println(
                            "Asset registered successfully!"
                    );
                    System.out.println("Asset ID: " + asset.getAssetId());
                    System.out.println("Asset Tag: " + asset.getAssetTag());
                    System.out.println("Asset Name: " + asset.getAssetName());
                    System.out.println("Category: " + asset.getCategory());
                    System.out.println("Status: " + asset.getStatus());

                    break;
                }

                case 2: {
                    System.out.println("\n--- Request an Asset ---");

                    boolean availableAssetFound = false;

                    for (Asset asset : assets) {
                        if ("AVAILABLE".equals(asset.getStatus())) {
                            System.out.println(
                                    "Asset ID: " + asset.getAssetId()
                                            + " | Tag: " + asset.getAssetTag()
                                            + " | Name: " + asset.getAssetName()
                                            + " | Category: " + asset.getCategory()
                            );
                            availableAssetFound = true;
                        }
                    }

                    if (!availableAssetFound) {
                        System.out.println("No assets are currently available.");
                        break;
                    }

                    System.out.print("Enter Asset ID to request: ");
                    String assetIdInput = scanner.nextLine().trim();

                    int requestedAssetId;

                    try {
                        requestedAssetId = Integer.parseInt(assetIdInput);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Asset ID. Enter a number.");
                        break;
                    }

                    Asset requestedAsset = null;

                    for (Asset asset : assets) {
                        if (asset.getAssetId() == requestedAssetId) {
                            requestedAsset = asset;
                            break;
                        }
                    }

                    if (requestedAsset == null) {
                        System.out.println("Asset not found.");
                        break;
                    }

                    if (!"AVAILABLE".equals(requestedAsset.getStatus())) {
                        System.out.println("This asset is not available for requests.");
                        break;
                    }

                    System.out.print("Enter Employee ID: ");
                    String employeeIdInput = scanner.nextLine().trim();

                    int employeeId;

                    try {
                        employeeId = Integer.parseInt(employeeIdInput);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Employee ID. Enter a number.");
                        break;
                    }

                    if (employeeId <= 0) {
                        System.out.println("Employee ID must be greater than zero.");
                        break;
                    }

                    AssetRequest request = new AssetRequest(
                            nextRequestId,
                            requestedAssetId,
                            employeeId,
                            LocalDate.now(),
                            "PENDING"
                    );

                    assetRequests.add(request);
                    nextRequestId++;

                    System.out.println("Asset request submitted successfully!");
                    System.out.println("Request ID: " + request.getRequestId());
                    System.out.println("Asset: " + requestedAsset.getAssetName());
                    System.out.println("Employee ID: " + request.getEmployeeId());
                    System.out.println("Request Date: " + request.getRequestDate());
                    System.out.println("Request Status: " + request.getStatus());

                    break;
                }



                case 3: {
                    System.out.println("\n--- Asset Assignment / Return ---");
                    System.out.println("1. Assign Asset");
                    System.out.println("2. Return Asset");
                    System.out.println("0. Back to Main Menu");
                    System.out.print("Enter your choice: ");

                    String actionInput = scanner.nextLine().trim();

                    if (actionInput.equals("1")) {
                        System.out.print("Enter Asset ID: ");
                        String idInput = scanner.nextLine().trim();

                        int assetId;

                        try {
                            assetId = Integer.parseInt(idInput);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Asset ID. Enter a number.");
                            break;
                        }

                        Asset selectedAsset = null;

                        for (Asset asset : assets) {
                            if (asset.getAssetId() == assetId) {
                                selectedAsset = asset;
                                break;
                            }
                        }

                        if (selectedAsset == null) {
                            System.out.println("Asset not found.");
                            break;
                        }

                        if (selectedAsset.getStatus().equals("ASSIGNED")) {
                            System.out.println("Asset is not available for assignment.");
                            break;
                        }

                        if (selectedAsset.getStatus().equals("MAINTENANCE")) {
                            System.out.println("Asset is under maintenance.");
                            break;
                        }

                        System.out.print("Enter Employee ID: ");
                        String employeeInput = scanner.nextLine().trim();

                        int employeeId;

                        try {
                            employeeId = Integer.parseInt(employeeInput);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Employee ID. Enter a number.");
                            break;
                        }

                        if (employeeId <= 0) {
                            System.out.println("Employee ID must be greater than zero.");
                            break;
                        }

                        Assignment assignment = new Assignment(
                                nextAssignmentId,
                                assetId,
                                employeeId,
                                LocalDate.now(),
                                null
                        );

                        assignments.add(assignment);
                        nextAssignmentId++;
                        selectedAsset.setStatus("ASSIGNED");

                        System.out.println("Asset assigned successfully!");
                        System.out.println("Assignment ID: " + assignment.getAssignmentId());
                        System.out.println("Asset: " + selectedAsset.getAssetName());
                        System.out.println("Employee ID: " + employeeId);
                        System.out.println("Assignment Date: " + assignment.getAssignmentDate());

                    } else if (actionInput.equals("2")) {
                        System.out.print("Enter Asset ID to return: ");
                        String idInput = scanner.nextLine().trim();

                        int assetId;

                        try {
                            assetId = Integer.parseInt(idInput);
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Asset ID. Enter a number.");
                            break;
                        }

                        Asset selectedAsset = null;

                        for (Asset asset : assets) {
                            if (asset.getAssetId() == assetId) {
                                selectedAsset = asset;
                                break;
                            }
                        }

                        if (selectedAsset == null) {
                            System.out.println("Asset not found.");
                            break;
                        }

                        Assignment activeAssignment = null;

                        for (Assignment assignment : assignments) {
                            if (assignment.getAssetId() == assetId
                                    && assignment.getReturnDate() == null) {
                                activeAssignment = assignment;
                                break;
                            }
                        }

                        if (activeAssignment == null) {
                            System.out.println("This asset has no active assignment.");
                            break;
                        }

                        activeAssignment.setReturnDate(LocalDate.now());
                        selectedAsset.setStatus("AVAILABLE");

                        System.out.println("Asset returned successfully!");
                        System.out.println("Asset: " + selectedAsset.getAssetName());
                        System.out.println("Return Date: " + activeAssignment.getReturnDate());

                    } else if (actionInput.equals("0")) {
                        System.out.println("Returning to main menu.");
                    } else {
                        System.out.println("Invalid choice. Select 0, 1, or 2.");
                    }

                    break;
                }




                case 4: {
                    System.out.println("\n--- Check Asset Assignment ---");

                    System.out.print("Enter Asset ID: ");
                    String assetIdInput = scanner.nextLine().trim();

                    int assetId;

                    try {
                        assetId = Integer.parseInt(assetIdInput);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Asset ID. Enter a number.");
                        break;
                    }

                    Asset selectedAsset = null;

                    for (Asset asset : assets) {
                        if (asset.getAssetId() == assetId) {
                            selectedAsset = asset;
                            break;
                        }
                    }

                    if (selectedAsset == null) {
                        System.out.println("Asset not found.");
                        break;
                    }

                    System.out.println("\n--- Asset Details ---");
                    System.out.println("Asset ID: " + selectedAsset.getAssetId());
                    System.out.println("Asset Tag: " + selectedAsset.getAssetTag());
                    System.out.println("Asset Name: " + selectedAsset.getAssetName());
                    System.out.println("Category: " + selectedAsset.getCategory());
                    System.out.println("Current Status: " + selectedAsset.getStatus());

                    Assignment activeAssignment = null;

                    for (Assignment assignment : assignments) {
                        if (assignment.getAssetId() == assetId
                                && assignment.getReturnDate() == null) {
                            activeAssignment = assignment;
                            break;
                        }
                    }

                    if (activeAssignment != null) {
                        System.out.println("\n--- Current Assignment ---");
                        System.out.println(
                                "Assignment ID: " + activeAssignment.getAssignmentId()
                        );
                        System.out.println(
                                "Employee ID: " + activeAssignment.getEmployeeId()
                        );
                        System.out.println(
                                "Assignment Date: " + activeAssignment.getAssignmentDate()
                        );
                        System.out.println("Assignment Status: ACTIVE");
                    } else {
                        System.out.println("\nNo active assignment found for this asset.");
                    }

                    break;
                }



                case 5: {
                    System.out.println("\n--- Record Asset Maintenance ---");

                    System.out.print("Enter Asset ID: ");
                    String assetIdInput = scanner.nextLine().trim();

                    int assetId;

                    try {
                        assetId = Integer.parseInt(assetIdInput);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Asset ID. Enter a number.");
                        break;
                    }

                    Asset selectedAsset = null;

                    for (Asset asset : assets) {
                        if (asset.getAssetId() == assetId) {
                            selectedAsset = asset;
                            break;
                        }
                    }

                    if (selectedAsset == null) {
                        System.out.println("Asset not found.");
                        break;
                    }

                    if ("DISPOSED".equals(selectedAsset.getStatus())) {
                        System.out.println("Disposed assets cannot undergo maintenance.");
                        break;
                    }

                    if ("MAINTENANCE".equals(selectedAsset.getStatus())) {
                        System.out.println("This asset is already under maintenance.");
                        break;
                    }

                    if ("ASSIGNED".equals(selectedAsset.getStatus())) {
                        System.out.println(
                                "Return the asset before recording maintenance."
                        );
                        break;
                    }

                    System.out.print("Enter maintenance description: ");
                    String description = scanner.nextLine().trim();

                    if (description.isEmpty()) {
                        System.out.println("Description cannot be empty.");
                        break;
                    }

                    Maintenance maintenance = new Maintenance(
                            nextMaintenanceId,
                            assetId,
                            description,
                            LocalDate.now(),
                            "IN_PROGRESS"
                    );

                    maintenanceRecords.add(maintenance);
                    nextMaintenanceId++;

                    selectedAsset.setStatus("MAINTENANCE");

                    System.out.println("Maintenance recorded successfully!");
                    System.out.println(
                            "Maintenance ID: " + maintenance.getMaintenanceId()
                    );
                    System.out.println(
                            "Asset: " + selectedAsset.getAssetName()
                    );
                    System.out.println(
                            "Description: " + maintenance.getDescription()
                    );
                    System.out.println(
                            "Maintenance Date: " + maintenance.getMaintenanceDate()
                    );
                    System.out.println(
                            "Maintenance Status: " + maintenance.getStatus()
                    );

                    break;
                }



                case 6: {
                    System.out.println("\n--- Track Software Licences ---");
                    System.out.println("1. Add Software Licence");
                    System.out.println("2. View Software Licences");
                    System.out.println("3. Allocate Licence Seat");
                    System.out.println("0. Back to Main Menu");
                    System.out.print("Enter your choice: ");

                    String choiceInput = scanner.nextLine().trim();
                    int licenseChoice;

                    try {
                        licenseChoice = Integer.parseInt(choiceInput);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid choice. Enter a number.");
                        break;
                    }

                    switch (licenseChoice) {
                        case 1: {
                            System.out.print("Enter software name: ");
                            String softwareName = scanner.nextLine().trim();

                            if (softwareName.isEmpty()) {
                                System.out.println("Software name cannot be empty.");
                                break;
                            }

                            System.out.print("Enter purchased seats: ");
                            String seatsInput = scanner.nextLine().trim();

                            int purchasedSeats;

                            try {
                                purchasedSeats = Integer.parseInt(seatsInput);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid seat count. Enter a number.");
                                break;
                            }

                            if (purchasedSeats <= 0) {
                                System.out.println("Purchased seats must be greater than zero.");
                                break;
                            }

                            SoftwareLicense license = new SoftwareLicense(
                                    nextLicenseId, softwareName, purchasedSeats
                            );

                            softwareLicenses.add(license);
                            System.out.println("Software licence added successfully.");
                            System.out.println("Licence ID: " + nextLicenseId);
                            nextLicenseId++;
                            break;
                        }

                        case 2: {
                            if (softwareLicenses.isEmpty()) {
                                System.out.println("No software licences registered.");
                                break;
                            }

                            System.out.println("\n--- Registered Software Licences ---");

                            for (SoftwareLicense license : softwareLicenses) {
                                System.out.println("Licence ID: " + license.getLicenseId());
                                System.out.println("Software: " + license.getSoftwareName());
                                System.out.println("Purchased Seats: "
                                        + license.getPurchasedSeats());
                                System.out.println("Used Seats: " + license.getUsedSeats());
                                System.out.println("Available Seats: "
                                        + license.getAvailableSeats());
                                System.out.println("-----------------------------");
                            }
                            break;
                        }

                        case 3: {
                            if (softwareLicenses.isEmpty()) {
                                System.out.println("No software licences registered.");
                                break;
                            }

                            System.out.println("\n--- Available Software Licences ---");

                            for (SoftwareLicense license : softwareLicenses) {
                                System.out.println(
                                        license.getLicenseId() + ". "
                                                + license.getSoftwareName()
                                                + " (Available: "
                                                + license.getAvailableSeats() + ")"
                                );
                            }

                            System.out.print("Enter Licence ID: ");
                            String idInput = scanner.nextLine().trim();

                            int licenseId;

                            try {
                                licenseId = Integer.parseInt(idInput);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid Licence ID. Enter a number.");
                                break;
                            }

                            SoftwareLicense selectedLicense = null;

                            for (SoftwareLicense license : softwareLicenses) {
                                if (license.getLicenseId() == licenseId) {
                                    selectedLicense = license;
                                    break;
                                }
                            }

                            if (selectedLicense == null) {
                                System.out.println("Software licence not found.");
                                break;
                            }

                            if (selectedLicense.allocateSeat()) {
                                System.out.println("Licence seat allocated successfully.");
                                System.out.println("Software: "
                                        + selectedLicense.getSoftwareName());
                                System.out.println("Available Seats: "
                                        + selectedLicense.getAvailableSeats());
                            } else {
                                System.out.println(
                                        "Allocation denied. No available licence seats."
                                );
                            }
                            break;
                        }

                        case 0:
                            System.out.println("Returning to main menu.");
                            break;

                        default:
                            System.out.println("Invalid choice.");
                    }

                    break;
                }


                case 7:
                    System.out.println(
                            "Warranty expiry checking is not implemented yet."
                    );
                    break;

                case 8:
                    System.out.println(
                            "Depreciation reporting is not implemented yet."
                    );
                    break;

                case 9:
                    System.out.println("\n--- Registered Assets ---");

                    if (assets.isEmpty()) {
                        System.out.println("No assets have been registered yet.");
                    } else {
                        for (Asset asset : assets) {
                            System.out.println("-------------------------");
                            System.out.println("Asset ID: " + asset.getAssetId());
                            System.out.println("Asset Tag: " + asset.getAssetTag());
                            System.out.println("Asset Name: " + asset.getAssetName());
                            System.out.println("Category: " + asset.getCategory());
                            System.out.println("Status: " + asset.getStatus());
                        }
                        System.out.println("-------------------------");
                    }
                    break;


                case 10: {
                    System.out.println("\n--- Complete Asset Maintenance ---");

                    boolean foundMaintenance = false;

                    for (Maintenance record : maintenanceRecords) {
                        if ("IN_PROGRESS".equals(record.getStatus())) {
                            Asset asset = null;

                            for (Asset item : assets) {
                                if (item.getAssetId() == record.getAssetId()) {
                                    asset = item;
                                    break;
                                }
                            }

                            if (asset != null) {
                                System.out.println(
                                        "Maintenance ID: " + record.getMaintenanceId()
                                );
                                System.out.println(
                                        "Asset ID: " + asset.getAssetId()
                                );
                                System.out.println(
                                        "Asset Name: " + asset.getAssetName()
                                );
                                System.out.println(
                                        "Description: " + record.getDescription()
                                );
                                System.out.println();
                                foundMaintenance = true;
                            }
                        }
                    }

                    if (!foundMaintenance) {
                        System.out.println("No pending maintenance records found.");
                        break;
                    }

                    System.out.print("Enter Maintenance ID to complete: ");
                    String maintenanceInput  = scanner.nextLine().trim();

                    int maintenanceId;

                    try {
                        maintenanceId = Integer.parseInt(maintenanceInput);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Maintenance ID. Enter a number.");
                        break;
                    }

                    Maintenance selectedRecord = null;

                    for (Maintenance record : maintenanceRecords) {

                        if (record.getMaintenanceId() == maintenanceId) {
                            selectedRecord = record;
                            break;
                        }
                    }

                    if (selectedRecord == null) {
                        System.out.println("Maintenance record not found.");
                        break;
                    }

                    if (!"IN_PROGRESS".equals(selectedRecord.getStatus())) {
                        System.out.println("This maintenance is already completed.");
                        break;
                    }

                    Asset selectedAsset = null;

                    for (Asset asset : assets) {
                        if (asset.getAssetId() == selectedRecord.getAssetId()) {
                            selectedAsset = asset;
                            break;
                        }
                    }

                    if (selectedAsset == null) {
                        System.out.println("Associated asset not found.");
                        break;
                    }

                    if (!"MAINTENANCE".equals(selectedAsset.getStatus())) {
                        System.out.println(
                                "Asset status is inconsistent. Maintenance cannot be completed."
                        );
                        break;
                    }

                    selectedRecord.setStatus("COMPLETED");
                    selectedAsset.setStatus("AVAILABLE");

                    System.out.println("Maintenance completed successfully!");
                    System.out.println("Maintenance ID: " + selectedRecord.getMaintenanceId());
                    System.out.println("Asset: " + selectedAsset.getAssetName());
                    System.out.println("Maintenance Status: " + selectedRecord.getStatus());
                    System.out.println("Asset Status: " + selectedAsset.getStatus());

                    break;
                }


                case 0:
                    System.out.println(
                            "Exiting IT Asset Management System. Goodbye!"
                    );
                    break;

                default:
                    System.out.println(
                            "Invalid choice! Please select an option from 0 to 9."
                    );
            }

        } while (choice != 0);

        scanner.close();
    }
}
