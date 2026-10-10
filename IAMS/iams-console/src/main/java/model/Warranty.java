package model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Warranty {

    private int warrantyId;
    private int assetId;
    private LocalDate expiryDate;

    public Warranty(int warrantyId, int assetId, LocalDate expiryDate) {
        this.warrantyId = warrantyId;
        this.assetId = assetId;
        this.expiryDate = expiryDate;
    }

    public int getWarrantyId() {
        return warrantyId;
    }

    public int getAssetId() {
        return assetId;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public long getDaysUntilExpiry() {
        return ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
    }

    public boolean isExpired() {
        return expiryDate.isBefore(LocalDate.now());
    }

    public boolean isExpiringSoon() {
        long daysRemaining = getDaysUntilExpiry();
        return daysRemaining >= 0 && daysRemaining <= 30;
    }
}
