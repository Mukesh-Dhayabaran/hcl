public class SampleData {

    public static void main(String[] args) {

        String[] assetTags = {
                "AST-001",
                "AST-002",
                "AST-003",
                "AST-004",
                "AST-005",
                "AST-006",
                "AST-007"
        };

        String[] deviceNames = {
                "Dell Latitude 5420",
                "HP ProBook 450 G8",
                "Lenovo ThinkPad E14",
                "Dell OptiPlex 7090",
                "HP EliteBook 840 G7",
                "Lenovo ThinkCentre M720",
                "Dell Inspiron 15"
        };

        String[] categories = {
                "Laptop",
                "Laptop",
                "Laptop",
                "Desktop",
                "Laptop",
                "Desktop",
                "Laptop"
        };

        String[] assignedTo = {
                "Arun",
                "Priya",
                "Rahul",
                "Karthik",
                "Divya",
                "Unassigned",
                "Vishnu"
        };

        String[] status = {
                "ASSIGNED",
                "ASSIGNED",
                "ASSIGNED",
                "ASSIGNED",
                "ASSIGNED",
                "AVAILABLE",
                "ASSIGNED"
        };

        System.out.println("------------------------");
        System.out.println("Sample Asset Data");
        System.out.println("------------------------");

        for (int i = 0; i < assetTags.length; i++) {
            System.out.println(
                    assetTags[i] + " | " +
                            deviceNames[i] + " | " +
                            categories[i] + " | " +
                            assignedTo[i] + " | " +
                            status[i]
            );
        }
    }
}