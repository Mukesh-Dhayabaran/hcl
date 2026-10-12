# IAMS — UML Class Diagram

## 1. Entity and User Inheritance

```mermaid
classDiagram
    class BaseEntity {
        -int id
        #BaseEntity(int id)
        +getId() int
    }

    class Asset
    class AssetCategory
    class Assignment

    class User {
        <<abstract>>
        -String name
        -String email
        #User(int userId, String name, String email)
        +getName() String
        +getEmail() String
        +getRole()* String
        +displayPermissions()* void
    }

    class Admin {
        +getRole() String
        +displayPermissions() void
    }

    class ITAssetManager {
        +getRole() String
        +displayPermissions() void
    }

    class Employee {
        +getRole() String
        +displayPermissions() void
    }

    BaseEntity <|-- Asset
    BaseEntity <|-- AssetCategory
    BaseEntity <|-- Assignment
    BaseEntity <|-- User

    User <|-- Admin
    User <|-- ITAssetManager
    User <|-- Employee
```

## 2. Depreciation Strategy Pattern

```mermaid
classDiagram
    class DepreciationStrategy {
        <<interface>>
        +calculateAnnualDepreciation(double, double, int) double
        +calculateCurrentValue(double, double, int, int) double
    }

    class StraightLineDepreciation {
        +calculateAnnualDepreciation(double, double, int) double
        +calculateCurrentValue(double, double, int, int) double
    }

    class WrittenDownValueDepreciation {
        +calculateAnnualDepreciation(double, double, int) double
        +calculateCurrentValue(double, double, int, int) double
    }

    class DepreciationReport {
        -DepreciationStrategy strategy
        +getAnnualDepreciation() double
        +getAccumulatedDepreciation() double
        +getCurrentValue() double
    }

    DepreciationStrategy <|.. StraightLineDepreciation
    DepreciationStrategy <|.. WrittenDownValueDepreciation
    DepreciationReport --> DepreciationStrategy : uses
```

## Design Concepts

- **Inheritance:** `Asset`, `AssetCategory`, `Assignment`, and `User` inherit the common ID field from `BaseEntity`.
- **Abstraction:** `User` defines common user details and abstract role-specific methods.
- **Polymorphism:** `Admin`, `ITAssetManager`, and `Employee` provide their own implementations of `getRole()` and `displayPermissions()`.
- **Interface:** `DepreciationStrategy` defines the operations required for depreciation calculations.
- **Strategy pattern:** `StraightLineDepreciation` and `WrittenDownValueDepreciation` implement alternative depreciation algorithms.
- **Dependency:** `DepreciationReport` uses a `DepreciationStrategy` to calculate depreciation.