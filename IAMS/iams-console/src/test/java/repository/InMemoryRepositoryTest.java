package repository;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryRepositoryTest {

    @Test
    void saveReturnsTheSameEntity() {
        InMemoryRepository<TestEntity, Integer> repository =
                new InMemoryRepository<>(TestEntity::id);
        TestEntity entity = new TestEntity(1, "Laptop");

        TestEntity saved = repository.save(entity);

        assertSame(entity, saved);
    }

    @Test
    void findByIdReturnsExistingEntity() {
        InMemoryRepository<TestEntity, Integer> repository =
                new InMemoryRepository<>(TestEntity::id);
        TestEntity entity = new TestEntity(1, "Laptop");
        repository.save(entity);

        Optional<TestEntity> result = repository.findById(1);

        assertTrue(result.isPresent());
        assertSame(entity, result.orElseThrow());
    }

    @Test
    void findByIdReturnsEmptyForUnknownId() {
        InMemoryRepository<TestEntity, Integer> repository =
                new InMemoryRepository<>(TestEntity::id);

        Optional<TestEntity> result = repository.findById(99);

        assertTrue(result.isEmpty());
    }

    @Test
    void findAllReturnsEntitiesInInsertionOrder() {
        InMemoryRepository<TestEntity, Integer> repository =
                new InMemoryRepository<>(TestEntity::id);
        TestEntity first = new TestEntity(1, "Laptop");
        TestEntity second = new TestEntity(2, "Monitor");
        TestEntity third = new TestEntity(3, "Keyboard");

        repository.save(first);
        repository.save(second);
        repository.save(third);

        assertEquals(List.of(first, second, third), repository.findAll());
    }

    @Test
    void deleteByIdRemovesExistingEntity() {
        InMemoryRepository<TestEntity, Integer> repository =
                new InMemoryRepository<>(TestEntity::id);
        TestEntity entity = new TestEntity(1, "Laptop");
        repository.save(entity);

        boolean deleted = repository.deleteById(1);

        assertTrue(deleted);
        assertTrue(repository.findById(1).isEmpty());
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void deleteByIdReturnsFalseForUnknownId() {
        InMemoryRepository<TestEntity, Integer> repository =
                new InMemoryRepository<>(TestEntity::id);

        boolean deleted = repository.deleteById(99);

        assertFalse(deleted);
    }

    @Test
    void saveReplacesEntityWithSameId() {
        InMemoryRepository<TestEntity, Integer> repository =
                new InMemoryRepository<>(TestEntity::id);
        TestEntity original = new TestEntity(1, "Laptop");
        TestEntity replacement = new TestEntity(1, "Desktop");
        repository.save(original);

        TestEntity saved = repository.save(replacement);

        assertSame(replacement, saved);
        assertSame(replacement, repository.findById(1).orElseThrow());
        assertEquals(List.of(replacement), repository.findAll());
    }

    @Test
    void findAllReturnsAnIndependentList() {
        InMemoryRepository<TestEntity, Integer> repository =
                new InMemoryRepository<>(TestEntity::id);
        TestEntity entity = new TestEntity(1, "Laptop");
        repository.save(entity);

        List<TestEntity> result = repository.findAll();
        result.clear();

        assertEquals(List.of(entity), repository.findAll());
    }

    private static final class TestEntity {
        private final int id;
        private final String name;

        private TestEntity(int id, String name) {
            this.id = id;
            this.name = name;
        }

        private int id() {
            return id;
        }
    }
}
