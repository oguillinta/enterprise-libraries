# lib-validation-core

Framework-independent validation library for Java applications.

This library provides reusable Jakarta Bean Validation constraints and
validators for common application and domain data such as identifiers,
currencies, documents, account numbers, card information, email addresses,
and monetary amounts.

## Features

- Jakarta Bean Validation integration
- UUID format validation
- ISO 4217 currency validation
- Bank account number validation
- Payment card number validation
- Luhn checksum validation
- Card expiration validation
- Email address validation
- Monetary amount validation
- Identification document validation
- Peru-oriented DNI and RUC validation
- Configurable validation constraints
- Framework-independent design
- Java 25 compatible

## Package Structure

```text
pe.com.galaxy.enterprise.libs.validation
├── annotation
│   ├── ValidAccountNumber
│   ├── ValidCardExpiration
│   ├── ValidCardNumber
│   ├── ValidCurrency
│   ├── ValidDocument
│   ├── ValidEmailAddress
│   ├── ValidMoneyAmount
│   └── ValidUuid
│
├── validator
│   ├── AccountNumberValidator
│   ├── CardExpirationValidator
│   ├── CardNumberValidator
│   ├── CurrencyValidator
│   ├── DocumentValidator
│   ├── EmailAddressValidator
│   ├── MoneyAmountValidator
│   └── UuidValidator
│
├── model
│   └── DocumentType
│
└── constant
    └── ValidationMessages
```

## Core Concepts

### Validation Constraints

The library exposes custom Jakarta Bean Validation annotations that can be
used on supported fields, method return values, parameters, record components,
and other applicable elements.

Each annotation is associated with a dedicated `ConstraintValidator`
implementation.

## Supported Constraints

### UUID

`ValidUuid` validates canonical UUID string representations.

The constraint validates format only. Required-value validation should be
handled separately using constraints such as `NotBlank`.

### Currency

`ValidCurrency` validates ISO 4217 currency codes.

Typical valid values include:

```text
PEN
USD
EUR
```

### Account Number

`ValidAccountNumber` validates numeric bank account numbers using configurable
minimum and maximum lengths.

The constraint validates structural format only and does not verify that an
account exists or belongs to a particular customer.

Default limits:

```text
Minimum length: 6
Maximum length: 20
```

### Card Number

`ValidCardNumber` validates payment card numbers.

The validator:

- removes spaces and hyphens
- requires between 13 and 19 digits
- applies the Luhn checksum algorithm

### Card Expiration

`ValidCardExpiration` validates expiration values using:

```text
yyyy-MM
```

A value is considered valid when it represents the current month or a future
month.

### Email Address

`ValidEmailAddress` validates the structural format of email addresses.

The validator performs format validation only and does not verify that the
email address exists or can receive messages.

### Money Amount

`ValidMoneyAmount` validates monetary values using configurable:

```text
minimum
maximum
scale
```

Default configuration:

```text
Minimum: 0.01
Maximum: 99999999999999999.99
Scale:   2
```

### Document

`ValidDocument` validates identification documents according to a configured
`DocumentType`.

Supported document types:

```text
DNI
RUC
CE
PASSPORT
```

The current implementation contains validation conventions intended for
applications operating in Peru.

## Document Validation

### DNI

DNI validation currently requires exactly:

```text
8 numeric digits
```

### RUC

RUC validation requires:

```text
11 numeric digits
```

and applies a check-digit calculation.

### Carné de Extranjería

CE validation currently accepts alphanumeric values with the configured
structural length rules.

### Passport

Passport validation currently accepts alphanumeric values with the configured
structural length rules.

Document validation verifies structural rules only. It does not confirm that
a document was actually issued by an authority.

## Optional Values

The custom validators generally consider `null` and blank values valid.

This follows the principle that:

```text
format validation
```

and:

```text
required-value validation
```

are separate responsibilities.

Consumers can combine these constraints with standard Jakarta Bean Validation
annotations such as:

```text
@NotNull
@NotBlank
@NotEmpty
```

when a value is mandatory.

## Constraint Configuration

Some constraints support configurable validation rules.

For example:

```text
ValidAccountNumber
├── minLength
└── maxLength
```

and:

```text
ValidMoneyAmount
├── min
├── max
└── scale
```

Invalid constraint configuration should fail during validator initialization
rather than being treated as invalid user input.

## Validation Messages

`ValidationMessages` centralizes the default messages used by the custom
constraints.

Current message categories include:

```text
INVALID_UUID
INVALID_CURRENCY
INVALID_DOCUMENT
INVALID_ACCOUNT_NUMBER
INVALID_CARD_NUMBER
INVALID_EMAIL_ADDRESS
INVALID_MONEY_AMOUNT
INVALID_CARD_EXPIRATION
```

Centralizing these messages keeps validation annotations consistent and avoids
duplicating message literals throughout the library.

## Design Principles

This library follows these principles:

- Keep validation logic reusable across applications.
- Separate required-value validation from format validation.
- Keep validators independent from Spring.
- Use Jakarta Bean Validation standard contracts.
- Keep domain-independent structural rules inside the validation library.
- Avoid database or external-service validation.
- Prefer deterministic validation rules.
- Fail fast when a constraint itself is configured incorrectly.
- Keep validation messages centralized.

## Architectural Position

```text
┌──────────────────────────────┐
│     Consuming Application    │
├──────────────────────────────┤
│   Validation Annotations     │
├──────────────────────────────┤
│        Validators            │
├──────────────────────────────┤
│ Jakarta Bean Validation API  │
└──────────────────────────────┘
```

The library validates input structure and reusable data constraints.

Business rules that depend on domain state should remain in the domain or
application layers instead of being implemented as generic validators.

## Framework Independence

`lib-validation-core` is independent from Spring.

It requires the Jakarta Bean Validation API but does not require:

- Spring Boot
- Spring Framework
- JPA
- Hibernate ORM
- Databases
- HTTP clients
- Servlet APIs
- Cloud SDKs

A Jakarta Bean Validation implementation is expected to be provided by the
consuming application at runtime.

## Requirements

- Java 25
- Jakarta Bean Validation
- Maven 3.9+ recommended

## Maven Coordinates

```xml
<dependency>
    <groupId>pe.com.galaxy.enterprise.java.libs</groupId>
    <artifactId>lib-validation-core</artifactId>
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

- valid and invalid UUID formats
- ISO 4217 currency codes
- account-number length and character rules
- invalid account constraint configuration
- card-number normalization
- Luhn checksum validation
- current, future, and expired card dates
- valid and invalid email formats
- monetary minimum and maximum values
- decimal scale validation
- DNI validation
- RUC validation and check digits
- CE validation
- passport validation
- null and blank optional values

Tests should remain independent from Spring, databases, external services,
and network resources.

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
lib-validation-core-0.0.1-SNAPSHOT.jar
lib-validation-core-0.0.1-SNAPSHOT-sources.jar
lib-validation-core-0.0.1-SNAPSHOT-javadoc.jar
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