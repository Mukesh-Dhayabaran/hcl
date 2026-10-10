
package model;

import java.time.LocalDate;

public class AssetRequest {

    private int requestId;
    private int assetId;
    private int employeeId;
    private LocalDate requestDate;
    private String status;

    public AssetRequest(int requestId, int assetId, int employeeId,
                        LocalDate requestDate, String status) {
        this.requestId = requestId;
        this.assetId = assetId;
        this.employeeId = employeeId;
        this.requestDate = requestDate;
        this.status = status;
    }

    public int getRequestId() {
        return requestId;
    }

    public int getAssetId() {
        return assetId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
