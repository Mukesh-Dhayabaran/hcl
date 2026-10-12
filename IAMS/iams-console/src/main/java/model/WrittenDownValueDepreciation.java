
package model;

public class WrittenDownValueDepreciation implements DepreciationStrategy {

    @Override
    public double calculateAnnualDepreciation(
            double purchaseCost,
            double residualValue,
            int usefulLifeYears) {

        if (purchaseCost <= 0 || residualValue < 0
                || residualValue > purchaseCost || usefulLifeYears <= 0) {
            throw new IllegalArgumentException(
                    "Invalid depreciation inputs."
            );
        }

        double rate = 1.0 - Math.pow(
                residualValue / purchaseCost,
                1.0 / usefulLifeYears
        );

        return purchaseCost * rate;
    }

    @Override
    public double calculateCurrentValue(
            double purchaseCost,
            double residualValue,
            int usefulLifeYears,
            int elapsedYears) {

        if (elapsedYears < 0) {
            throw new IllegalArgumentException(
                    "Elapsed years cannot be negative."
            );
        }

        double rate = 1.0 - Math.pow(
                residualValue / purchaseCost,
                1.0 / usefulLifeYears
        );

        double currentValue = purchaseCost
                * Math.pow(1.0 - rate, elapsedYears);

        return Math.max(residualValue, currentValue);
    }
}
