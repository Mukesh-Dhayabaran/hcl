package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DepreciationStrategyTest {

    private static final double DELTA = 0.000001;

    @Test
    void straightLineCalculatesAnnualDepreciation() {
        StraightLineDepreciation strategy = new StraightLineDepreciation();

        double result = strategy.calculateAnnualDepreciation(
                50_000, 5_000, 5
        );

        assertEquals(9_000, result, DELTA);
    }

    @Test
    void straightLineCurrentValueAtZeroElapsedYearsIsPurchaseCost() {
        StraightLineDepreciation strategy = new StraightLineDepreciation();

        double result = strategy.calculateCurrentValue(
                50_000, 5_000, 5, 0
        );

        assertEquals(50_000, result, DELTA);
    }

    @Test
    void straightLineCurrentValueAtUsefulLifeIsResidualValue() {
        StraightLineDepreciation strategy = new StraightLineDepreciation();

        double result = strategy.calculateCurrentValue(
                50_000, 5_000, 5, 5
        );

        assertEquals(5_000, result, DELTA);
    }

    @Test
    void straightLineCurrentValueBeyondUsefulLifeDoesNotGoBelowResidualValue() {
        StraightLineDepreciation strategy = new StraightLineDepreciation();

        double result = strategy.calculateCurrentValue(
                50_000, 5_000, 5, 8
        );

        assertEquals(5_000, result, DELTA);
    }

    @Test
    void writtenDownValueCalculatesAnnualDepreciation() {
        WrittenDownValueDepreciation strategy =
                new WrittenDownValueDepreciation();

        double expectedRate =
                1 - Math.pow(5_000.0 / 50_000.0, 1.0 / 5);
        double expected = 50_000 * expectedRate;

        double result = strategy.calculateAnnualDepreciation(
                50_000, 5_000, 5
        );

        assertEquals(expected, result, DELTA);
    }

    @Test
    void writtenDownValueAtZeroElapsedYearsIsPurchaseCost() {
        WrittenDownValueDepreciation strategy =
                new WrittenDownValueDepreciation();

        double result = strategy.calculateCurrentValue(
                50_000, 5_000, 5, 0
        );

        assertEquals(50_000, result, DELTA);
    }

    @Test
    void writtenDownValueNeverFallsBelowResidualValue() {
        WrittenDownValueDepreciation strategy =
                new WrittenDownValueDepreciation();

        double result = strategy.calculateCurrentValue(
                50_000, 5_000, 5, 20
        );

        assertEquals(5_000, result, DELTA);
    }

    @Test
    void writtenDownValueRejectsInvalidAnnualDepreciationInputs() {
        WrittenDownValueDepreciation strategy =
                new WrittenDownValueDepreciation();

        assertThrows(IllegalArgumentException.class,
                () -> strategy.calculateAnnualDepreciation(
                        0, 5_000, 5));
        assertThrows(IllegalArgumentException.class,
                () -> strategy.calculateAnnualDepreciation(
                        50_000, -1, 5));
        assertThrows(IllegalArgumentException.class,
                () -> strategy.calculateAnnualDepreciation(
                        50_000, 60_000, 5));
        assertThrows(IllegalArgumentException.class,
                () -> strategy.calculateAnnualDepreciation(
                        50_000, 5_000, 0));
        assertThrows(IllegalArgumentException.class,
                () -> strategy.calculateAnnualDepreciation(
                        Double.NaN, 5_000, 5));
        assertThrows(IllegalArgumentException.class,
                () -> strategy.calculateAnnualDepreciation(
                        Double.POSITIVE_INFINITY, 5_000, 5));
    }

    @Test
    void writtenDownValueRejectsNegativeElapsedYears() {
        WrittenDownValueDepreciation strategy =
                new WrittenDownValueDepreciation();

        assertThrows(IllegalArgumentException.class,
                () -> strategy.calculateCurrentValue(
                        50_000, 5_000, 5, -1));
    }

    @Test
    void straightLineRejectsInvalidInputs() {
        StraightLineDepreciation strategy = new StraightLineDepreciation();

        assertThrows(IllegalArgumentException.class,
                () -> strategy.calculateAnnualDepreciation(
                        0, 5_000, 5));
        assertThrows(IllegalArgumentException.class,
                () -> strategy.calculateAnnualDepreciation(
                        Double.NaN, 5_000, 5));
        assertThrows(IllegalArgumentException.class,
                () -> strategy.calculateAnnualDepreciation(
                        50_000, Double.POSITIVE_INFINITY, 5));
        assertThrows(IllegalArgumentException.class,
                () -> strategy.calculateAnnualDepreciation(
                        50_000, 60_000, 5));
        assertThrows(IllegalArgumentException.class,
                () -> strategy.calculateAnnualDepreciation(
                        50_000, 5_000, 0));
        assertThrows(IllegalArgumentException.class,
                () -> strategy.calculateCurrentValue(
                        50_000, 5_000, 5, -1));
    }

    @Test
    void writtenDownValueRejectsNonFiniteCurrentValueInputs() {
        WrittenDownValueDepreciation strategy =
                new WrittenDownValueDepreciation();

        assertThrows(IllegalArgumentException.class,
                () -> strategy.calculateCurrentValue(
                        Double.NaN, 5_000, 5, 1));
        assertThrows(IllegalArgumentException.class,
                () -> strategy.calculateCurrentValue(
                        50_000, Double.POSITIVE_INFINITY, 5, 1));
        assertThrows(IllegalArgumentException.class,
                () -> strategy.calculateCurrentValue(
                        50_000, 5_000, 0, 1));
    }
}
