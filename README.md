# Caterpillar Mining Equipment API

This project is a study implementation of the Caterpillar Mining PC2 case. It exposes the command side of a small CQRS application and registers mining equipment while keeping the domain independent from HTTP and persistence concerns.

## Architecture

The code follows DDD, Ports and Adapters, and CQRS conventions inspired by the ACME Learning Center Platform:

- `mining/domain`: aggregate root, value objects, and repository port.
- `mining/application`: create command and command service.
- `mining/infrastructure`: JPA entity, converters, persistence assembler, Spring Data repository, and repository adapter.
- `mining/interfaces`: REST resources, assemblers, and controller.
- `shared`: geographic value object, application results, auditing, naming strategy, OpenAPI, and REST error handling.

The API intentionally contains only the required command endpoint; it does not include unrelated CRUD operations.

## Tech stack

- Java 25
- Spring Boot 4.1.2
- Spring Web MVC and Jakarta Bean Validation
- Spring Data JPA and Hibernate
- MySQL
- Springdoc OpenAPI / Swagger UI
- Maven
- AES-256/GCM authenticated encryption for serial numbers

## MySQL configuration

MySQL must be running and accessible. The application uses the `caterpillar` database and creates it when the configured MySQL account has permission.

The local defaults are:

| Environment variable | Default |
| --- | --- |
| `DATABASE_HOST` | `localhost` |
| `DATABASE_PORT` | `3306` |
| `DATABASE_NAME` | `caterpillar` |
| `DATABASE_USER` | `root` |
| `DATABASE_PASSWORD` | `root` |
| `JPA_DDL_AUTO` | `update` |
| `JPA_SHOW_SQL` | `true` |
| `SERVER_PORT` | `8080` |

Set `SERIAL_ENCRYPTION_KEY` to a Base64-encoded 32-byte secret outside local development. The committed value is only a development default. Changing this key after data has been stored prevents existing serial numbers from being decrypted.

PowerShell example:

```powershell
$env:DATABASE_USER = "root"
$env:DATABASE_PASSWORD = "your-local-password"
$env:SERIAL_ENCRYPTION_KEY = "your-base64-encoded-32-byte-key"
.\mvnw.cmd spring-boot:run
```

## Run and explore

The project requires a Java 25 JDK:

```powershell
.\mvnw.cmd clean spring-boot:run
```

Then open Swagger UI at:

```text
http://localhost:8080/swagger-ui/index.html
```

## Required endpoint

```http
POST /api/v1/equipment-units
Content-Type: application/json
```

Example request:

```json
{
  "model": "CAT 793F",
  "serialNumber": "CAT-793F-0001",
  "operationStatus": "ACTIVE",
  "assignedMineSite": "Antamina",
  "gpsLatitude": -9.531,
  "gpsLongitude": -77.059,
  "hoursOfOperation": 1250
}
```

A valid request returns `201 Created` with generated persistence and business identifiers, audit timestamps, and the original serial number. The database stores the serial number as randomized AES/GCM ciphertext. Registering the same serial number at the same mine returns `409 Conflict`; invalid coordinates, status, or request data return `400 Bad Request` as RFC 9457 problem details.


## Business Rules

The `MiningEquipmentUnit` aggregate follows these rules:

- `equipmentUnitId` is generated automatically as a UUID when the equipment unit is registered.
- `serialNumber` must be unique within the same `assignedMineSite`.
- Latitude must be between `-90` and `90`.
- Longitude must be between `-180` and `180`.
- `hoursOfOperation` must be zero or greater.
- `operationStatus` only accepts:
    - `ACTIVE`
    - `IN_MAINTENANCE`
    - `INACTIVE`
- `serialNumber` is stored encrypted in the database and automatically decrypted when read.
- Creation and modification timestamps are generated automatically.

## Project Structure

```text
pc2repasoswagger
├── mining
│   ├── domain
│   │   ├── model
│   │   │   ├── aggregates
│   │   │   └── valueobjects
│   │   └── repositories
│   ├── application
│   │   ├── commands
│   │   ├── commandservices
│   │   └── internal
│   ├── infrastructure
│   │   └── persistence
│   └── interfaces
│       └── rest
│
└── shared
    ├── domain
    ├── application
    ├── infrastructure
    └── interfaces



```md
## HTTP Responses

| Status | Meaning |
| --- | --- |
| `201 Created` | Equipment unit registered successfully |
| `400 Bad Request` | Invalid request data or business invariant |
| `409 Conflict` | Same serial number already exists in the same mine site |
| `500 Internal Server Error` | Unexpected application error |


## Request Flow

A successful POST request follows this flow:

```text
HTTP Request
    ↓
CreateMiningEquipmentUnitResource
    ↓
CreateMiningEquipmentUnitCommandFromResourceAssembler
    ↓
CreateMiningEquipmentUnitCommand
    ↓
MiningEquipmentUnitCommandService
    ↓
MiningEquipmentUnitCommandServiceImpl
    ↓
MiningEquipmentUnit Aggregate
    ↓
MiningEquipmentUnitRepository
    ↓
MiningEquipmentUnitRepositoryImpl
    ↓
MiningEquipmentUnitPersistenceAssembler
    ↓
MiningEquipmentUnitPersistenceEntity
    ↓
Spring Data JPA / Hibernate
    ↓
MySQL

## Author

Gabriel Yanac Flores
