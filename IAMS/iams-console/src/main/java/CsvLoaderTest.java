import model.Asset;
import repository.CrudRepository;
import repository.InMemoryRepository;

import java.io.IOException;

public class CsvLoaderTest {

    public static void main(String[] args) throws IOException {
        CrudRepository<Asset, Integer> repository =
                new InMemoryRepository<>(Asset::getId);

        AssetCsvLoader loader = new AssetCsvLoader();
        loader.load(repository);

        System.out.println("Assets loaded: "
                + repository.findAll().size());

        for (Asset asset : repository.findAll()) {
            System.out.println(
                    asset.getId() + " | "
                            + asset.getAssetTag() + " | "
                            + asset.getAssetName()
            );
        }
    }
}