
package repository;

import model.AssetRequest;

import java.util.List;
import java.util.Optional;

public class AssetRequestRepository implements CrudRepository<AssetRequest, Integer> {

    private final InMemoryRepository<AssetRequest, Integer> repository =
            new InMemoryRepository<>(AssetRequest::getRequestId);

    @Override
    public AssetRequest save(AssetRequest entity) {
        if (entity == null) {
            throw new IllegalArgumentException(
                    "Asset request cannot be null"
            );
        }
        return repository.save(entity);
    }

    @Override
    public Optional<AssetRequest> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public List<AssetRequest> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean deleteById(Integer id) {
        return repository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }
}
