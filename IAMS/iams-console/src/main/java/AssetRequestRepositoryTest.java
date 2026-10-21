import model.AssetRequest;
import repository.AssetRequestRepository;

import java.time.LocalDate;

public class AssetRequestRepositoryTest {

    public static void main(String[] args) {
        AssetRequestRepository repository = new AssetRequestRepository();

        AssetRequest request = new AssetRequest(
                1, 101, 201, LocalDate.now(), "PENDING"
        );

        repository.save(request);

        if (repository.findById(1).isEmpty()) {
            throw new AssertionError("Saved request was not found");
        }

        if (!repository.existsById(1)) {
            throw new AssertionError("Request should exist");
        }

        if (repository.findAll().size() != 1) {
            throw new AssertionError("Expected one request");
        }

        if (!repository.deleteById(1)) {
            throw new AssertionError("Request deletion failed");
        }

        if (repository.existsById(1)) {
            throw new AssertionError("Deleted request still exists");
        }

        try {
            repository.save(null);
            throw new AssertionError("Null request should be rejected");
        } catch (IllegalArgumentException expected) {
            System.out.println("Null validation passed");
        }

        System.out.println("All AssetRequestRepository checks passed");
    }
}