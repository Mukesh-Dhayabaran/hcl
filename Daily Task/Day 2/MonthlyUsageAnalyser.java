public class MonthlyUsageAnalyser {

    public static void main(String[] args) {

        int[] monthlyUsage = { 120, 150, 180, 200, 175, 160, 210, 190, 220, 195, 170, 155};

        System.out.println("-------------");
        System.out.println("Monthly Usage");
        System.out.println("-------------");

        for (int i=0;i<monthlyUsage.length;i++) 
        System.out.println("Month "+(i+1)+": "+monthlyUsage[i]);

        int total=0;
        int max=monthlyUsage[0];
        int min=monthlyUsage[0];

        for (int usage : monthlyUsage) 
        {
            total+=usage;

            if (usage>max)
            max=usage;
        
            if (usage<min) 
            min=usage;
        }

        double average = (double)total/monthlyUsage.length;

        System.out.println();
        System.out.println("Total Usage   : "+total);
        System.out.printf("Average Usage : %.2f\n",average);
        System.out.println("Maximum Usage : "+max);
        System.out.println("Minimum Usage : "+min);

        char grade = average >= Constants.MEDIUM_USAGE_LIMIT ? Constants.GRADE_A 
            : average >= Constants.LOW_USAGE_LIMIT ? Constants.GRADE_B
            : Constants.GRADE_C;

        System.out.println("Usage Grade : "+grade);
    }
}