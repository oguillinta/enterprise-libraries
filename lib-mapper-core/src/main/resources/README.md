# lib-mapper-core

Reusable mapping abstractions and shared MapStruct configuration for Java
applications.

This library provides generic mapping contracts for one-way and bidirectional
object transformations, framework-neutral pagination models, page mapping
abstractions, and a shared MapStruct configuration intended to promote
consistent mapper implementations across enterprise applications.

## Features

- Generic one-way mapping contract
- Generic bidirectional mapping contract
- Collection mapping support
- Framework-neutral pagination result
- Paginated content mapping contract
- Shared MapStruct configuration
- Constructor-based dependency injection for generated mappers
- Strict unmapped-target validation
- Explicit null checking
- Java 25 compatible

## Package Structure

```text
pe.com.galaxy.enterprise.libs.mapper
├── contract
│   ├── Mapper
│   └── BidirectionalMapper
├── pagination
│   ├── PageResult
│   └── PageMapper
└── config
    └── CoreMapperConfig
```

## Core Concepts

### Mapper

`Mapper<S, T>` defines a one-way transformation contract between a source
type and a target type.

The contract supports:

```text
Single object mapping
Collection mapping
```

It can be used when an application only requires transformation in one
direction.

Typical mapping boundaries include:

```text
Entity -> Response
Domain -> DTO
External model -> Application model
```

## BidirectionalMapper

`BidirectionalMapper<D, E>` defines transformations between domain and
external representations in both directions.

It supports:

```text
Domain -> External
External -> Domain
Domain list -> External list
External list -> Domain list
```

The external representation can correspond to persistence entities,
integration models, DTOs, or other representations outside the domain model.

## Pagination

### PageResult

`PageResult<T>` is a framework-neutral representation of paginated data.

It contains:

```text
content
page
size
totalElements
totalPages
first
last
```

The model does not depend on persistence-specific or web-specific pagination
types.

This allows application and domain-facing components to expose pagination
metadata without depending on implementations such as:

```text
Spring Data Page
JPA pagination
HTTP pagination models
```

### PageMapper

`PageMapper<S, T>` defines a contract for transforming the content of a
paginated result while preserving its pagination metadata.

Conceptually:

```text
PageResult<Source>
        │
        │ map content
        ▼
PageResult<Target>
```

Pagination information such as page number, size, total elements, total pages,
and first/last indicators should remain unchanged during the mapping process.

## MapStruct Integration

`CoreMapperConfig` provides shared MapStruct configuration intended to
standardize generated mappers.

The current configuration uses:

```text
Component model:          Spring
Injection strategy:       Constructor
Unmapped target policy:   ERROR
Null value check:         ALWAYS
```

### Spring Component Model

Generated MapStruct implementations are created using the Spring component
model.

This allows generated mapper implementations to participate in Spring
dependency injection.

### Constructor Injection

Dependencies used by generated mappers are injected through constructors.

This favors explicit dependencies and improves testability.

### Unmapped Target Policy

The configuration uses:

```text
ReportingPolicy.ERROR
```

This causes compilation to fail when a target property is not mapped.

The objective is to prevent accidental data loss when models evolve without
their mapper definitions being updated.

### Null Value Check Strategy

The configuration uses:

```text
NullValueCheckStrategy.ALWAYS
```

MapStruct therefore generates explicit null checks where applicable during
mapping operations.

## Design Principles

This library follows these principles:

- Keep mapping contracts reusable across applications.
- Keep domain models independent from persistence models.
- Avoid leaking persistence-specific types into application contracts.
- Use explicit mapping boundaries between architectural layers.
- Prefer compile-time generated mapping over reflection-based mapping.
- Fail compilation when target properties are unintentionally unmapped.
- Preserve pagination metadata while transforming page content.
- Keep pagination abstractions independent from persistence frameworks.
- Centralize MapStruct configuration to ensure consistent mapper generation.

## Architectural Position

```text
┌──────────────────────────────┐
│      Consuming Layer         │
├──────────────────────────────┤
│ Mapper / BidirectionalMapper │
│         PageMapper           │
├──────────────────────────────┤
│       MapStruct Mapper       │
├──────────────────────────────┤
│      CoreMapperConfig        │
├──────────────────────────────┤
│          MapStruct           │
└──────────────────────────────┘
```

Mapping commonly occurs between architectural boundaries such as:

```text
Domain
  ↕
Persistence

Application
  ↕
Web / API

Application
  ↕
External integrations
```

The mapping library should not introduce business rules into these
transformations.

## Framework Considerations

The core mapping contracts and pagination abstractions are Java-oriented and
do not depend on persistence or web technologies.

However, `CoreMapperConfig` currently configures MapStruct using:

```text
MappingConstants.ComponentModel.SPRING
```

Therefore, generated mappers using this configuration are intended for
Spring-based applications.

The library itself should not contain:

- JPA entities
- Hibernate-specific mapping logic
- REST controllers
- HTTP response models
- Database access
- Business rules

## Requirements

- Java 25
- MapStruct
- Maven 3.9+ recommended

Spring is required by consuming applications that use `CoreMapperConfig`
with the configured Spring component model.

## Maven Coordinates

```xml
<dependency>
    <groupId>pe.com.galaxy.enterprise.java.libs</groupId>
    <artifactId>lib-mapper-core</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

## Build

```bash
mvn clean verify
```

On Windows using Maven Wrapper:

```powershell
.\mvnw clean verify
```

## Run Unit Tests

```bash
mvn test
```

or:

```powershell
.\mvnw clean test
```

The unit-test suite should cover:

- one-way object mapping
- one-way collection mapping
- domain-to-external mapping
- external-to-domain mapping
- bidirectional collection mapping
- paginated result metadata
- page content transformation
- pagination metadata preservation

The mapping contracts can be tested using lightweight test-only
implementations without Spring or external infrastructure.

## Install Locally

```bash
mvn clean install
```

or:

```powershell
.\mvnw clean install
```

The library can then be consumed from the local Maven repository using the
Maven coordinates shown above.

## Generate Javadocs

```bash
mvn javadoc:javadoc
```

Generated API documentation:

```text
target/site/apidocs
```

## Generated Artifacts

When Maven Source and Javadoc plugins are configured, the build can generate:

```text
lib-mapper-core-0.0.1-SNAPSHOT.jar
lib-mapper-core-0.0.1-SNAPSHOT-sources.jar
lib-mapper-core-0.0.1-SNAPSHOT-javadoc.jar
```

## Versioning

Current development version:

```text
0.0.1-SNAPSHOT
```

Stable releases remove the `SNAPSHOT` suffix.

Examples:

```text
0.0.1
0.1.0
1.0.0
```

## License

Licensed under the Apache License 2.0.

## Author

Oscar Guillinta