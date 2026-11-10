package model;

import java.time.LocalDate;
import java.time.Clock;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Warranty {

    private int warrantyId;
    private int assetId;
    private LocalDate expiryDate;
    private final Clock clock;

    public Warranty(int warrantyId, int assetId, LocalDate expiryDate) {
        this(warrantyId, assetId, expiryDate, Clock.systemDefaultZone());
    }

    public Warranty(
            int warrantyId,
            int assetId,
            LocalDate expiryDate,
            Clock clock) {
        this.warrantyId = warrantyId;
        this.assetId = assetId;
        this.expiryDate = Objects.requireNonNull(
                expiryDate,
                "Expiry date cannot be null"
        );
        this.clock = Objects.requireNonNull(
                clock,
                "Clock cannot be null"
        );
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
        return ChronoUnit.DAYS.between(LocalDate.now(clock), expiryDate);
    }

    public boolean isExpired() {
        return expiryDate.isBefore(LocalDate.now(clock));
    }

    public boolean isExpiringSoon() {
        long daysRemaining = getDaysUntilExpiry();
        return daysRemaining >= 0 && daysRemaining <= 30;
    }
}
