package model;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WarrantyTest {

    private static final LocalDate TODAY =
            LocalDate.of(2026, 11, 10);
    private static final Clock FIXED_CLOCK = Clock.fixed(
            Instant.parse("2026-11-10T00:00:00Z"),
            ZoneOffset.UTC
    );

    @Test
    void futureWarrantyIsNotExpired() {
        LocalDate expiryDate = TODAY.plusDays(31);
        Warranty warranty = warranty(expiryDate);

        assertFalse(warranty.isExpired());
        assertFalse(warranty.isExpiringSoon());
        assertEquals(31, warranty.getDaysUntilExpiry());
    }

    @Test
    void warrantyExpiringTodayIsNotExpiredAndIsExpiringSoon() {
        Warranty warranty = warranty(TODAY);

        assertFalse(warranty.isExpired());
        assertTrue(warranty.isExpiringSoon());
        assertEquals(0, warranty.getDaysUntilExpiry());
    }

    @Test
    void expiredWarrantyIsExpiredAndNotExpiringSoon() {
        LocalDate expiryDate = TODAY.minusDays(1);
        Warranty warranty = warranty(expiryDate);

        assertTrue(warranty.isExpired());
        assertFalse(warranty.isExpiringSoon());
        assertEquals(-1, warranty.getDaysUntilExpiry());
    }

    @Test
    void warrantyWithExactlyThirtyDaysRemainingIsExpiringSoon() {
        Warranty warranty = warranty(TODAY.plusDays(30));

        assertFalse(warranty.isExpired());
        assertTrue(warranty.isExpiringSoon());
        assertEquals(30, warranty.getDaysUntilExpiry());
    }

    @Test
    void warrantyBeyondThirtyDaysIsNotExpiringSoon() {
        Warranty warranty = warranty(TODAY.plusDays(31));

        assertFalse(warranty.isExpired());
        assertFalse(warranty.isExpiringSoon());
    }

    @Test
    void constructorRetainsWarrantyValues() {
        LocalDate expiryDate = LocalDate.of(2027, 1, 31);
        Warranty warranty = warranty(7, 42, expiryDate);

        assertEquals(7, warranty.getWarrantyId());
        assertEquals(42, warranty.getAssetId());
        assertEquals(expiryDate, warranty.getExpiryDate());
    }

    @Test
    void constructorRejectsNullExpiryDate() {
        assertThrows(
                NullPointerException.class,
                () -> new Warranty(1, 101, null, FIXED_CLOCK)
        );
    }

    @Test
    void constructorRejectsNullClock() {
        assertThrows(
                NullPointerException.class,
                () -> new Warranty(1, 101, TODAY, null)
        );
    }

    private static Warranty warranty(LocalDate expiryDate) {
        return warranty(1, 101, expiryDate);
    }

    private static Warranty warranty(int warrantyId, int assetId,
                                     LocalDate expiryDate) {
        return new Warranty(warrantyId, assetId, expiryDate, FIXED_CLOCK);
    }
}
