## Project ID
JFS-55

## Domain
IT Operations

## Description
The IT Asset Management System is used to manage an organisation's hardware and software assets throughout their lifecycle.

The system supports asset registration, employee requests, asset assignment and return, maintenance tracking, software licence management, warranty tracking, depreciation reports, and disposal records.

## User Roles

- Admin
- IT Asset Manager
- Employee

## Main Features

- Asset registration
- Asset request
- Asset assignment and return
- Warranty and AMC tracking
- Software licence tracking
- Maintenance history
- Depreciation reports
- Asset disposal


### Collection Usage

The system uses Java Collections Framework interfaces to manage assets, assignments, requests, maintenance records, software licences, and warranties.

| Collection | Usage | Reason |
|---|---|---|
| `List<Asset>` | Store registered assets | Maintains insertion order and supports iteration |
| `List<Assignment>` | Store assignment history | Preserves assignment and return records |
| `List<AssetRequest>` | Store asset requests | Maintains request history |
| `List<Maintenance>` | Store maintenance records | Supports tracking maintenance history |
| `List<SoftwareLicense>` | Store software licences | Supports licence management |
| `List<Warranty>` | Store warranty records | Supports warranty tracking |
| `Set<String>` | Track asset tags | Prevents duplicate asset tags |
| `Queue<AssetRequest>` | Track pending requests | Supports adding, viewing, and removing pending requests |
| `Map<Integer, AssetRequest>` | Retrieve requests by ID | Provides direct lookup of requests |

The asset registry and related records are maintained using collection interfaces, allowing implementation choices to change without modifying the variable declarations.

The request queue tracks pending requests, while the request map supports lookup by request ID. The IT Asset Manager can select a specific request for approval or rejection; processing is therefore not restricted to strict FIFO order.

### Verification

- Asset registration validates required fields and rejects duplicate asset tags, including case variations.
- Asset requests validate the requested asset.
- Approving a valid request creates an assignment and updates the asset status.
- Rejecting a request updates its status and removes it from the pending queue.
- Returning an assigned asset records the return and makes the asset available again.