# AI-Generated Code Review — IAMS

## Component Reviewed

- **Entity:** `model.AssetRequest` (existing entity)
- **Repository:** `repository.AssetRequestRepository` (generated with GitHub Copilot)
- **Storage:** Existing `InMemoryRepository<AssetRequest, Integer>`

## Review Table

| Review area | Finding | Action taken |
|---|---|---|
| Encapsulation | The internal repository is `private final`. | No change required. |
| Collection safety | `findAll()` delegates to the existing repository, which returns a new list. | No change required. |
| Null handling | The generated `save()` method did not explicitly reject a null entity. | Added an `IllegalArgumentException` check. |
| Duplicate IDs | Saving an existing ID replaces the stored request. | Retained the existing repository's update-by-ID behaviour. |
| Exception handling | The generated class does not swallow exceptions. | No change required. |
| `equals()` / `hashCode()` | The repository wrapper does not depend on entity equality for its CRUD operations. | No change required. |

## Verification

- The original manual repository checks covered save, find, list, existence,
  deletion, and null validation.
- The repository behavior is now also covered by JUnit 5 tests.
- The project currently has 57 passing JUnit 5 tests across repository,
  console workflow, maintenance, licence, warranty, depreciation, and valid
  CSV-loading scenarios.
- `mvn clean test`: BUILD SUCCESS.
- Test result: 57 tests run, 0 failures, 0 errors, 0 skipped.

## Limitations

The original AI review focused on `AssetRequestRepository`; its null
validation finding was addressed with an `IllegalArgumentException`, while
duplicate-ID replacement behavior was retained from `InMemoryRepository`.

Later verification added automated tests for warranty expiry alerts (FR7) and
depreciation calculations (FR8), including the validation fixes for null
warranty expiry dates, injectable warranty clocks, and invalid depreciation
inputs. These later changes are separate from the original repository review.

Remaining limitations are:

- Invalid CSV input is not directly tested because the loader reads a fixed
  classpath resource.
- `Maintenance` and `SoftwareLicense` constructors do not independently
  validate all input values.
- Licence-seat release is not implemented.
- `AssetManagementMenu` contains duplicated mutable state across repositories
  and collections and remains a large mixed-responsibility class.
- Role-based access, immutable asset history, and sub-two-second reporting
  have not been implemented or measured.
