
package model;

public class StraightLineDepreciation implements DepreciationStrategy {

    @Override
    public double calculateAnnualDepreciation(
            double purchaseCost,
            double residualValue,
            int usefulLifeYears) {

        validateFinancialInputs(
                purchaseCost, residualValue, usefulLifeYears
        );
        return (purchaseCost - residualValue) / usefulLifeYears;
    }

    @Override
    public double calculateCurrentValue(
            double purchaseCost,
            double residualValue,
            int usefulLifeYears,
            int elapsedYears) {

        validateFinancialInputs(
                purchaseCost, residualValue, usefulLifeYears
        );
        if (elapsedYears < 0) {
            throw new IllegalArgumentException(
                    "Elapsed years cannot be negative."
            );
        }

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

    private void validateFinancialInputs(
            double purchaseCost,
            double residualValue,
            int usefulLifeYears) {

        if (!Double.isFinite(purchaseCost) || purchaseCost <= 0
                || !Double.isFinite(residualValue)
                || residualValue < 0
                || residualValue > purchaseCost
                || usefulLifeYears <= 0) {
            throw new IllegalArgumentException(
                    "Invalid depreciation inputs."
            );
        }
    }
}
