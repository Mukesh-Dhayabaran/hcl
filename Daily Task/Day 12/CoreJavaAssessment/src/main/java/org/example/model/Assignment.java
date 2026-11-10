
package org.example.model;

import java.time.LocalDate;

public class Assignment {

    private final int assignmentId;
    private final int assetId;
    private final int employeeId;
    private final LocalDate assignmentDate;
    private LocalDate returnDate;

    public Assignment(int assignmentId, int assetId, int employeeId) {
        this.assignmentId = assignmentId;
        this.assetId = assetId;
        this.employeeId = employeeId;
        this.assignmentDate = LocalDate.now();
        this.returnDate = null;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public int getAssetId() {
        return assetId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public LocalDate getAssignmentDate() {
        return assignmentDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public boolean isActive() {
        return returnDate == null;
    }

    public void returnAsset() {
        if (isActive()) {
            this.returnDate = LocalDate.now();
        }
    }

    public void displayDetails() {
        System.out.println("Assignment ID  : " + assignmentId);
        System.out.println("Asset ID       : " + assetId);
        System.out.println("Employee ID    : " + employeeId);
        System.out.println("Assigned Date  : " + assignmentDate);
        System.out.println("Return Date    : "
                + (returnDate == null ? "Not Returned" : returnDate));
        System.out.println("Status         : "
                + (isActive() ? "ACTIVE" : "RETURNED"));
    }
}
