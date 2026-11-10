package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SoftwareLicenseTest {

    @Test
    void newLicenceStartsWithAllSeatsAvailable() {
        SoftwareLicense license =
                new SoftwareLicense(1, "Office Suite", 3);

        assertEquals(3, license.getPurchasedSeats());
        assertEquals(0, license.getUsedSeats());
        assertEquals(3, license.getAvailableSeats());
    }

    @Test
    void allocationIncrementsUsedSeatsAndReducesAvailableSeats() {
        SoftwareLicense license =
                new SoftwareLicense(1, "Office Suite", 3);

        assertTrue(license.allocateSeat());
        assertTrue(license.allocateSeat());

        assertEquals(2, license.getUsedSeats());
        assertEquals(1, license.getAvailableSeats());
    }

    @Test
    void allocationAtSeatLimitIsRejected() {
        SoftwareLicense license =
                new SoftwareLicense(1, "Office Suite", 2);

        assertTrue(license.allocateSeat());
        assertTrue(license.allocateSeat());
        assertFalse(license.allocateSeat());

        assertEquals(2, license.getUsedSeats());
        assertEquals(0, license.getAvailableSeats());
    }
}
