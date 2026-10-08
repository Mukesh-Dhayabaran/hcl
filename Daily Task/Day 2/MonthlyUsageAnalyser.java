public class MonthlyUsageAnalyser {

    static final int SLAB_ONE_LIMIT = 100;
    static final int SLAB_TWO_LIMIT = 200;

    public static void main(String[] args) {

        int[] monthlyUsage = {
            120, 150, 135, 160,
            180, 210, 195, 170,
            155, 140, 165, 190
        };

        System.out.println("----------------------");
        System.out.println("Monthly Usage Analyser");
        System.out.println("----------------------");

        for (int usage : monthlyUsage) {
            System.out.println(usage);
        }
    }
}