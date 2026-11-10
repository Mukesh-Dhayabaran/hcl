import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MaintenanceAndLicenseWorkflowTest {

    private final PrintStream originalOut = System.out;
    private final java.io.InputStream originalIn = System.in;

    @AfterEach
    void restoreStandardStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    void recordsAndCompletesMaintenanceLifecycle() {
        String output = runMenu(
                "5\n1\nReplace battery\n"
                        + "10\n1\n"
                        + "0\n"
        );

        assertContains(output, "Maintenance recorded successfully!");
        assertContains(output, "Maintenance Status: IN_PROGRESS");
        assertContains(output, "Maintenance completed successfully!");
        assertContains(output, "Maintenance Status: COMPLETED");
        assertContains(output, "Asset Status: AVAILABLE");
    }

    @Test
    void rejectsMaintenanceForAssignedAssetAndRepeatedCompletion() {
        String output = runMenu(
                "3\n1\n1\n101\n"
                        + "5\n1\nRepair keyboard\n"
                        + "3\n2\n1\n"
                        + "5\n1\nRepair keyboard\n"
                        + "10\n1\n"
                        + "10\n"
                        + "0\n"
        );

        assertContains(output,
                "Return the asset before recording maintenance.");
        assertContains(output, "Maintenance recorded successfully!");
        assertContains(output, "Maintenance Status: IN_PROGRESS");
        assertContains(output, "Maintenance completed successfully!");
        assertContains(output, "No pending maintenance records found.");
    }

    @Test
    void rejectsInvalidMaintenanceInputsSafely() {
        String output = runMenu(
                "5\n999\n"
                        + "5\n1\n\n"
                        + "0\n"
        );

        assertContains(output, "Asset not found.");
        assertContains(output, "Description cannot be empty.");
    }

    @Test
    void createsLicenceAllocatesSeatsAndRejectsAllocationBeyondLimit() {
        String output = runMenu(
                "6\n1\nOffice Suite\n2\n"
                        + "6\n3\n1\n"
                        + "6\n3\n1\n"
                        + "6\n3\n1\n"
                        + "6\n2\n"
                        + "0\n"
        );

        assertContains(output, "Software licence added successfully.");
        assertContains(output, "Licence seat allocated successfully.");
        assertContains(output,
                "Allocation denied. No available licence seats.");
        assertContains(output, "Used Seats: 2");
        assertContains(output, "Available Seats: 0");
    }

    @Test
    void rejectsInvalidLicenceCreationAndUnknownLicenceAllocation() {
        String output = runMenu(
                "6\n1\nOffice Suite\n0\n"
                        + "6\n1\nOffice Suite\n1\n"
                        + "6\n3\n999\n"
                        + "0\n"
        );

        assertContains(output,
                "Purchased seats must be greater than zero.");
        assertContains(output, "Software licence not found.");
    }

    @Test
    void handlesEmptyLicenceCollection() {
        String output = runMenu(
                "6\n2\n"
                        + "6\n3\n"
                        + "0\n"
        );

        assertContains(output, "No software licences registered.");
    }

    private String runMenu(String input) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setIn(new ByteArrayInputStream(
                input.getBytes(StandardCharsets.UTF_8)
        ));
        System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));

        AssetManagementMenu.main(new String[0]);

        return output.toString(StandardCharsets.UTF_8);
    }

    private static void assertContains(String output, String expected) {
        assertTrue(
                output.contains(expected),
                () -> "Expected output to contain: " + expected
                        + "\nActual output:\n" + output
        );
    }
}
