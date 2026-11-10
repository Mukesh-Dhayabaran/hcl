
package org.example.model;

public class ComputerAsset extends Asset {

    private String processor;
    private int ramSize;
    private int storageSize;

    public ComputerAsset(int assetId, String assetTag, String name,
                         double purchaseCost, String processor,
                         int ramSize, int storageSize) {

        super(assetId, assetTag, name, purchaseCost);

        if (processor == null || processor.isBlank()) {
            throw new IllegalArgumentException("Processor cannot be empty");
        }

        if (ramSize <= 0) {
            throw new IllegalArgumentException("RAM must be positive");
        }

        if (storageSize <= 0) {
            throw new IllegalArgumentException("Storage must be positive");
        }

        this.processor = processor;
        this.ramSize = ramSize;
        this.storageSize = storageSize;

    }

    public String getProcessor() {
        return processor;
    }

    public int getRamSize() {
        return ramSize;
    }

    public int getStorageSize() {
        return storageSize;
    }

    @Override
    public String getAssetType() {
        return "Computer";
    }

    @Override
    public void displayDetails() {
        super.displayDetails();

        System.out.println("Processor     : " + processor);
        System.out.println("RAM           : " + ramSize + " GB");
        System.out.println("Storage       : " + storageSize + " GB");
    }
}
