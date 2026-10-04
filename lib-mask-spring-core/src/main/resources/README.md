# lib-mask-spring-core

Spring and Jackson integration for `lib-mask-core`.

This library provides Spring configuration and Jackson serialization support
for applying reusable data-masking strategies exposed by `lib-mask-core`.

It enables declarative masking of sensitive fields while keeping the actual
masking algorithms isolated in the core library.

## Features

- Spring integration for `lib-mask-core`
- Automatic `MaskerService` configuration
- Jackson serialization integration
- Declarative field masking through `@Masked`
- Mask type selection through `MaskType`
- Central registration of default masking strategies
- Personal name masking
- Email address masking
- Payment card number masking
- Phone number masking
- Document number masking
- Bank account number masking
- Separation between core masking rules and framework integration

## Package Structure

```text
pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core
├── annotation
│   └── Masked
│
├── configuration
│   └── MaskingSpringConfig
│
└── jackson
    ├── MaskingValueSerializer
    └── MaskingValueSerializerModifier
```

## Core Dependency

`lib-mask-spring-core` builds on top of:

```text
lib-mask-core
```

The core library contains the masking contracts, strategies, handler, models,
and masking rules.

This module contains only the Spring and Jackson integration required to apply
those rules automatically in Spring-based applications.

Conceptually:

```text
lib-mask-core
      │
      │ masking contracts and strategies
      ▼
lib-mask-spring-core
      │
      │ Spring + Jackson integration
      ▼
Consuming Application
```

## Masked Annotation

`@Masked` provides declarative masking for values serialized through Jackson.

The annotation associates a field or property with a `MaskType`.

Conceptually:

```java
@Masked(MaskType.EMAIL)
private String email;
```

or:

```java
@Masked(MaskType.PERSON_NAME)
private String firstName;
```

The annotation itself does not implement masking logic.

It provides metadata that is interpreted by the Jackson integration and
delegated to the `MaskerService`.

## Supported Mask Types

The Spring integration supports the masking types provided by
`lib-mask-core`:

```text
ACCOUNT_NUMBER
CARD_NUMBER
DOCUMENT
EMAIL
PERSON_NAME
PHONE
```

Each type is resolved through the corresponding masking strategy registered
in the `MaskerService`.

## Spring Configuration

`MaskingSpringConfig` provides the Spring configuration required by the
integration module.

It creates the default `MaskerService` using a `MaskingStrategyHandler`.

The default strategy set includes:

```text
AccountMaskingStrategyImpl
CardMaskingStrategyImpl
DocumentMaskingStrategyImpl
EmailMaskingStrategyImpl
PersonNameMaskingStrategyImpl
PhoneMaskingStrategyImpl
```

Conceptually:

```text
Spring Container
      │
      ▼
MaskingSpringConfig
      │
      ▼
MaskerService
      │
      ▼
MaskingStrategyHandler
      │
      ├── ACCOUNT_NUMBER
      ├── CARD_NUMBER
      ├── DOCUMENT
      ├── EMAIL
      ├── PERSON_NAME
      └── PHONE
```

## Jackson Integration

### MaskingValueSerializer

`MaskingValueSerializer` applies masking during Jackson serialization.

It delegates the actual masking operation to the configured `MaskerService`
instead of implementing masking rules directly.

This preserves the separation between:

```text
serialization concern
```

and:

```text
masking concern
```

### MaskingValueSerializerModifier

`MaskingValueSerializerModifier` customizes Jackson property serialization.

It identifies properties configured for masking and assigns the appropriate
masking serializer.

This allows masking to occur automatically when an annotated object is
serialized.

## Serialization Flow

The masking process follows this general flow:

```text
Application Object
      │
      ▼
Jackson Serialization
      │
      ▼
@Masked metadata detected
      │
      ▼
MaskingValueSerializerModifier
      │
      ▼
MaskingValueSerializer
      │
      ▼
MaskerService
      │
      ▼
MaskingStrategyHandler
      │
      ▼
Specific MaskingStrategy
      │
      ▼
Masked JSON value
```

The consuming application does not need to invoke individual masking
strategies manually when using the annotation-based integration.

## Personal Name Masking

Version `1.1.0` adds support for personal-name masking through:

```text
MaskType.PERSON_NAME
```

and:

```text
PersonNameMaskingStrategyImpl
```

Typical results include:

```text
Nasly               -> N****
Gomez               -> G****
Maria Lopez         -> M**** L****
Maria Lopez Torres  -> M**** L**** T*****
```

This allows applications to protect names in serialized responses in the same
way they already protect email addresses, documents, account numbers, and
payment-card data.

## Architectural Position

```text
┌─────────────────────────────────┐
│      Consuming Application      │
├─────────────────────────────────┤
│            @Masked              │
├─────────────────────────────────┤
│      Jackson Integration        │
│                                 │
│ MaskingValueSerializerModifier  │
│ MaskingValueSerializer          │
├─────────────────────────────────┤
│          MaskerService          │
├─────────────────────────────────┤
│       lib-mask-core             │
│                                 │
│ MaskingStrategyHandler          │
│ Masking Strategies              │
└─────────────────────────────────┘
```

The Spring integration layer should not contain masking algorithms.

Those rules belong to `lib-mask-core`.

## Separation of Responsibilities

The libraries have different responsibilities:

```text
lib-mask-core
├── masking contracts
├── mask types
├── masking strategies
├── masking handler
└── framework-independent behavior

lib-mask-spring-core
├── Spring configuration
├── @Masked integration
└── Jackson serialization support
```

This allows `lib-mask-core` to remain reusable in applications that do not use
Spring.

## Design Principles

This library follows these principles:

- Keep concrete masking algorithms in `lib-mask-core`.
- Keep Spring-specific integration outside the core library.
- Use annotation-driven masking for serialization concerns.
- Delegate masking behavior to `MaskerService`.
- Avoid duplicating masking rules in Jackson serializers.
- Keep sensitive-data protection reusable across Spring applications.
- Allow new `MaskType` values to be integrated consistently.
- Keep application DTOs free from explicit masking code.

## Masking and Encryption

Masking and encryption address different concerns.

```text
Encryption
    ↓
Protects sensitive values while stored or transmitted.

Masking
    ↓
Reduces exposure when values are presented to consumers.
```

A value may therefore be:

```text
encrypted at rest
        ↓
decrypted internally
        ↓
masked during API serialization
```

`lib-mask-spring-core` addresses the final presentation/serialization concern.

It is not a replacement for encryption.

## Requirements

- Java 25
- Spring Framework 7+
- Jackson
- `lib-mask-core` 1.1.0
- Maven 3.9+ recommended

## Maven Coordinates

```xml
<dependency>
    <groupId>pe.com.galaxy.enterprise.java.libs</groupId>
    <artifactId>lib-mask-spring-core</artifactId>
    <version>1.1.0</version>
</dependency>
```

`lib-mask-core` is declared as a dependency of this module and can therefore
be supplied transitively to consuming applications.

## Core Library Dependency

The module depends on:

```xml
<dependency>
    <groupId>pe.com.galaxy.enterprise.java.libs</groupId>
    <artifactId>lib-mask-core</artifactId>
    <version>1.1.0</version>
</dependency>
```

Keeping both modules on compatible versions is recommended.

For example:

```text
lib-mask-core         1.1.0
lib-mask-spring-core  1.1.0
```

## Usage

A response model can declare masking requirements directly:

```java
public record CustomerResponse(

        @Masked(MaskType.PERSON_NAME)
        String firstName,

        @Masked(MaskType.PERSON_NAME)
        String lastName,

        @Masked(MaskType.EMAIL)
        String email

) {
}
```

A value such as:

```text
firstName = Nasly
lastName  = Gomez
email     = nasly.gomez@email.com
```

can then be serialized with protected values such as:

```json
{
  "firstName": "N****",
  "lastName": "G****",
  "email": "n**********@email.com"
}
```

The exact output is determined by the masking strategies supplied by
`lib-mask-core`.

## Testing

Unit tests should cover:

- Spring masking configuration
- default `MaskerService` creation
- registration of all default masking strategies
- `@Masked` metadata handling
- Jackson serializer selection
- Jackson masking serialization
- email masking during serialization
- card masking during serialization
- document masking during serialization
- account-number masking during serialization
- phone masking during serialization
- personal-name masking during serialization
- null values
- blank values
- unsupported or invalid masking configuration

Tests for individual masking algorithms should remain in `lib-mask-core`.

The Spring module should primarily test framework integration.

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

## Install Locally

```bash
mvn clean install
```

or:

```powershell
.\mvnw clean install
```

## Publish

When distribution management is configured, the library can be published
with:

```bash
mvn deploy
```

or on Windows:

```powershell
.\mvnw deploy
```

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
lib-mask-spring-core-1.1.0.jar
lib-mask-spring-core-1.1.0-sources.jar
lib-mask-spring-core-1.1.0-javadoc.jar
```

## Versioning

Current stable version:

```text
1.1.0
```

Version `1.1.0` adds Spring/Jackson integration support for:

```text
MaskType.PERSON_NAME
```

through the new personal-name masking strategy provided by
`lib-mask-core:1.1.0`.

The project follows Semantic Versioning:

```text
MAJOR.MINOR.PATCH
```

Examples:

```text
1.0.0  Initial stable release
1.1.0  Backward-compatible functionality
1.1.1  Backward-compatible bug fix
2.0.0  Breaking API change
```

## License

Licensed under the Apache License 2.0.

## Author

Oscar Guillinta