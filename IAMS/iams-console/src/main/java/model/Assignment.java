package model;

import java.time.LocalDate;

public class Assignment {

    private int assignmentId;
    private int assetId;
    private int employeeId;
    private LocalDate assignmentDate;
    private LocalDate returnDate;

    public Assignment(int assignmentId, int assetId, int employeeId,
                      LocalDate assignmentDate, LocalDate returnDate) {
        this.assignmentId = assignmentId;
        this.assetId = assetId;
        this.employeeId = employeeId;
        this.assignmentDate = assignmentDate;
        this.returnDate = returnDate;
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

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
