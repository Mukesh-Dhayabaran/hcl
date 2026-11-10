import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AssetManagementMenuWorkflowTest {

    private final PrintStream originalOut = System.out;
    private final java.io.InputStream originalIn = System.in;

    @AfterEach
    void restoreStandardStreams() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    void registersAssetAndRejectsEmptyAndCaseInsensitiveDuplicateTags() {
        String output = runMenu(
                "1\nAST-NEW\nDell Server\nServer\n"
                        + "1\nast-001\nAnother Laptop\nLaptop\n"
                        + "1\n\n\n\n"
                        + "0\n"
        );

        assertContains(output, "Asset registered successfully!");
        assertContains(output, "An asset with this tag already exists: ast-001");
        assertContains(output, "Asset tag, name, and category cannot be empty.");
    }

    @Test
    void createsRequestAndRejectsInvalidEmployeeAndUnavailableAsset() {
        String output = runMenu(
                "2\n1\n101\n"
                        + "2\n999\n"
                        + "2\n2\n0\n"
                        + "3\n1\n1\n202\n"
                        + "2\n1\n303\n"
                        + "0\n"
        );

        assertContains(output, "Asset request submitted successfully!");
        assertContains(output, "Asset not found.");
        assertContains(output, "Employee ID must be greater than zero.");
        assertContains(output, "This asset is not available for requests.");
    }

    @Test
    void approvesRequestCreatesAssignmentAndChangesAssetStatus() {
        String output = runMenu(
                "2\n1\n101\n"
                        + "11\n1\n1\n"
                        + "4\n1\n1\n"
                        + "0\n"
        );

        assertContains(output, "Request approved successfully!");
        assertContains(output, "Request Status: APPROVED");
        assertContains(output, "Current Status: ASSIGNED");
        assertContains(output, "Assignment Status: ACTIVE");
    }

    @Test
    void rejectsRequestAndRemovesItFromPendingRequests() {
        String output = runMenu(
                "2\n1\n101\n"
                        + "11\n1\n2\n"
                        + "11\n"
                        + "0\n"
        );

        assertContains(output, "Request rejected successfully.");
        assertContains(output, "No pending requests to process.");
    }

    @Test
    void rejectsInvalidPendingRequestIdSafely() {
        String output = runMenu(
                "2\n1\n101\n"
                        + "11\n999\n"
                        + "0\n"
        );

        assertContains(output, "Request not found.");
    }

    @Test
    void preventsAssigningAnAlreadyAssignedAsset() {
        String output = runMenu(
                "3\n1\n1\n101\n"
                        + "3\n1\n1\n202\n"
                        + "0\n"
        );

        assertContains(output, "Asset assigned successfully!");
        assertContains(output, "Asset is not available for assignment.");
    }

    @Test
    void rejectsInvalidAssetAndEmployeeIdsForDirectAssignment() {
        String output = runMenu(
                "3\n1\n999\n"
                        + "3\n1\n1\n0\n"
                        + "0\n"
        );

        assertContains(output, "Asset not found.");
        assertContains(output, "Employee ID must be greater than zero.");
    }

    @Test
    void returnsAssignedAssetAndRejectsRepeatedReturn() {
        String output = runMenu(
                "3\n1\n1\n101\n"
                        + "3\n2\n1\n"
                        + "4\n1\n1\n"
                        + "3\n2\n1\n"
                        + "0\n"
        );

        assertContains(output, "Asset returned successfully!");
        assertContains(output, "Current Status: AVAILABLE");
        assertContains(output, "This asset has no active assignment.");
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
