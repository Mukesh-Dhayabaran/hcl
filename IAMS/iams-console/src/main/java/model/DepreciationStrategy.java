
package model;

public interface DepreciationStrategy {

    double calculateAnnualDepreciation(
            double purchaseCost,
            double residualValue,
            int usefulLifeYears
    );

    double calculateCurrentValue(
            double purchaseCost,
            double residualValue,
            int usefulLifeYears,
            int elapsedYears
    );
}
