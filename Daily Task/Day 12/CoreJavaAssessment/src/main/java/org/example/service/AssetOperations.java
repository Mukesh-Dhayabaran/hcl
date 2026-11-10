package org.example.service;

import org.example.exception.AssetNotFoundException;
import org.example.model.Asset;

import java.util.List;

public interface AssetOperations {

    void registerAsset(Asset asset);

    Asset findAsset(int assetId) throws AssetNotFoundException;

    List<Asset> getAllAssets();
}