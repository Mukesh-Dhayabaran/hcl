package model;

public class SoftwareLicense {

    private int licenseId;
    private String softwareName;
    private int purchasedSeats;
    private int usedSeats;

    public SoftwareLicense(int licenseId, String softwareName,
                           int purchasedSeats) {
        this.licenseId = licenseId;
        this.softwareName = softwareName;
        this.purchasedSeats = purchasedSeats;
        this.usedSeats = 0;
    }

    public int getLicenseId() {
        return licenseId;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public int getPurchasedSeats() {
        return purchasedSeats;
    }

    public int getUsedSeats() {
        return usedSeats;
    }

    public int getAvailableSeats() {
        return purchasedSeats - usedSeats;
    }

    public boolean allocateSeat() {
        if (usedSeats >= purchasedSeats) {
            return false;
        }

        usedSeats++;
        return true;
    }
}
