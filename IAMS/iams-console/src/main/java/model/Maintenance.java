package model;

import java.time.LocalDate;

public class Maintenance {

    private int maintenanceId;
    private int assetId;
    private String description;
    private LocalDate maintenanceDate;
    private String status;

    public Maintenance(int maintenanceId, int assetId,
                       String description, LocalDate maintenanceDate,
                       String status) {
        this.maintenanceId = maintenanceId;
        this.assetId = assetId;
        this.description = description;
        this.maintenanceDate = maintenanceDate;
        this.status = status;
    }

    public int getMaintenanceId() {
        return maintenanceId;
    }

    public int getAssetId() {
        return assetId;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getMaintenanceDate() {
        return maintenanceDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
