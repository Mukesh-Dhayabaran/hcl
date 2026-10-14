import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Comparator;
import java.time.LocalDate;
import model.Asset;
import model.Assignment;
import model.AssetRequest;
import model.Maintenance;
import model.SoftwareLicense;
import model.Warranty;
import model.DepreciationReport;
import exception.ValidationException;
import exception.DuplicateAssetException;

public class AssetManagementMenu {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        List<Asset> assets = new ArrayList<>();

        Map<Integer, Asset> assetsById = new HashMap<>();

        Set<String> assetTags = new HashSet<>();

        int nextAssetId = 1;

        List<Assignment> assignments = new ArrayList<>();
        Map<Integer, Assignment> activeAssignmentsByAssetId = new HashMap<>();
        int nextAssignmentId = 1;


        List<AssetRequest> assetRequests = new ArrayList<>();

        // Maintains pending requests in FIFO order.
        Queue<AssetRequest> pendingRequests = new ArrayDeque<>();

        // Provides direct lookup of requests using their Request ID.
        Map<Integer, AssetRequest> requestsById = new HashMap<>();

        int nextRequestId = 1;


        List<Maintenance> maintenanceRecords = new ArrayList<>();
        Map<Integer, Maintenance> maintenanceById = new HashMap<>();
        int nextMaintenanceId = 1;

        List<SoftwareLicense> softwareLicenses = new ArrayList<>();
        Map<Integer, SoftwareLicense> licensesById = new HashMap<>();
        int nextLicenseId = 1;

        List<Warranty> warranties = new ArrayList<>();
        int nextWarrantyId = 1;

        Map<Integer, DepreciationReport> depreciationDetails = new HashMap<>();

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
            System.out.println("11. Process Pending Request");
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

                    try {
                        if (assetTag.isEmpty()
                                || assetName.isEmpty()
                                || category.isEmpty()) {
                            throw new ValidationException(
                                    "Asset tag, name, and category cannot be empty."
                            );
                        }

                        if (assetTags.contains(assetTag.toLowerCase())) {
                            throw new DuplicateAssetException(
                                    "An asset with this tag already exists: " + assetTag
                            );
                        }

                        Asset asset = new Asset(
                                nextAssetId,
                                assetTag,
                                assetName,
                                category,
                                "AVAILABLE"
                        );

                        assets.add(asset);
                        assetsById.put(asset.getAssetId(), asset);
                        assetTags.add(assetTag.toLowerCase());
                        nextAssetId++;

                        System.out.println("Asset registered successfully!");
                        System.out.println("Asset ID: " + asset.getAssetId());
                        System.out.println("Asset Tag: " + asset.getAssetTag());
                        System.out.println("Asset Name: " + asset.getAssetName());
                        System.out.println("Category: " + asset.getCategory());
                        System.out.println("Status: " + asset.getStatus());

                    } catch (ValidationException | DuplicateAssetException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

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

                    Asset requestedAsset = assetsById.get(requestedAssetId);

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
                    requestsById.put(request.getRequestId(), request);
                    pendingRequests.offer(request);
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

                        Asset selectedAsset = assetsById.get(assetId);

                        if (selectedAsset == null) {
                            System.out.println("Asset not found.");
                            break;
                        }

                        if (activeAssignmentsByAssetId.containsKey(assetId)
                                || "ASSIGNED".equals(selectedAsset.getStatus())) {
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
                        activeAssignmentsByAssetId.put(assetId, assignment);
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

                        Asset selectedAsset = assetsById.get(assetId);

                        if (selectedAsset == null) {
                            System.out.println("Asset not found.");
                            break;
                        }

                        Assignment activeAssignment = activeAssignmentsByAssetId.get(assetId);

                        if (activeAssignment == null) {
                            System.out.println("This asset has no active assignment.");
                            break;
                        }

                        activeAssignment.setReturnDate(LocalDate.now());
                        activeAssignmentsByAssetId.remove(assetId);
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

                    Asset selectedAsset = assetsById.get(assetId);

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

                    Assignment activeAssignment = activeAssignmentsByAssetId.get(assetId);

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

                    Asset selectedAsset = assetsById.get(assetId);

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
                    maintenanceById.put(maintenance.getMaintenanceId(), maintenance);
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
                            licensesById.put(license.getLicenseId(), license);
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

                            SoftwareLicense selectedLicense = licensesById.get(licenseId);

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



                case 7: {
                    System.out.println("\n--- Warranty Expiry Alerts ---");
                    System.out.println("1. Register Asset Warranty");
                    System.out.println("2. View Warranty Expiry Alerts");
                    System.out.println("0. Back to Main Menu");
                    System.out.print("Enter your choice: ");

                    String choiceInput = scanner.nextLine().trim();
                    int warrantyChoice;

                    try {
                        warrantyChoice = Integer.parseInt(choiceInput);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid choice. Enter a number.");
                        break;
                    }

                    switch (warrantyChoice) {
                        case 1: {
                            if (assets.isEmpty()) {
                                System.out.println("No assets registered yet.");
                                break;
                            }

                            System.out.println("\n--- Registered Assets ---");
                            for (Asset asset : assets) {
                                System.out.println(
                                        "Asset ID: " + asset.getAssetId()
                                                + " | " + asset.getAssetName()
                                                + " | Tag: " + asset.getAssetTag()
                                );
                            }

                            System.out.print("Enter Asset ID: ");
                            String assetIdInput = scanner.nextLine().trim();

                            int assetId;

                            try {
                                assetId = Integer.parseInt(assetIdInput);
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid Asset ID. Enter a number.");
                                break;
                            }

                            Asset selectedAsset = assetsById.get(assetId);

                            if (selectedAsset == null) {
                                System.out.println("Asset not found.");
                                break;
                            }

                            boolean warrantyExists = false;

                            for (Warranty warranty : warranties) {
                                if (warranty.getAssetId() == assetId) {
                                    warrantyExists = true;
                                    break;
                                }
                            }

                            if (warrantyExists) {
                                System.out.println(
                                        "A warranty is already registered for this asset."
                                );
                                break;
                            }

                            System.out.print("Enter warranty expiry date (YYYY-MM-DD): ");
                            String expiryInput = scanner.nextLine().trim();

                            LocalDate expiryDate;

                            try {
                                expiryDate = LocalDate.parse(expiryInput);
                            } catch (java.time.format.DateTimeParseException e) {
                                System.out.println(
                                        "Invalid date. Use the format YYYY-MM-DD."
                                );
                                break;
                            }

                            Warranty warranty = new Warranty(
                                    nextWarrantyId, assetId, expiryDate
                            );

                            warranties.add(warranty);

                            System.out.println("Warranty registered successfully.");
                            System.out.println("Warranty ID: " + nextWarrantyId);
                            System.out.println("Expiry Date: " + expiryDate);

                            nextWarrantyId++;
                            break;
                        }

                        case 2: {
                            if (warranties.isEmpty()) {
                                System.out.println("No warranties registered.");
                                break;
                            }

                            System.out.println("\n--- Warranty Expiry Report ---");

                            for (Warranty warranty : warranties) {

                                Asset linkedAsset = assetsById.get(warranty.getAssetId());

                                if (linkedAsset == null) {
                                    continue;
                                }

                                long daysRemaining = warranty.getDaysUntilExpiry();

                                System.out.println("\nAsset: "
                                        + linkedAsset.getAssetName());
                                System.out.println("Asset ID: "
                                        + warranty.getAssetId());
                                System.out.println("Expiry Date: "
                                        + warranty.getExpiryDate());

                                if (warranty.isExpired()) {
                                    System.out.println("Status: EXPIRED");
                                    System.out.println("Expired "
                                            + Math.abs(daysRemaining) + " day(s) ago");
                                } else if (warranty.isExpiringSoon()) {
                                    System.out.println("Status: EXPIRING SOON");
                                    System.out.println("Days remaining: "
                                            + daysRemaining);
                                } else {
                                    System.out.println("Status: ACTIVE");
                                    System.out.println("Days remaining: "
                                            + daysRemaining);
                                }
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





                case 8: {
                    if (assets.isEmpty()) {
                        System.out.println("No assets registered yet.");
                        break;
                    }

                    System.out.println("\n--- Registered Assets ---");
                    for (Asset asset : assets) {
                        System.out.println(
                                "Asset ID: " + asset.getAssetId()
                                        + " | " + asset.getAssetName()
                                        + " | Tag: " + asset.getAssetTag()
                        );
                    }

                    System.out.print("Enter Asset ID: ");
                    String assetIdInput = scanner.nextLine().trim();

                    int assetId;

                    try {
                        assetId = Integer.parseInt(assetIdInput);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Asset ID. Enter a number.");
                        break;
                    }

                    Asset selectedAsset = assetsById.get(assetId);

                    if (selectedAsset == null) {
                        System.out.println("Asset not found.");
                        break;
                    }

                    DepreciationReport report = depreciationDetails.get(assetId);

                    if (report == null) {
                        double purchaseCost;
                        double residualValue;
                        int usefulLifeYears;
                        int elapsedYears;

                        System.out.print("Enter purchase cost: ");
                        try {
                            purchaseCost = Double.parseDouble(
                                    scanner.nextLine().trim()
                            );
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid purchase cost.");
                            break;
                        }

                        if (!Double.isFinite(purchaseCost) || purchaseCost <= 0) {
                            System.out.println("Purchase cost must be greater than zero.");
                            break;
                        }

                        System.out.print("Enter residual value: ");
                        try {
                            residualValue = Double.parseDouble(
                                    scanner.nextLine().trim()
                            );
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid residual value.");
                            break;
                        }

                        if (!Double.isFinite(residualValue)
                                || residualValue < 0
                                || residualValue > purchaseCost) {
                            System.out.println(
                                    "Residual value must be between zero and purchase cost."
                            );
                            break;
                        }

                        System.out.print("Enter useful life in years: ");
                        try {
                            usefulLifeYears = Integer.parseInt(
                                    scanner.nextLine().trim()
                            );
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid useful life.");
                            break;
                        }

                        if (usefulLifeYears <= 0) {
                            System.out.println("Useful life must be greater than zero.");
                            break;
                        }

                        System.out.print("Enter elapsed years: ");
                        try {
                            elapsedYears = Integer.parseInt(
                                    scanner.nextLine().trim()
                            );
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid elapsed years.");
                            break;
                        }

                        if (elapsedYears < 0) {
                            System.out.println("Elapsed years cannot be negative.");
                            break;
                        }

                        report = new DepreciationReport(
                                assetId,
                                purchaseCost,
                                residualValue,
                                usefulLifeYears,
                                elapsedYears
                        );

                        depreciationDetails.put(assetId, report);
                    }

                    System.out.println("\n===== DEPRECIATION REPORT =====");
                    System.out.println("Asset Name: " + selectedAsset.getAssetName());
                    System.out.println("Asset Tag: " + selectedAsset.getAssetTag());
                    System.out.println("Method: Straight-Line Method");

                    System.out.printf(
                            "Purchase Cost: %.2f%n",
                            report.getPurchaseCost()
                    );
                    System.out.printf(
                            "Residual Value: %.2f%n",
                            report.getResidualValue()
                    );
                    System.out.println(
                            "Useful Life: " + report.getUsefulLifeYears() + " years"
                    );
                    System.out.println(
                            "Elapsed Years: " + report.getElapsedYears()
                    );

                    System.out.printf(
                            "Annual Depreciation: %.2f%n",
                            report.getAnnualDepreciation()
                    );
                    System.out.printf(
                            "Accumulated Depreciation: %.2f%n",
                            report.getAccumulatedDepreciation()
                    );
                    System.out.printf(
                            "Current Book Value: %.2f%n",
                            report.getCurrentValue()
                    );

                    break;
                }


                case 9: {
                    System.out.println("\n--- Registered Assets ---");

                    if (assets.isEmpty()) {
                        System.out.println("No assets have been registered yet.");
                        break;
                    }

                    System.out.println("\nSort assets by:");
                    System.out.println("1. Asset ID");
                    System.out.println("2. Asset Name");
                    System.out.print("Enter your choice: ");

                    int sortChoice;
                    try {
                        sortChoice = Integer.parseInt(scanner.nextLine().trim());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid sorting choice. Enter 1 or 2.");
                        break;
                    }

                    List<Asset> sortedAssets = new ArrayList<>(assets);

                    Comparator<Asset> sortById =
                            Comparator.comparingInt(Asset::getAssetId);

                    Comparator<Asset> sortByName =
                            Comparator.comparing(
                                    Asset::getAssetName,
                                    String.CASE_INSENSITIVE_ORDER
                            ).thenComparing(sortById);

                    switch (sortChoice) {
                        case 1:
                            sortedAssets.sort(sortById);
                            break;

                        case 2:
                            sortedAssets.sort(sortByName);
                            break;

                        default:
                            System.out.println("Invalid sorting choice.");
                            break;
                    }

                    if (sortChoice == 1 || sortChoice == 2) {
                        for (Asset asset : sortedAssets) {
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
                }



                case 10: {
                    System.out.println("\n--- Complete Asset Maintenance ---");

                    boolean foundMaintenance = false;

                    for (Maintenance record : maintenanceRecords) {
                        if ("IN_PROGRESS".equals(record.getStatus())) {
                            Asset asset = assetsById.get(record.getAssetId());

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

                    Maintenance selectedRecord = maintenanceById.get(maintenanceId);

                    if (selectedRecord == null) {
                        System.out.println("Maintenance record not found.");
                        break;
                    }

                    if (!"IN_PROGRESS".equals(selectedRecord.getStatus())) {
                        System.out.println("This maintenance is already completed.");
                        break;
                    }

                    Asset selectedAsset = assetsById.get(selectedRecord.getAssetId());

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


                case 11: {
                    System.out.println("\n--- Manage Pending Requests ---");

                    if (pendingRequests.isEmpty()) {
                        System.out.println("No pending requests to process.");
                        break;
                    }

                    // Display all requests that are still pending.
                    System.out.println("\n--- Pending Request List ---");

                    for (AssetRequest pending : pendingRequests) {
                        if (!"PENDING".equals(pending.getStatus())) {
                            continue;
                        }

                        Asset linkedAsset = assetsById.get(pending.getAssetId());

                        String assetName = linkedAsset == null
                                ? "Asset not found"
                                : linkedAsset.getAssetName();

                        System.out.println(
                                "Request ID: " + pending.getRequestId()
                                        + " | Asset ID: " + pending.getAssetId()
                                        + " | Asset: " + assetName
                                        + " | Employee ID: " + pending.getEmployeeId()
                                        + " | Date: " + pending.getRequestDate()
                                        + " | Status: " + pending.getStatus()
                        );
                    }

                    System.out.print("\nEnter Request ID to manage: ");
                    String requestInput = scanner.nextLine().trim();

                    int requestId;

                    try {
                        requestId = Integer.parseInt(requestInput);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid Request ID. Enter a number.");
                        break;
                    }

                    // Find the request directly using its ID.
                    AssetRequest request = requestsById.get(requestId);

                    if (request == null) {
                        System.out.println("Request not found.");
                        break;
                    }

                    if (!"PENDING".equals(request.getStatus())
                            || !pendingRequests.contains(request)) {
                        System.out.println("This request is no longer pending.");
                        break;
                    }

                    System.out.println("\nSelected Request ID: "
                            + request.getRequestId());
                    System.out.println("Asset ID: " + request.getAssetId());
                    System.out.println("Employee ID: " + request.getEmployeeId());

                    System.out.println("\n1. Approve Request");
                    System.out.println("2. Reject Request");
                    System.out.println("0. Cancel");
                    System.out.print("Enter your choice: ");

                    String action = scanner.nextLine().trim();

                    if ("0".equals(action)) {
                        System.out.println("Request management cancelled.");
                        break;
                    }

                    if ("2".equals(action)) {
                        request.setStatus("REJECTED");
                        pendingRequests.remove(request);

                        System.out.println("Request rejected successfully.");
                        System.out.println("Request ID: " + request.getRequestId());
                        System.out.println("Status: " + request.getStatus());
                        break;
                    }

                    if (!"1".equals(action)) {
                        System.out.println("Invalid choice.");
                        break;
                    }

                    // Approval requires the requested asset to exist and be available.
                    Asset selectedAsset = assetsById.get(request.getAssetId());

                    if (selectedAsset == null) {
                        System.out.println("Requested asset no longer exists.");
                        break;
                    }

                    if (!"AVAILABLE".equals(selectedAsset.getStatus())) {
                        System.out.println("Requested asset is not available.");
                        System.out.println("Request remains pending.");
                        break;
                    }

                    // Create the assignment only after all checks succeed.
                    Assignment assignment = new Assignment(
                            nextAssignmentId,
                            request.getAssetId(),
                            request.getEmployeeId(),
                            LocalDate.now(),
                            null
                    );

                    assignments.add(assignment);
                    activeAssignmentsByAssetId.put(request.getAssetId(), assignment);
                    nextAssignmentId++;

                    selectedAsset.setStatus("ASSIGNED");
                    request.setStatus("APPROVED");
                    pendingRequests.remove(request);

                    System.out.println("\nRequest approved successfully!");
                    System.out.println("Request ID: " + request.getRequestId());
                    System.out.println("Assignment ID: " + assignment.getAssignmentId());
                    System.out.println("Asset: " + selectedAsset.getAssetName());
                    System.out.println("Employee ID: " + request.getEmployeeId());
                    System.out.println("Request Status: " + request.getStatus());

                    break;
                }


                case 0:
                    System.out.println(
                            "Exiting IT Asset Management System. Goodbye!"
                    );
                    break;

                default:
                    System.out.println(
                            "Invalid choice! Please select an option from 0 to 11."
                    );
            }

        } while (choice != 0);

        scanner.close();
    }
}
