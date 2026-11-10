
package org.example.model;

public class FurnitureAsset extends Asset {

    private String material;
    private String furnitureCategory;

    public FurnitureAsset(int assetId, String assetTag, String name,
                          double purchaseCost, String material,
                          String furnitureCategory) {

        super(assetId, assetTag, name, purchaseCost);

        this.material = material;
        this.furnitureCategory = furnitureCategory;
    }

    public String getMaterial() {
        return material;
    }

    public String getFurnitureCategory() {
        return furnitureCategory;
    }

    @Override
    public String getAssetType() {
        return "Furniture";
    }

    @Override
    public void displayDetails() {
        super.displayDetails();

        System.out.println("Material      : " + material);
        System.out.println("Category      : " + furnitureCategory);
    }
}
