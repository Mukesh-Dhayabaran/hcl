package org.example.repository;

import org.example.model.Asset;

public class AssetRepository extends GenericRepository<Asset, Integer> {

    public AssetRepository() {
        super(Asset::getAssetId);
    }
}
