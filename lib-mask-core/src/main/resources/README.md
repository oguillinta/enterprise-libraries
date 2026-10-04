# lib-mask-core

Framework-independent data masking library for Java applications.

This library provides reusable masking contracts, strategies, configuration
models, and a strategy-based masking handler for protecting sensitive values
without coupling consumers to Spring or other application frameworks.

## Features

- Strategy-based data masking
- Personal name masking
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
pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core
├── contract
│   └── MaskerService
│
├── handler
│   └── MaskingStrategyHandler
│
├── model
│   ├── MaskType
│   └── MaskingOptions
│
├── exception
│   └── MaskingException
│
├── strategy
│   ├── MaskingStrategy
│   ├── AccountMaskingStrategyImpl
│   ├── CardMaskingStrategyImpl
│   ├── DocumentMaskingStrategyImpl
│   ├── EmailMaskingStrategyImpl
│   ├── PersonNameMaskingStrategyImpl
│   └── PhoneMaskingStrategyImpl
│
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
PERSON_NAME
```

Each type is associated with a dedicated masking strategy.

## Default Masking Strategies

### Person Name

Personal-name masking preserves the first character of each name component
while masking the remaining characters.

Examples:

```text
Nasly               -> N****
Gomez               -> G****
Maria Lopez         -> M**** L****
Maria Lopez Torres  -> M**** L**** T*****
```

Multi-part names are processed independently, preserving the original logical
separation between name components.

Null or blank values are returned unchanged.

### Email

Email masking preserves the first character of the local part and the complete
domain while masking the remaining local-part characters.

Example:

```text
nasly.gomez@email.com
        ↓
n**********@email.com
```

### Card Number

Card-number masking preserves the first four and last four characters while
masking the intermediate characters.

Conceptually:

```text
4556123412345678
        ↓
4556********5678
```

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
Personal names
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
      ├── EMAIL ───────────> EmailMaskingStrategy
      ├── CARD_NUMBER ─────> CardMaskingStrategy
      ├── PHONE ───────────> PhoneMaskingStrategy
      ├── DOCUMENT ────────> DocumentMaskingStrategy
      ├── ACCOUNT_NUMBER ──> AccountMaskingStrategy
      └── PERSON_NAME ─────> PersonNameMaskingStrategy
```

## Design Principles

This library follows these principles:

- Keep masking logic independent from application frameworks.
- Use explicit masking strategies for different sensitive-data categories.
- Treat personal names as maskable personally identifiable information.
- Prefer composition and strategy resolution over conditional logic.
- Keep concrete masking rules isolated from orchestration.
- Allow masking behavior to be extended through contracts.
- Avoid coupling masking logic to HTTP, persistence, or Spring.
- Keep sensitive-data protection concerns reusable across applications.
- Preserve only the minimum amount of information required for usability.

## Architectural Position

```text
┌──────────────────────────────────┐
│       Consuming Application      │
├──────────────────────────────────┤
│          MaskerService           │
├──────────────────────────────────┤
│     MaskingStrategyHandler       │
├──────────────────────────────────┤
│        Masking Strategies        │
│                                  │
│ Name / Email / Card / Phone      │
│ Document / Account               │
└──────────────────────────────────┘
```

Applications should normally depend on the `MaskerService` abstraction rather
than coupling application code directly to individual strategies.

For simple framework-independent use cases, the `MaskingUtils` facade can be
used instead.

## Personal Data Protection

Masking is intended to reduce unnecessary exposure of sensitive or personally
identifiable information.

Typical candidates include:

```text
Personal names
Email addresses
Phone numbers
Payment card numbers
Document numbers
Bank account numbers
```

Masking should be applied according to the application's security and privacy
requirements.

Masking is not encryption and should not be treated as a replacement for
encryption when sensitive values must be securely stored.

## Framework Independence

`lib-mask-core` is intentionally implemented as a pure Java library.

It does not require:

```text
Spring Boot
Spring Framework
JPA
Hibernate
Databases
HTTP clients
Servlet APIs
Cloud SDKs
```

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
    <version>0.1.0-SNAPSHOT</version>
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
- personal-name masking
- multi-part name masking
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
lib-mask-core-0.1.0-SNAPSHOT.jar
lib-mask-core-0.1.0-SNAPSHOT-sources.jar
lib-mask-core-0.1.0-SNAPSHOT-javadoc.jar
```

## Versioning

Current development version:

```text
0.1.0-SNAPSHOT
```

Version `0.1.0` introduces personal-name masking through:

```text
MaskType.PERSON_NAME
PersonNameMaskingStrategyImpl
```

The project follows Semantic Versioning:

```text
MAJOR.MINOR.PATCH
```

While the library remains in initial development under version `0.x`, its API
may continue to evolve before the first stable `1.0.0` release.

A future stable release may follow:

```text
0.1.0-SNAPSHOT
      ↓
0.1.0
      ↓
1.0.0
```

## License

Licensed under the Apache License 2.0.

## Author

Oscar Guillinta