package org.example.service;

import org.example.exception.AssetNotFoundException;
import org.example.exception.DuplicateAssetException;
import org.example.model.Asset;
import org.example.model.ComputerAsset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AssetServiceTest {

    private AssetService assetService;

    @BeforeEach
    void setUp() {
        assetService = new AssetService();
    }

    private ComputerAsset createComputer(int id, String tag) {
        return new ComputerAsset(
                id, tag, "HP Laptop", 55000,
                "Intel i5", 8, 512
        );
    }

    @Test
    void shouldRegisterAssetSuccessfully() {
        Asset asset = createComputer(101, "AST-101");

        assetService.registerAsset(asset);

        assertEquals(1, assetService.getAllAssets().size());
        assertEquals(101, assetService.getAllAssets().get(0).getAssetId());
    }

    @Test
    void shouldRejectDuplicateAssetId() {
        assetService.registerAsset(createComputer(101, "AST-101"));

        assertThrows(
                DuplicateAssetException.class,
                () -> assetService.registerAsset(
                        createComputer(101, "AST-102")
                )
        );
    }

    @Test
    void shouldRejectDuplicateAssetTag() {
        assetService.registerAsset(createComputer(101, "AST-101"));

        assertThrows(
                DuplicateAssetException.class,
                () -> assetService.registerAsset(
                        createComputer(102, "AST-101")
                )
        );
    }

    @Test
    void shouldFindAssetById() throws AssetNotFoundException {
        assetService.registerAsset(createComputer(101, "AST-101"));

        Asset asset = assetService.findAsset(101);

        assertEquals("HP Laptop", asset.getName());
    }

    @Test
    void shouldThrowExceptionWhenAssetDoesNotExist() {
        assertThrows(
                AssetNotFoundException.class,
                () -> assetService.findAsset(999)
        );
    }

    @Test
    void shouldReturnAllRegisteredAssets() {
        assetService.registerAsset(createComputer(101, "AST-101"));
        assetService.registerAsset(createComputer(102, "AST-102"));

        List<Asset> assets = assetService.getAllAssets();

        assertEquals(2, assets.size());
    }

    @Test
    void shouldRejectNullAsset() {
        assertThrows(
                NullPointerException.class,
                () -> assetService.registerAsset(null)
        );
    }

    @Test
    void shouldRejectBlankAssetName() {
        ComputerAsset asset = new ComputerAsset(
                103, "AST-103", "   ",
                55000, "Intel i5", 8, 512
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> assetService.registerAsset(asset)
        );
    }

    @Test
    void shouldRejectNonFinitePurchaseCost() {
        ComputerAsset asset = new ComputerAsset(
                104, "AST-104", "HP Laptop",
                Double.NaN, "Intel i5", 8, 512
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> assetService.registerAsset(asset)
        );
    }

    // ComputerAsset constructor validation tests

    @Test
    void shouldRejectZeroRamSize() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ComputerAsset(
                        105, "AST-105", "HP Laptop",
                        55000, "Intel i5", 0, 512
                )
        );
    }

    @Test
    void shouldRejectNegativeRamSize() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ComputerAsset(
                        106, "AST-106", "HP Laptop",
                        55000, "Intel i5", -8, 512
                )
        );
    }

    @Test
    void shouldRejectZeroStorageSize() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ComputerAsset(
                        107, "AST-107", "HP Laptop",
                        55000, "Intel i5", 8, 0
                )
        );
    }

    @Test
    void shouldRejectEmptyProcessor() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new ComputerAsset(
                        108, "AST-108", "HP Laptop",
                        55000, "", 8, 512
                )
        );
    }
}
