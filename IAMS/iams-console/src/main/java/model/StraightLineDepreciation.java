
package model;

public class StraightLineDepreciation implements DepreciationStrategy {

    @Override
    public double calculateAnnualDepreciation(
            double purchaseCost,
            double residualValue,
            int usefulLifeYears) {

        return (purchaseCost - residualValue) / usefulLifeYears;
    }

    @Override
    public double calculateCurrentValue(
            double purchaseCost,
            double residualValue,
            int usefulLifeYears,
            int elapsedYears) {

        double annualDepreciation = calculateAnnualDepreciation(
                purchaseCost,
                residualValue,
                usefulLifeYears
        );

        double depreciableAmount = purchaseCost - residualValue;

        double accumulatedDepreciation = Math.min(
                annualDepreciation * elapsedYears,
                depreciableAmount
        );

        return purchaseCost - accumulatedDepreciation;
    }
}
