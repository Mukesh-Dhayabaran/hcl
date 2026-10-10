
package model;

public class DepreciationReport {

    private final int assetId;
    private final double purchaseCost;
    private final double residualValue;
    private final int usefulLifeYears;
    private final int elapsedYears;

    public DepreciationReport(int assetId, double purchaseCost,
                              double residualValue, int usefulLifeYears,
                              int elapsedYears) {

        if (!Double.isFinite(purchaseCost) || purchaseCost <= 0) {
            throw new IllegalArgumentException(
                    "Purchase cost must be greater than zero."
            );
        }

        if (!Double.isFinite(residualValue)
                || residualValue < 0
                || residualValue > purchaseCost) {
            throw new IllegalArgumentException(
                    "Residual value must be between zero and purchase cost."
            );
        }

        if (usefulLifeYears <= 0) {
            throw new IllegalArgumentException(
                    "Useful life must be greater than zero."
            );
        }

        if (elapsedYears < 0) {
            throw new IllegalArgumentException(
                    "Elapsed years cannot be negative."
            );
        }

        this.assetId = assetId;
        this.purchaseCost = purchaseCost;
        this.residualValue = residualValue;
        this.usefulLifeYears = usefulLifeYears;
        this.elapsedYears = elapsedYears;
    }

    public int getAssetId() {
        return assetId;
    }

    public double getPurchaseCost() {
        return purchaseCost;
    }

    public double getResidualValue() {
        return residualValue;
    }

    public int getUsefulLifeYears() {
        return usefulLifeYears;
    }

    public int getElapsedYears() {
        return elapsedYears;
    }

    public double getAnnualDepreciation() {
        return (purchaseCost - residualValue) / usefulLifeYears;
    }

    public double getAccumulatedDepreciation() {
        return Math.min(
                getAnnualDepreciation() * elapsedYears,
                purchaseCost - residualValue
        );
    }

    public double getCurrentValue() {
        return purchaseCost - getAccumulatedDepreciation();
    }
}
