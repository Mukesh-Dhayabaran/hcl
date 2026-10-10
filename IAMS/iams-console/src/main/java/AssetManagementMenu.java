import java.util.Scanner;
import java.util.ArrayList;
import java.time.LocalDate;
import model.Asset;
import model.Assignment;
import model.AssetRequest;

public class AssetManagementMenu {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Asset> assets = new ArrayList<>();

        ArrayList<Assignment> assignments = new ArrayList<>();
        int nextAssignmentId = 1;

        ArrayList<AssetRequest> assetRequests = new ArrayList<>();
        int nextRequestId = 1;

        int nextAssetId = 1;
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
            System.out.println("9. View All Registered Assets");
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

                        if (!selectedAsset.getStatus().equals("AVAILABLE")) {
                            System.out.println("Asset is not available for assignment.");
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


                case 4:
                    System.out.println(
                            "Duplicate assignment checking is not implemented yet."
                    );
                    break;

                case 5:
                    System.out.println(
                            "Maintenance feature is not implemented yet."
                    );
                    break;

                case 6:
                    System.out.println(
                            "Software licence tracking is not implemented yet."
                    );
                    break;

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
