# lib-mask-core

Framework-independent data masking library for Java applications.

This library provides reusable masking contracts, strategies, configuration
models, and a strategy-based masking handler for protecting sensitive values
without coupling consumers to Spring or other application frameworks.

## Features

- Strategy-based data masking
- Email address masking
- Payment card number masking
- Phone number masking
- Document number masking
- Bank account number masking
- Extensible masking strategy contract
- Central strategy resolution by mask type
- Duplicate strategy detection
- Convenience masking facade
- Framework-independent design
- Java 25 compatible

## Package Structure

```text
pe.com.galaxy.enterprise.libs.mask
├── contract
│   └── MaskerService
├── handler
│   └── MaskingStrategyHandler
├── model
│   ├── MaskType
│   └── MaskingOptions
├── exception
│   └── MaskingException
├── strategy
│   ├── MaskingStrategy
│   ├── AccountMaskingStrategyImpl
│   ├── CardMaskingStrategyImpl
│   ├── DocumentMaskingStrategyImpl
│   ├── EmailMaskingStrategyImpl
│   └── PhoneMaskingStrategyImpl
└── MaskingUtils
```

## Core Concepts

### Masker Service

`MaskerService` defines the main masking contract exposed by the library.

A masking operation receives:

```text
value
+
MaskType
```

and returns the corresponding masked representation.

### Masking Strategy

`MaskingStrategy` defines the contract implemented by individual masking
algorithms.

Each strategy declares the `MaskType` it supports and contains the masking
rules associated with that type.

This allows additional masking strategies to be introduced without modifying
the central masking handler.

## Supported Mask Types

The library currently supports:

```text
EMAIL
CARD_NUMBER
PHONE
DOCUMENT
ACCOUNT_NUMBER
```

Each type is associated with a dedicated masking strategy.

## Default Masking Strategies

### Email

Email masking preserves the first character of the local part and the complete
domain while masking the remaining local-part characters.

### Card Number

Card-number masking preserves the first four and last four characters while
masking the intermediate characters.

### Phone

Phone-number masking preserves the final four characters while masking the
preceding content.

### Document

Document masking provides specialized behavior according to document length.

The current implementation distinguishes:

```text
8 characters  -> DNI-style masking
11 characters -> RUC-style masking
Other lengths -> Generic document masking
```

### Account Number

Account-number masking removes whitespace and preserves the final four
characters while masking the preceding content.

## MaskingStrategyHandler

`MaskingStrategyHandler` is the default implementation of `MaskerService`.

It receives the available masking strategies and indexes them by their
supported `MaskType`.

The handler is responsible for:

- registering masking strategies
- resolving the appropriate strategy
- delegating masking operations
- rejecting duplicate strategy registrations
- reporting missing strategies
- handling null and blank values before strategy execution

Only one strategy can be registered for each `MaskType`.

## MaskingOptions

`MaskingOptions` represents configurable masking parameters:

```text
visiblePrefix
visibleSuffix
maskCharacter
```

It provides a reusable model for masking rules that require configurable
visible prefixes, suffixes, and replacement characters.

## MaskingException

`MaskingException` represents failures occurring during masking operations.

It can contain:

```text
message
optional cause
```

The exception is primarily intended for masking resolution or execution
failures that cannot be represented as a normal masking result.

## MaskingUtils

`MaskingUtils` provides a convenience facade using the default strategies
included with the library.

The default configuration includes strategies for:

```text
Account numbers
Card numbers
Documents
Email addresses
Phone numbers
```

Applications that require custom strategy registration should use
`MaskingStrategyHandler` directly instead of relying on the static facade.

## Extensibility

The library follows the Strategy pattern.

New masking behavior can be introduced by implementing:

```text
MaskingStrategy
```

and assigning the implementation to a `MaskType`.

The central handler remains independent from the concrete masking algorithm.

Conceptually:

```text
MaskerService
      │
      ▼
MaskingStrategyHandler
      │
      ├── EMAIL ──────────> EmailMaskingStrategy
      ├── CARD_NUMBER ────> CardMaskingStrategy
      ├── PHONE ──────────> PhoneMaskingStrategy
      ├── DOCUMENT ───────> DocumentMaskingStrategy
      └── ACCOUNT_NUMBER ─> AccountMaskingStrategy
```

## Design Principles

This library follows these principles:

- Keep masking logic independent from application frameworks.
- Use explicit masking strategies for different sensitive-data categories.
- Prefer composition and strategy resolution over conditional logic.
- Keep concrete masking rules isolated from orchestration.
- Allow masking behavior to be extended through contracts.
- Avoid coupling masking logic to HTTP, persistence, or Spring.
- Keep sensitive-data protection concerns reusable across applications.

## Architectural Position

```text
┌──────────────────────────────┐
│     Consuming Application    │
├──────────────────────────────┤
│        MaskerService         │
├──────────────────────────────┤
│   MaskingStrategyHandler     │
├──────────────────────────────┤
│     Masking Strategies       │
│                              │
│ Email / Card / Phone         │
│ Document / Account           │
└──────────────────────────────┘
```

Applications should normally depend on the `MaskerService` abstraction rather
than coupling application code directly to individual strategies.

## Framework Independence

`lib-mask-core` is intentionally implemented as a pure Java library.

It does not require:

- Spring Boot
- Spring Framework
- JPA
- Hibernate
- Databases
- HTTP clients
- Servlet APIs
- Cloud SDKs

Spring-specific integration should be implemented separately, for example:

```text
lib-mask-spring-core
```

## Requirements

- Java 25
- Maven 3.9+ recommended

## Maven Coordinates

```xml
<dependency>
    <groupId>pe.com.galaxy.enterprise.java.libs</groupId>
    <artifactId>lib-mask-core</artifactId>
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

- strategy resolution
- duplicate strategy detection
- missing strategy handling
- null and blank values
- account masking
- card masking
- document masking
- email masking
- phone masking
- exception propagation
- default strategy registration through `MaskingUtils`

Tests should remain independent from Spring, databases, network resources,
and external infrastructure.

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

When the Maven Source and Javadoc plugins are configured, the build can
generate:

```text
lib-mask-core-0.0.1-SNAPSHOT.jar
lib-mask-core-0.0.1-SNAPSHOT-sources.jar
lib-mask-core-0.0.1-SNAPSHOT-javadoc.jar
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