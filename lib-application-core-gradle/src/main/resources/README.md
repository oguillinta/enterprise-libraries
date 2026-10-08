# lib-application-core

Framework-independent application-layer abstractions for Java applications
following Clean Architecture, Hexagonal Architecture, Domain-Driven Design
(DDD), and CQRS-oriented patterns.

This library provides reusable contracts for commands, queries, handlers,
application results, and application-level exceptions without coupling the
application layer to Spring, HTTP, persistence, messaging, or infrastructure
technologies.

## Features

- Generic command abstraction
- Generic command handler contract
- Generic query abstraction
- Generic query handler contract
- Structured application results
- Support for successful and failed application outcomes
- Application-level exception abstraction
- Framework-independent design
- Suitable for CQRS-oriented architectures
- Java 25 compatible

## Package Structure

```text
pe.com.galaxy.enterprise.libs.application
├── command
│   ├── Command
│   └── CommandHandler
├── query
│   ├── Query
│   └── QueryHandler
├── result
│   └── ApplicationResult
└── exception
    └── ApplicationException
```

## Core Concepts

### Commands

Commands represent intentions to execute state-changing application operations.

They are modeled using:

```text
Command<R>
CommandHandler<C, R>
```

### Queries

Queries represent requests for information without expressing an intention
to modify application state.

They are modeled using:

```text
Query<R>
QueryHandler<Q, R>
```

### Application Results

`ApplicationResult<T>` provides a structured, transport-independent result
for application operations.

It contains:

```text
data
code
message
```

Expected successful and unsuccessful outcomes can be represented using:

```text
ApplicationResult.success(...)
ApplicationResult.failure(...)
```

### Application Exceptions

`ApplicationException` represents exceptional failures occurring during
application-layer execution, such as orchestration failures or errors
interacting with application ports.

Expected business outcomes should normally be modeled using domain behavior
or `ApplicationResult`, rather than exceptions.

## Result vs Exception

```text
ApplicationResult.success(...)
        │
        └── Successful expected outcome

ApplicationResult.failure(...)
        │
        └── Expected unsuccessful outcome

ApplicationException
        │
        └── Exceptional application execution failure
```

## Design Principles

- Keep the application layer independent from infrastructure.
- Keep commands and queries framework-independent.
- Use commands for state-changing intentions.
- Use queries for information retrieval.
- Keep business rules in the domain layer.
- Keep persistence implementations outside the application layer.
- Do not expose HTTP concepts from application abstractions.
- Use structured results for expected outcomes.
- Use application exceptions for exceptional execution failures.
- Prefer explicit contracts over framework-specific annotations.

## Architectural Position

```text
┌──────────────────────────┐
│        Web / API         │
├──────────────────────────┤
│       Application        │
│                          │
│ Command                  │
│ CommandHandler           │
│ Query                    │
│ QueryHandler             │
│ ApplicationResult        │
│ ApplicationException     │
├──────────────────────────┤
│         Domain           │
├──────────────────────────┤
│     Infrastructure       │
└──────────────────────────┘
```

`lib-application-core` contains reusable application-layer primitives and
should not depend on the web or infrastructure layers.

## Framework Independence

This library is intentionally implemented as pure Java.

It does not require:

- Spring Boot
- Spring Framework
- JPA
- Hibernate
- Servlet APIs
- Jakarta REST
- Databases
- Messaging brokers
- HTTP clients

## Requirements

- Java 25
- Maven 3.9+ recommended

## Maven Coordinates

```xml
<dependency>
    <groupId>pe.com.galaxy.enterprise.java.libs</groupId>
    <artifactId>lib-application-core</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

## Build

```bash
mvn clean verify
```

On Windows:

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

## Install Locally

```bash
mvn clean install
```

or:

```powershell
.\mvnw clean install
```

## Generate Javadocs

```bash
mvn javadoc:javadoc
```

Generated documentation:

```text
target/site/apidocs
```

## Generated Artifacts

```text
lib-application-core-0.0.1-SNAPSHOT.jar
lib-application-core-0.0.1-SNAPSHOT-sources.jar
lib-application-core-0.0.1-SNAPSHOT-javadoc.jar
```

## Versioning

Current development version:

```text
0.0.1-SNAPSHOT
```

Stable releases remove the `SNAPSHOT` suffix.

## License

Licensed under the Apache License 2.0.

## Author

Oscar Guillinta