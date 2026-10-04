# lib-encrypt-core

Framework-independent encryption library for Java applications.

This library provides reusable encryption contracts, configuration models,
cryptographic result types, and an AES-256-GCM implementation without coupling
the consumer to Spring or other application frameworks.

## Features

- AES-256 encryption
- Galois/Counter Mode (GCM)
- Authenticated encryption
- 256-bit encryption keys
- Random initialization vector generation
- 96-bit initialization vectors
- 128-bit authentication tags
- UTF-8 text encoding
- Base64 representation of encrypted values
- Defensive handling of encryption key material
- Framework-independent design
- Java 25 compatible

## Package Structure

```text
pe.com.galaxy.enterprise.libs.encrypt
├── contract
│   └── EncryptionService
├── model
│   ├── EncryptionAlgorithm
│   ├── EncryptionConfig
│   └── EncryptionResult
├── exception
│   ├── EncryptionException
│   └── InvalidEncryptionKeyException
└── service
    └── AesGcmEncryptionService
```

## Core Concepts

### Encryption Service

`EncryptionService` defines the public contract for encryption and decryption
operations.

The contract allows consumers to depend on an abstraction instead of a
specific cryptographic implementation.

### Encryption Algorithm

`EncryptionAlgorithm` represents the cryptographic transformations supported
by the library.

The current implementation supports:

```text
AES/GCM/NoPadding
```

Combined with a 256-bit key, this results in AES-256-GCM encryption.

### Encryption Configuration

`EncryptionConfig` contains the cryptographic configuration required by an
encryption service.

The current AES implementation requires:

```text
Key size: 32 bytes
Key size: 256 bits
```

The encryption key is defensively copied when the configuration is created
and whenever the key is accessed.

This prevents external modification of the internal key representation.

### Encryption Result

`EncryptionResult` represents the output of an encryption operation.

It contains:

```text
cipherText
initializationVector
```

Both values are represented as Base64-encoded strings.

The initialization vector must be stored together with the ciphertext because
it is required during decryption.

### AES-GCM Implementation

`AesGcmEncryptionService` provides the current cryptographic implementation.

Its configuration is:

```text
Algorithm:          AES
Mode:               GCM
Padding:            NoPadding
Key length:         256 bits
IV length:          96 bits
Authentication tag: 128 bits
Encoding:           UTF-8
Output encoding:    Base64
```

A new cryptographically secure initialization vector is generated for each
encryption operation.

## AES-256-GCM

The library uses:

```text
AES/GCM/NoPadding
```

The transformation is composed of:

```text
AES
 │
 └── Symmetric encryption algorithm

GCM
 │
 └── Galois/Counter Mode

NoPadding
 │
 └── No traditional block padding is required
```

The AES key size is determined by the encryption key supplied to the cipher.

The library requires exactly:

```text
32 bytes × 8 bits = 256 bits
```

Therefore, the current implementation uses:

```text
AES-256-GCM
```

## Authenticated Encryption

GCM provides authenticated encryption.

This means the encrypted data receives both:

```text
Confidentiality
+
Integrity / authenticity verification
```

If encrypted data is modified or decrypted with an incorrect key, the GCM
authentication verification fails and the value cannot be successfully
decrypted.

## Initialization Vector

AES-GCM requires an initialization vector, also commonly referred to as a
nonce.

This library generates a new:

```text
12-byte / 96-bit IV
```

for every encryption operation using `SecureRandom`.

The IV is not secret and is returned together with the ciphertext.

However, an IV must not be reused with the same encryption key for different
encryption operations.

## Key Management

`lib-encrypt-core` performs cryptographic operations but does not manage the
lifecycle or storage of encryption keys.

Encryption keys should not be:

- hardcoded in source code
- committed to Git
- stored directly in application configuration repositories
- logged
- exposed through APIs

Consumers should obtain encryption keys from an appropriate secret-management
solution.

Examples include:

```text
Environment secrets
Secret managers
Cloud key-management services
Azure Key Vault
Hardware security modules
```

Key-management integrations belong outside this core library.

## Design Principles

This library follows these principles:

- Keep cryptographic functionality independent from application frameworks.
- Depend on encryption abstractions rather than concrete implementations.
- Keep cryptographic configuration explicit.
- Generate a new initialization vector for each encryption operation.
- Use authenticated encryption.
- Avoid exposing mutable internal key material.
- Keep key storage and secret management outside the core library.
- Translate low-level cryptographic failures into library-specific exceptions.
- Avoid coupling encryption logic to HTTP, persistence, or Spring.

## Architectural Position

```text
┌─────────────────────────────┐
│      Consuming Service      │
├─────────────────────────────┤
│     EncryptionService       │
│          Contract           │
├─────────────────────────────┤
│ AesGcmEncryptionService     │
├─────────────────────────────┤
│       Java JCA / JCE        │
│ Cipher / SecretKey / GCM    │
└─────────────────────────────┘
```

Applications should normally depend on the `EncryptionService` contract
rather than directly coupling business code to cryptographic APIs.

## Framework Independence

`lib-encrypt-core` is intentionally implemented as a pure Java library.

It does not require:

- Spring Boot
- Spring Framework
- JPA
- Hibernate
- Databases
- Servlet APIs
- HTTP clients
- Cloud SDKs

Spring-specific configuration should be provided by a separate integration
library such as:

```text
lib-encrypt-spring-core
```

## Requirements

- Java 25
- Maven 3.9+ recommended

## Maven Coordinates

```xml
<dependency>
    <groupId>pe.com.galaxy.enterprise.java.libs</groupId>
    <artifactId>lib-encrypt-core</artifactId>
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

- valid AES-256 configuration
- invalid encryption key lengths
- null encryption configuration values
- defensive key copies
- encryption and decryption round trips
- UTF-8 values
- random IV generation
- IV length validation
- different ciphertext for repeated encryption
- corrupted ciphertext
- invalid Base64 values
- decryption using an incorrect key
- null input validation

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

Generated documentation:

```text
target/site/apidocs
```

## Generated Artifacts

When Maven source and Javadoc plugins are configured, the build can generate:

```text
lib-encrypt-core-0.0.1-SNAPSHOT.jar
lib-encrypt-core-0.0.1-SNAPSHOT-sources.jar
lib-encrypt-core-0.0.1-SNAPSHOT-javadoc.jar
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