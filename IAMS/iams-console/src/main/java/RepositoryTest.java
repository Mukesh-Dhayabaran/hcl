import model.Asset;
import repository.CrudRepository;
import repository.InMemoryRepository;

public class RepositoryTest {

    public static void main(String[] args) {
        CrudRepository<Asset, Integer> repository =
                new InMemoryRepository<>(Asset::getId);

        Asset asset = new Asset(
                1, "AST-001", "Dell Laptop", "Laptop", "Available"
        );

        repository.save(asset);

        System.out.println("Saved asset: "
                + repository.findById(1).orElseThrow().getAssetName());

        System.out.println("Asset count: "
                + repository.findAll().size());

        System.out.println("Exists: "
                + repository.existsById(1));

        repository.deleteById(1);

        System.out.println("Exists after deletion: "
                + repository.existsById(1));
    }
}