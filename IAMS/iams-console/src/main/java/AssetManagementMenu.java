import java.util.ArrayList;
import java.util.Scanner;
import model.Asset;

public class AssetManagementMenu {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Asset> assets = new ArrayList<>();

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

                case 2:
                    System.out.println(
                            "Asset request feature is not implemented yet."
                    );
                    break;

                case 3:
                    System.out.println(
                            "Assignment / return feature is not implemented yet."
                    );
                    break;

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

                case 0:
                    System.out.println(
                            "Exiting IT Asset Management System. Goodbye!"
                    );
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
