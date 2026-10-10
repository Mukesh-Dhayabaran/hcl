import java.time.LocalDate;
import model.Asset;
import model.AssetCategory;
import model.Assignment;

public class ModelTest {

    public static void main(String[] args) {

        // Test 1: Asset
        Asset asset = new Asset(
                1, "AST-001", "Dell Latitude 5420",
                "Laptop", "AVAILABLE"
        );

        System.out.println("=== ASSET TEST ===");
        System.out.println("Asset ID: " + asset.getAssetId());
        System.out.println("Asset Tag: " + asset.getAssetTag());
        System.out.println("Asset Name: " + asset.getAssetName());
        System.out.println("Category: " + asset.getCategory());
        System.out.println("Status: " + asset.getStatus());

        // Test 2: model.AssetCategory
        AssetCategory category = new AssetCategory(
                1, "Laptop", "Portable computers"
        );

        System.out.println("\n=== CATEGORY TEST ===");
        System.out.println("Category ID: " + category.getCategoryId());
        System.out.println("Name: " + category.getCategoryName());
        System.out.println("Description: " + category.getDescription());

        // Test 3: model.Assignment
        Assignment assignment = new Assignment(
                101, 1, 501, LocalDate.now(), null
        );

        System.out.println("\n=== ASSIGNMENT TEST ===");
        System.out.println("model.Assignment ID: " + assignment.getAssignmentId());
        System.out.println("Asset ID: " + assignment.getAssetId());
        System.out.println("Employee ID: " + assignment.getEmployeeId());
        System.out.println("Assigned Date: " + assignment.getAssignmentDate());
        System.out.println("Return Date: " + assignment.getReturnDate());

        // Test updating the return date
        assignment.setReturnDate(LocalDate.now());

        System.out.println("\nAfter returning the asset:");
        System.out.println("Return Date: " + assignment.getReturnDate());

        System.out.println("\nAll model tests completed!");
    }
}
