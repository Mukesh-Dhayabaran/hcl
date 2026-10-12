\# Day 5 – IAMS Class Diagram



\## 1. Entity Inheritance



```mermaid

classDiagram

&#x20;   class BaseEntity {

&#x20;       -int id

&#x20;       +getId() int

&#x20;   }



&#x20;   class Asset

&#x20;   class AssetCategory

&#x20;   class Assignment



&#x20;   class User {

&#x20;       <<abstract>>

&#x20;       -String name

&#x20;       -String email

&#x20;       +getName() String

&#x20;       +getEmail() String

&#x20;       +getRole() String

&#x20;       +displayPermissions() void

&#x20;   }



&#x20;   BaseEntity <|-- Asset

&#x20;   BaseEntity <|-- AssetCategory

&#x20;   BaseEntity <|-- Assignment

&#x20;   BaseEntity <|-- User



&#x20;   User <|-- Admin

&#x20;   User <|-- ITAssetManager

&#x20;   User <|-- Employee

```



\## 2. Depreciation Strategy



```mermaid

classDiagram

&#x20;   class DepreciationStrategy {

&#x20;       <<interface>>

&#x20;       +calculateAnnualDepreciation() double

&#x20;       +calculateCurrentValue() double

&#x20;   }



&#x20;   class StraightLineDepreciation

&#x20;   class WrittenDownValueDepreciation

&#x20;   class DepreciationReport



&#x20;   DepreciationStrategy <|.. StraightLineDepreciation

&#x20;   DepreciationStrategy <|.. WrittenDownValueDepreciation

&#x20;   DepreciationReport --> DepreciationStrategy : uses

```



\## Key Concepts



\- \*\*Inheritance:\*\* Common entity information is maintained in `BaseEntity`.

\- \*\*Abstract class:\*\* `User` provides common user details and defines role-specific behavior.

\- \*\*Polymorphism:\*\* `Admin`, `ITAssetManager`, and `Employee` implement role-specific behavior.

\- \*\*Strategy pattern:\*\* Different depreciation algorithms implement `DepreciationStrategy`.

\- \*\*Composition/reference:\*\* `DepreciationReport` uses a `DepreciationStrategy` to calculate depreciation.

