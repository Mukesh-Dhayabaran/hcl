
import java.time.LocalDate;

import model.Asset;
import model.AssetCategory;
import model.Assignment;
import model.DepreciationReport;
import model.StraightLineDepreciation;
import model.WrittenDownValueDepreciation;
import model.Admin;
import model.ITAssetManager;
import model.Employee;
import model.User;

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

        // Test 2: AssetCategory
        AssetCategory category = new AssetCategory(
                1, "Laptop", "Portable computers"
        );

        System.out.println("\n=== CATEGORY TEST ===");
        System.out.println("Category ID: " + category.getCategoryId());
        System.out.println("Name: " + category.getCategoryName());
        System.out.println("Description: " + category.getDescription());

        // Test 3: Assignment
        Assignment assignment = new Assignment(
                101, 1, 501, LocalDate.now(), null
        );

        System.out.println("\n=== ASSIGNMENT TEST ===");
        System.out.println("Assignment ID: " + assignment.getAssignmentId());
        System.out.println("Asset ID: " + assignment.getAssetId());
        System.out.println("Employee ID: " + assignment.getEmployeeId());
        System.out.println("Assigned Date: " + assignment.getAssignmentDate());
        System.out.println("Return Date: " + assignment.getReturnDate());

        assignment.setReturnDate(LocalDate.now());

        System.out.println("\nAfter returning the asset:");
        System.out.println("Return Date: " + assignment.getReturnDate());

        // Test 4: User role hierarchy and runtime polymorphism
        System.out.println("\n=== USER ROLE TEST ===");

        User[] users = {
                new Admin(1, "Arun", "arun@example.com"),
                new ITAssetManager(2, "Bala", "bala@example.com"),
                new Employee(3, "Chitra", "chitra@example.com")
        };

        for (User user : users) {
            System.out.println(
                    user.getName() + " - " + user.getRole()
            );
            user.displayPermissions();
        }

        // Test 5: Straight-Line Depreciation
        System.out.println("\n=== SLM DEPRECIATION TEST ===");

        DepreciationReport slmReport = new DepreciationReport(
                101, 50000, 5000, 5, 2,
                new StraightLineDepreciation()
        );

        System.out.printf(
                "Annual depreciation: %.2f%n",
                slmReport.getAnnualDepreciation()
        );
        System.out.printf(
                "Current value: %.2f%n",
                slmReport.getCurrentValue()
        );

        // Test 6: Written-Down Value Depreciation
        System.out.println("\n=== WDV DEPRECIATION TEST ===");

        DepreciationReport wdvReport = new DepreciationReport(
                102, 50000, 5000, 5, 2,
                new WrittenDownValueDepreciation()
        );

        System.out.printf(
                "Annual depreciation: %.2f%n",
                wdvReport.getAnnualDepreciation()
        );
        System.out.printf(
                "Current value: %.2f%n",
                wdvReport.getCurrentValue()
        );

        System.out.println("\nAll model tests completed!");
    }
}
