package model;

public class Asset {

    private int assetId;
    private String assetTag;
    private String assetName;
    private String category;
    private String status;

    public Asset(int assetId, String assetTag, String assetName,
                 String category, String status) {
        this.assetId = assetId;
        this.assetTag = assetTag;
        this.assetName = assetName;
        this.category = category;
        this.status = status;
    }

    public int getAssetId() {
        return assetId;
    }

    public String getAssetTag() {
        return assetTag;
    }

    public String getAssetName() {
        return assetName;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}