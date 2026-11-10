package org.example.service;

import org.example.exception.AssetNotFoundException;
import org.example.exception.AssetUnavailableException;
import org.example.model.Asset;
import org.example.model.AssetStatus;
import org.example.model.ComputerAsset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AssignmentServiceTest {

    private AssetService assetService;
    private AssignmentService assignmentService;

    @BeforeEach
    void setUp() {
        assetService = new AssetService();
        assignmentService = new AssignmentService(assetService);

        assetService.registerAsset(new ComputerAsset(
                101, "AST-101", "HP Laptop",
                55000, "Intel i5", 8, 512
        ));

        assetService.registerAsset(new ComputerAsset(
                102, "AST-102", "Dell Laptop",
                60000, "Intel i5", 16, 512
        ));
    }

    @Test
    void shouldAssignAssetSuccessfully() throws AssetNotFoundException {
        assignmentService.assignAsset(101, 5001);

        Asset asset = assetService.findAsset(101);

        assertEquals(AssetStatus.ASSIGNED, asset.getStatus());
        assertEquals(1, assignmentService.getActiveAssignments().size());
    }

    @Test
    void shouldRejectAssignmentOfAlreadyAssignedAsset()
            throws AssetNotFoundException {

        assignmentService.assignAsset(101, 5001);

        assertThrows(
                AssetUnavailableException.class,
                () -> assignmentService.assignAsset(101, 5002)
        );
    }

    @Test
    void shouldReturnAssetSuccessfully() throws AssetNotFoundException {
        assignmentService.assignAsset(101, 5001);
        assignmentService.returnAsset(101);

        Asset asset = assetService.findAsset(101);

        assertEquals(AssetStatus.AVAILABLE, asset.getStatus());
        assertEquals(0, assignmentService.getActiveAssignments().size());
        assertEquals(1, assignmentService.getAllAssignments().size());
    }

    @Test
    void shouldRejectReturningUnassignedAsset() {
        assertThrows(
                AssetUnavailableException.class,
                () -> assignmentService.returnAsset(101)
        );
    }

    @Test
    void shouldRejectAssignmentForUnknownAsset() {
        assertThrows(
                AssetNotFoundException.class,
                () -> assignmentService.assignAsset(999, 5001)
        );
    }

    @Test
    void shouldProcessPendingRequestsInFifoOrder()
            throws AssetNotFoundException {

        assignmentService.addPendingRequest(101);
        assignmentService.addPendingRequest(102);

        Optional<Integer> firstRequest =
                assignmentService.getNextPendingRequest();

        Optional<Integer> secondRequest =
                assignmentService.getNextPendingRequest();

        assertEquals(Optional.of(101), firstRequest);
        assertEquals(Optional.of(102), secondRequest);
        assertTrue(assignmentService.getNextPendingRequest().isEmpty());
    }
}