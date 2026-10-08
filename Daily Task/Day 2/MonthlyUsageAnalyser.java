public class MonthlyUsageAnalyser {

    public static void main(String[] args) {

        int[] weeklyUsage = {120, 150, 135, 160};

        System.out.println("Monthly Usage Analysis");
        System.out.println("----------------------");

        for (int usage : weeklyUsage) {
            System.out.println(usage);
        }
    }
}