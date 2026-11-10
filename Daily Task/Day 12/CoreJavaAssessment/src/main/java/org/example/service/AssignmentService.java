package org.example.service;

import org.example.exception.*;
import org.example.model.*;

import java.util.*;

public class AssignmentService {

    private final AssetService assetService;

    private final List<Assignment> assignmentHistory = new ArrayList<>();
    private final Map<Integer, Assignment> activeAssignments = new HashMap<>();
    private final Queue<Integer> pendingAssetRequests = new ArrayDeque<>();

    private int nextAssignmentId = 1;

    public AssignmentService(AssetService assetService) {
        this.assetService = assetService;
    }

    public void assignAsset(int assetId, int employeeId)
            throws AssetNotFoundException {

        if (employeeId <= 0) {
            throw new IllegalArgumentException("Invalid employee ID");
        }

        Asset asset = assetService.findAsset(assetId);

        if (asset.getStatus() != AssetStatus.AVAILABLE) {
            throw new AssetUnavailableException("Asset is not available");
        }

        Assignment assignment =
                new Assignment(nextAssignmentId++, assetId, employeeId);

        assignmentHistory.add(assignment);
        activeAssignments.put(assetId, assignment);
        asset.setStatus(AssetStatus.ASSIGNED);
    }

    public void returnAsset(int assetId) throws AssetNotFoundException {

        Asset asset = assetService.findAsset(assetId);
        Assignment assignment = activeAssignments.remove(assetId);

        if (assignment == null) {
            throw new AssetUnavailableException(
                    "No active assignment found for this asset");
        }

        assignment.returnAsset();
        asset.setStatus(AssetStatus.AVAILABLE);
    }

    public List<Assignment> getAllAssignments() {
        return new ArrayList<>(assignmentHistory);
    }

    public List<Assignment> getActiveAssignments() {
        return new ArrayList<>(activeAssignments.values());
    }

    public List<Assignment> findByEmployeeId(int employeeId) {
        return assignmentHistory.stream()
                .filter(a -> a.getEmployeeId() == employeeId)
                .toList();
    }

    public void addPendingRequest(int assetId) {
        pendingAssetRequests.offer(assetId);
    }

    public Optional<Integer> getNextPendingRequest() {
        return Optional.ofNullable(pendingAssetRequests.poll());
    }
}
