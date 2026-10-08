public class MonthlyUsageAnalyser {

    public static void main(String[] args) {

        int[] monthlyUsage = { 120, 150, 180, 200, 175, 160, 210, 190, 220, 195, 170, 155};

        System.out.println("-------------");
        System.out.println("Monthly Usage");
        System.out.println("-------------");

        for (int i=0;i<monthlyUsage.length;i++) 
        System.out.println("Month "+(i+1)+": "+monthlyUsage[i]);
    }
}