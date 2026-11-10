# AI Usage Log — IT Asset Management System

## Entry 1: Warranty Expiry Feature (FR7)

**AI assistance used:** ChatGPT

**Purpose:** Review the warranty expiry logic and identify how warranty status is calculated.

**AI suggestion:** Use Java's `LocalDate` and `ChronoUnit.DAYS` to calculate the days remaining until warranty expiry. Classify warranties as expired, expiring soon, or active.

**My review:** I reviewed the `Warranty` class and confirmed that it calculates days until expiry and identifies warranties expiring within 30 days. The menu displays the corresponding status when the user checks warranty alerts.

**Validation:** Automated JUnit 5 tests now cover expired, current-day,
future, 30-day, and beyond-30-day warranty boundaries. The warranty also
supports an injectable `Clock` for deterministic tests and rejects a null
expiry date.

## Entry 2: Depreciation Report Feature (FR8)

**AI assistance used:** ChatGPT

**Purpose:** Review the Straight-Line depreciation calculation and report workflow.

**AI suggestion:** Calculate annual depreciation from purchase cost and residual value, then use the elapsed years to determine accumulated depreciation and current book value.

**My review:** I reviewed the existing depreciation workflow, including the input fields and report reuse for the same asset ID. I will verify the calculations with test inputs before considering the feature complete.

**Validation:** Automated JUnit 5 tests verify Straight-Line and
Written-Down Value calculations, residual-value handling, useful-life
boundaries, non-finite and invalid inputs, and strategy selection through
`DepreciationReport`.

## Entry 3: Code Review and Validation

**AI assistance used:** ChatGPT

**Purpose:** Identify missing requirements and avoid unnecessary changes to existing functionality.

**AI suggestion:** Verify warranty alert behaviour, depreciation report calculations, input validation, and build/test results before committing the changes.

**My review:** I will compare the observed application output against the requirements and correct only confirmed defects. AI-generated suggestions will be reviewed rather than accepted without verification.

**Validation:** The project currently has 57 passing JUnit 5 tests. The suite
covers generic repositories, asset registration and requests, assignment and
return, maintenance, licence-seat allocation and limits, warranty expiry,
depreciation validation, report strategy selection, and valid classpath CSV
loading. Maven verification was completed with `mvn clean test`.

The following limitations remain:

- Invalid CSV input is not directly covered because `AssetCsvLoader` reads a
  fixed classpath resource rather than an injectable reader or stream.
- `Maintenance` and `SoftwareLicense` constructors do not independently
  validate all input values; menu-level validation is tested.
- Licence-seat release is not implemented; only allocation is supported.
- `AssetManagementMenu` maintains duplicated mutable state across repositories
  and lists/maps and remains a large, mixed-responsibility class.
- Role-based access, immutable asset history, and sub-two-second reporting
  have not been implemented or measured.
