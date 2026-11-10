package org.example.service;

import org.example.exception.*;
import org.example.model.*;
import org.example.repository.AssetRepository;

import java.util.*;

public class AssetService implements AssetOperations {

    private final AssetRepository repository = new AssetRepository();
    private final Set<String> registeredTags = new HashSet<>();

    @Override
    public void registerAsset(Asset asset) {
        Objects.requireNonNull(asset, "Asset cannot be null");

        if (asset.getAssetId() <= 0
                || !Double.isFinite(asset.getPurchaseCost())
                || asset.getPurchaseCost() <= 0
                || asset.getAssetTag() == null
                || asset.getAssetTag().isBlank()
                || asset.getName() == null
                || asset.getName().isBlank()) {

            throw new IllegalArgumentException("Invalid asset details");
        }

        String normalizedTag = asset.getAssetTag()
                .trim()
                .toUpperCase(Locale.ROOT);

        if (repository.existsById(asset.getAssetId())) {
            throw new DuplicateAssetException("Asset ID already exists");
        }

        if (registeredTags.contains(normalizedTag)) {
            throw new DuplicateAssetException("Asset tag already exists");
        }

        repository.save(asset);
        registeredTags.add(normalizedTag);
    }

    @Override
    public Asset findAsset(int assetId) throws AssetNotFoundException {
        return repository.findById(assetId)
                .orElseThrow(() ->
                        new AssetNotFoundException("Asset not found: " + assetId));
    }

    @Override
    public List<Asset> getAllAssets() {
        return repository.findAll();
    }

    public List<ComputerAsset> getComputers() {
        return repository.findByType(ComputerAsset.class);
    }

    public List<FurnitureAsset> getFurniture() {
        return repository.findByType(FurnitureAsset.class);
    }
}