
package org.example.repository;

import java.util.*;
import java.util.function.Function;

public class GenericRepository<T, ID> {

    private final Map<ID, T> storage = new LinkedHashMap<>();
    private final Function<T, ID> idExtractor;

    public GenericRepository(Function<T, ID> idExtractor) {
        this.idExtractor = Objects.requireNonNull(idExtractor);
    }

    public void save(T entity) {
        Objects.requireNonNull(entity, "Entity cannot be null");

        ID id = Objects.requireNonNull(
                idExtractor.apply(entity), "ID cannot be null");

        storage.put(id, entity);
    }

    public Optional<T> findById(ID id) {
        return Optional.ofNullable(storage.get(id));
    }

    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public boolean deleteById(ID id) {
        return storage.remove(id) != null;
    }

    public boolean existsById(ID id) {
        return storage.containsKey(id);
    }

    public int count() {
        return storage.size();
    }

    public <R extends T> List<R> findByType(Class<R> type) {
        List<R> results = new ArrayList<>();

        for (T entity : storage.values()) {
            if (type.isInstance(entity)) {
                results.add(type.cast(entity));
            }
        }

        return results;
    }
}
