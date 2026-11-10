package org.example.model;

public abstract class Asset {

    private int assetId;
    private final String assetTag;
    private String name;
    private double purchaseCost;
    private AssetStatus status;

    public Asset(int assetId, String assetTag, String name, double purchaseCost) {
        this.assetId = assetId;
        this.assetTag = assetTag;
        this.name = name;
        this.purchaseCost = purchaseCost;
        this.status = AssetStatus.AVAILABLE;
    }

    public int getAssetId() {
        return assetId;
    }

    public String getAssetTag() {
        return assetTag;
    }

    public String getName() {
        return name;
    }

    public double getPurchaseCost() {
        return purchaseCost;
    }

    public AssetStatus getStatus() {
        return status;
    }

    public void setStatus(AssetStatus status) {
        this.status = status;
    }

    public abstract String getAssetType();

    public void displayDetails() {
        System.out.println("Asset ID      : " + assetId);
        System.out.println("Asset Tag     : " + assetTag);
        System.out.println("Name          : " + name);
        System.out.println("Purchase Cost : " + purchaseCost);
        System.out.println("Status        : " + status);
        System.out.println("Asset Type    : " + getAssetType());
    }
}