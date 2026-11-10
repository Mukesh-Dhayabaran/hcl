import model.Asset;
import org.junit.jupiter.api.Test;
import repository.CrudRepository;
import repository.InMemoryRepository;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AssetCsvLoaderTest {

    @Test
    void loadsSeedAssetsFromClasspathCsv() throws IOException {
        CrudRepository<Asset, Integer> repository =
                new InMemoryRepository<>(Asset::getId);

        new AssetCsvLoader().load(repository);

        List<Asset> assets = repository.findAll();
        assertEquals(5, assets.size());
        assertEquals(1, assets.get(0).getAssetId());
        assertEquals("AST-001", assets.get(0).getAssetTag());
        assertEquals("Dell Laptop", assets.get(0).getAssetName());
        assertEquals("Laptop", assets.get(0).getCategory());
        assertEquals("AVAILABLE", assets.get(0).getStatus());
    }

    @Test
    void loadsAllSeedAssetIds() throws IOException {
        CrudRepository<Asset, Integer> repository =
                new InMemoryRepository<>(Asset::getId);

        new AssetCsvLoader().load(repository);

        for (int id = 1; id <= 5; id++) {
            Asset asset = repository.findById(id).orElse(null);
            assertNotNull(asset);
            assertEquals(id, asset.getAssetId());
        }
    }
}
