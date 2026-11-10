package repository;

import model.AssetRequest;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AssetRequestRepositoryTest {

    private static final LocalDate REQUEST_DATE =
            LocalDate.of(2026, 11, 10);

    @Test
    void saveReturnsAndStoresTheSameRequest() {
        AssetRequestRepository repository = new AssetRequestRepository();
        AssetRequest request = request(1, "PENDING");

        AssetRequest saved = repository.save(request);

        assertSame(request, saved);
        assertSame(request, repository.findById(1).orElseThrow());
    }

    @Test
    void findByIdReturnsEmptyForUnknownId() {
        AssetRequestRepository repository = new AssetRequestRepository();

        Optional<AssetRequest> result = repository.findById(99);

        assertTrue(result.isEmpty());
    }

    @Test
    void findAllReturnsRequestsInInsertionOrder() {
        AssetRequestRepository repository = new AssetRequestRepository();
        AssetRequest first = request(1, "PENDING");
        AssetRequest second = request(2, "APPROVED");

        repository.save(first);
        repository.save(second);

        assertEquals(List.of(first, second), repository.findAll());
    }

    @Test
    void existsByIdReflectsWhetherRequestIsStored() {
        AssetRequestRepository repository = new AssetRequestRepository();
        AssetRequest request = request(1, "PENDING");

        assertFalse(repository.existsById(1));

        repository.save(request);

        assertTrue(repository.existsById(1));
    }

    @Test
    void deleteByIdRemovesExistingRequest() {
        AssetRequestRepository repository = new AssetRequestRepository();
        repository.save(request(1, "PENDING"));

        boolean deleted = repository.deleteById(1);

        assertTrue(deleted);
        assertFalse(repository.existsById(1));
        assertTrue(repository.findAll().isEmpty());
    }

    @Test
    void deleteByIdReturnsFalseForUnknownId() {
        AssetRequestRepository repository = new AssetRequestRepository();

        assertFalse(repository.deleteById(99));
    }

    @Test
    void saveReplacesRequestWithSameId() {
        AssetRequestRepository repository = new AssetRequestRepository();
        AssetRequest original = request(1, "PENDING");
        AssetRequest replacement = request(1, "REJECTED");
        repository.save(original);

        AssetRequest saved = repository.save(replacement);

        assertSame(replacement, saved);
        assertSame(replacement, repository.findById(1).orElseThrow());
        assertEquals(List.of(replacement), repository.findAll());
    }

    @Test
    void saveRejectsNullRequest() {
        AssetRequestRepository repository = new AssetRequestRepository();

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> repository.save(null)
        );

        assertEquals("Asset request cannot be null", exception.getMessage());
    }

    private static AssetRequest request(int requestId, String status) {
        return new AssetRequest(
                requestId,
                100 + requestId,
                200 + requestId,
                REQUEST_DATE,
                status
        );
    }
}
