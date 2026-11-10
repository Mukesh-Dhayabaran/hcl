package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DepreciationReportTest {

    private static final double DELTA = 0.000001;

    @Test
    void defaultConstructorUsesStraightLineStrategy() {
        DepreciationReport report = new DepreciationReport(
                1, 50_000, 5_000, 5, 2
        );

        assertEquals(9_000, report.getAnnualDepreciation(), DELTA);
        assertEquals(32_000, report.getCurrentValue(), DELTA);
        assertEquals(18_000, report.getAccumulatedDepreciation(), DELTA);
    }

    @Test
    void explicitWrittenDownValueStrategyIsUsed() {
        DepreciationReport report = new DepreciationReport(
                1,
                50_000,
                5_000,
                5,
                2,
                new WrittenDownValueDepreciation()
        );

        double rate = 1 - Math.pow(5_000.0 / 50_000.0, 1.0 / 5);
        double expectedAnnual = 50_000 * rate;
        double expectedValue = 50_000 * Math.pow(1 - rate, 2);

        assertEquals(expectedAnnual, report.getAnnualDepreciation(), DELTA);
        assertEquals(expectedValue, report.getCurrentValue(), DELTA);
    }

    @Test
    void constructorRejectsInvalidInputs() {
        assertThrows(IllegalArgumentException.class,
                () -> new DepreciationReport(
                        1, 0, 5_000, 5, 1));
        assertThrows(IllegalArgumentException.class,
                () -> new DepreciationReport(
                        1, Double.NaN, 5_000, 5, 1));
        assertThrows(IllegalArgumentException.class,
                () -> new DepreciationReport(
                        1, 50_000, -1, 5, 1));
        assertThrows(IllegalArgumentException.class,
                () -> new DepreciationReport(
                        1, 50_000, 60_000, 5, 1));
        assertThrows(IllegalArgumentException.class,
                () -> new DepreciationReport(
                        1, 50_000, 5_000, 0, 1));
        assertThrows(IllegalArgumentException.class,
                () -> new DepreciationReport(
                        1, 50_000, 5_000, 5, -1));
        assertThrows(IllegalArgumentException.class,
                () -> new DepreciationReport(
                        1, 50_000, 5_000, 5, 1, null));
    }
}
