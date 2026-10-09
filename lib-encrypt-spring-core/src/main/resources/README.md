# lib-encrypt-spring-core

Spring, Spring Boot, and JPA integration for `lib-encrypt-core`.

This library provides Spring configuration, Spring Boot auto-configuration, and JPA persistence integration
for applying reusable encryption services exposed by `lib-encrypt-core`.

It enables applications to use AES-256-GCM encryption through dependency injection while keeping
the actual cryptographic implementation isolated in the core library.

Starting with version `1.1.0`, the library can be discovered automatically by
Spring Boot without requiring consuming applications to manually create or
register the default `EncryptionService`.

## Features

- Spring integration for `lib-encrypt-core`
- Spring Boot auto-configuration
- Automatic `EncryptionService` configuration
- Automatic discovery through `AutoConfiguration.imports`
- External encryption-key configuration through Spring Boot properties
- Base64 decoding of the configured encryption key
- AES-256-GCM encryption through `lib-encrypt-core`
- Automatic creation of `AesGcmEncryptionService`
- Conditional bean registration through `@ConditionalOnMissingBean`
- Support for custom `EncryptionService` implementations
- JPA integration through `EncryptedStringConverter`
- Transparent encryption before persistence
- Transparent decryption after retrieval
- Dependency injection support
- Separation between cryptographic logic and framework integration
- Java 25 compatible

## Package Structure

```text
pe.com.galaxy.enterprise.java.libs.lib_encrypt_spring_core
├── codec
│   └── EncryptionSpringConfig
│
├── config
│   ├── EncryptionProperties
│   └── EncryptionAutoConfiguration
│
└── converter
    └── EncryptedStringConverter
```

The Spring Boot auto-configuration metadata is located under:

```text
src/main/resources
└── META-INF
    └── spring
        └── org.springframework.boot.autoconfigure.AutoConfiguration.imports
```

## Core Dependency

`lib-encrypt-spring-core` builds on top of:

```text
lib-encrypt-core
```

The core library contains the encryption contracts, configuration model,
encryption algorithms, AES-GCM implementation, and framework-independent
cryptographic behavior.

This module contains only the Spring, Spring Boot, and JPA integration required
to use those capabilities conveniently in Spring-based applications.

Conceptually:

```text
lib-encrypt-core
      │
      │ encryption contracts and AES-GCM implementation
      ▼
lib-encrypt-spring-core
      │
      │ Spring + Spring Boot + JPA integration
      ▼
Consuming Application
```

This separation allows the encryption algorithms to remain independent from
Spring, Spring Boot, JPA, and any specific persistence provider.

## Encryption Service

`lib-encrypt-core` exposes encryption functionality through:

```text
EncryptionService
```

with the default AES-GCM implementation:

```text
AesGcmEncryptionService
```

The Spring integration module allows the service to be managed by the Spring
container rather than being instantiated manually by every consuming
application.

Without Spring integration, an application could manually construct the
service:

```java
byte[] key =
        Base64.getDecoder()
                .decode(encodedKey);

EncryptionConfig config =
        new EncryptionConfig(
                key,
                EncryptionAlgorithm.AES_GCM
        );

EncryptionService encryptionService =
        new AesGcmEncryptionService(config);
```

This approach works, but the consuming application becomes responsible for:

```text
reading configuration
        │
        ▼
decoding the key
        │
        ▼
building EncryptionConfig
        │
        ▼
creating AesGcmEncryptionService
```

`lib-encrypt-spring-core` moves that integration responsibility into a reusable
Spring library.

## Encryption Properties

Starting with version `1.1.0`, the library exposes Spring Boot configuration
properties through:

```text
EncryptionProperties
```

The configuration prefix is:

```text
galaxy.encryption
```

The primary property is:

```text
key
```

For example:

```yaml
galaxy:
  encryption:
    key: ${ENCRYPTION_KEY}
```

The value is read from Spring's configuration environment.

The actual secret should normally be provided externally through an
environment variable, secret manager, deployment configuration, or another
secure configuration mechanism.

The library does not require the secret to be stored directly inside the
application source code.

## Encryption Key Representation

The configured encryption key is expected to be Base64 encoded.

For example:

```text
ENCRYPTION_KEY=<BASE64_ENCODED_KEY>
```

The Spring Boot auto-configuration performs:

```java
byte[] key =
        Base64.getDecoder()
                .decode(properties.getKey());
```

before creating the core encryption configuration.

Conceptually:

```text
Configured Base64 key
        │
        ▼
EncryptionProperties
        │
        ▼
Base64 decoding
        │
        ▼
byte[]
        │
        ▼
EncryptionConfig
```

This keeps the external configuration representation separate from the binary
key representation expected by `lib-encrypt-core`.

## AES-256-GCM Key

`lib-encrypt-core` uses AES-256-GCM.

The configured key must therefore decode to exactly:

```text
32 bytes
```

The core library remains responsible for validating whether the resulting key
is appropriate for the selected encryption configuration.

The Spring integration layer does not implement AES key validation rules.

It only:

```text
reads configuration
        │
        ▼
decodes Base64
        │
        ▼
creates EncryptionConfig
```

The cryptographic validation remains part of the core library.

## Encryption Algorithm

The default Spring Boot auto-configuration uses:

```text
EncryptionAlgorithm.AES_GCM
```

Conceptually:

```java
EncryptionConfig config =
        new EncryptionConfig(
                key,
                EncryptionAlgorithm.AES_GCM
        );
```

The resulting configuration is supplied to:

```text
AesGcmEncryptionService
```

The Spring integration module therefore does not implement the AES-GCM
algorithm.

It delegates all cryptographic behavior to `lib-encrypt-core`.

## Spring Configuration

Before Spring Boot auto-configuration is applied, the encryption service can
be configured explicitly through Spring.

Conceptually:

```java
@Configuration
public class EncryptionSpringConfig {

    @Bean
    public EncryptionService encryptionService(
            @Value("${security.encryption.key}")
            String encodedKey
    ) {
        byte[] key =
                Base64.getDecoder()
                        .decode(encodedKey);

        EncryptionConfig config =
                new EncryptionConfig(
                        key,
                        EncryptionAlgorithm.AES_GCM
                );

        return new AesGcmEncryptionService(config);
    }
}
```

This configuration creates the default `EncryptionService`.

Conceptually:

```text
Spring Container
      │
      ▼
EncryptionSpringConfig
      │
      ▼
EncryptionConfig
      │
      ▼
AesGcmEncryptionService
      │
      ▼
EncryptionService
```

This explicit configuration approach is still valid.

Spring Boot auto-configuration simply removes the need for every consuming
application to repeat it.

## Spring Boot Auto-Configuration

Starting with version `1.1.0`, `lib-encrypt-spring-core` supports Spring Boot
auto-configuration.

The module provides:

```text
EncryptionAutoConfiguration
```

The auto-configuration creates the default `EncryptionService` when:

```text
lib-encrypt-core is available
```

and:

```text
the application has not already registered another EncryptionService
```

Conceptually:

```java
@AutoConfiguration
@ConditionalOnClass(EncryptionService.class)
@EnableConfigurationProperties(EncryptionProperties.class)
public class EncryptionAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public EncryptionService encryptionService(
            EncryptionProperties properties
    ) {
        byte[] key =
                Base64.getDecoder()
                        .decode(properties.getKey());

        EncryptionConfig config =
                new EncryptionConfig(
                        key,
                        EncryptionAlgorithm.AES_GCM
                );

        return new AesGcmEncryptionService(config);
    }
}
```

The auto-configuration is responsible for framework integration.

The actual encryption implementation remains in `lib-encrypt-core`.

## Auto-Configuration Discovery

Spring Boot discovers the encryption auto-configuration through:

```text
META-INF/spring/
org.springframework.boot.autoconfigure.AutoConfiguration.imports
```

The file contains:

```text
pe.com.galaxy.enterprise.java.libs.lib_encrypt_spring_core.config.EncryptionAutoConfiguration
```

Spring Boot reads this metadata during application startup.

The resulting process is:

```text
Spring Boot starts
        │
        ▼
AutoConfiguration.imports discovered
        │
        ▼
EncryptionAutoConfiguration loaded
        │
        ▼
EncryptionProperties enabled
        │
        ▼
Configuration values bound
        │
        ▼
EncryptionService bean created
```

This removes the need for consuming Spring Boot applications to manually
import or instantiate the encryption configuration.

## Auto-Configuration Flow

The complete startup flow can be represented as:

```text
Application adds lib-encrypt-spring-core
        │
        ▼
Spring Boot starts
        │
        ▼
AutoConfiguration.imports discovered
        │
        ▼
EncryptionAutoConfiguration loaded
        │
        ▼
EncryptionProperties registered
        │
        ▼
galaxy.encryption.key read
        │
        ▼
Base64 key decoded
        │
        ▼
EncryptionConfig created
        │
        ▼
EncryptionAlgorithm.AES_GCM selected
        │
        ▼
AesGcmEncryptionService created
        │
        ▼
EncryptionService bean registered
        │
        ▼
Application injects EncryptionService
```

This allows the consuming application to focus on configuration and business
logic rather than encryption infrastructure.

## Conditional Bean Registration

`EncryptionAutoConfiguration` uses:

```java
@ConditionalOnMissingBean
```

This means the default:

```text
AesGcmEncryptionService
```

is created only when no other `EncryptionService` has already been registered.

For example, a consuming application can provide:

```java
@Bean
public EncryptionService customEncryptionService() {
    return new CustomEncryptionService();
}
```

When that bean exists, the auto-configuration does not create another
`EncryptionService`.

Conceptually:

```text
EncryptionService already exists?
        │
        ├── YES ──> use application implementation
        │
        └── NO  ──> create AesGcmEncryptionService
```

This follows the standard Spring Boot auto-configuration principle of:

```text
configure defaults
        +
allow application override
```

## Dependency Injection

Once the auto-configuration is active, applications can inject:

```text
EncryptionService
```

through constructor injection.

For example:

```java
@Service
public class CustomerEncryptionService {

    private final EncryptionService encryptionService;

    public CustomerEncryptionService(
            EncryptionService encryptionService
    ) {
        this.encryptionService = encryptionService;
    }
}
```

The consuming class does not need to know:

```text
which implementation is used
how the key is loaded
how Base64 is decoded
how EncryptionConfig is created
```

It only depends on:

```text
EncryptionService
```

This reduces coupling between application code and encryption infrastructure.

## Encryption Flow

When encryption is requested:

```text
Application Service
      │
      ▼
EncryptionService
      │
      ▼
AesGcmEncryptionService
      │
      ▼
EncryptionConfig
      │
      ▼
AES/GCM/NoPadding
      │
      ▼
Encrypted Result
```

The Spring integration layer only provides the service.

The cryptographic flow itself belongs to `lib-encrypt-core`.

## JPA Integration

`lib-encrypt-spring-core` also provides JPA integration through:

```text
EncryptedStringConverter
```

The converter allows entity attributes to be encrypted before persistence and
decrypted after retrieval.

Conceptually:

```java
@Convert(converter = EncryptedStringConverter.class)
@Column(name = "first_name")
private String firstName;
```

This keeps entity code declarative.

The entity does not need to contain calls such as:

```java
encryptionService.encrypt(...)
```

or:

```java
encryptionService.decrypt(...)
```

The converter performs the persistence integration.

## JPA AttributeConverter

`EncryptedStringConverter` operates as a Jakarta Persistence
`AttributeConverter`.

Conceptually:

```text
Java Entity Attribute
        │
        ▼
EncryptedStringConverter
        │
        ▼
Database Column
```

During persistence:

```text
Plaintext Java value
        │
        ▼
convertToDatabaseColumn(...)
        │
        ▼
EncryptionService
        │
        ▼
Encrypted database representation
```

During retrieval:

```text
Encrypted database representation
        │
        ▼
convertToEntityAttribute(...)
        │
        ▼
EncryptionService
        │
        ▼
Plaintext Java value
```

The converter does not implement AES-GCM directly.

It delegates encryption responsibilities to the reusable encryption service.

## Persistence Flow

The complete persistence flow can be represented as:

```text
Entity Attribute
      │
      ▼
EncryptedStringConverter
      │
      ▼
EncryptionService
      │
      ▼
AesGcmEncryptionService
      │
      ▼
AES-GCM Encryption
      │
      ▼
Encrypted Database Value
```

The read flow is:

```text
Encrypted Database Value
      │
      ▼
EncryptedStringConverter
      │
      ▼
EncryptionService
      │
      ▼
AES-GCM Decryption
      │
      ▼
Entity Attribute
```

This keeps persistence integration separate from cryptographic implementation.

## JPA Example

An entity can define:

```java
@Entity
public class CustomerEntity {

    @Convert(converter = EncryptedStringConverter.class)
    @Column(name = "first_name")
    private String firstName;

    @Convert(converter = EncryptedStringConverter.class)
    @Column(name = "last_name")
    private String lastName;
}
```

Application code can work with:

```text
Oscar
Guillinta
```

while the database stores the encrypted representation produced by
`lib-encrypt-core`.

## Architectural Position

```text
┌─────────────────────────────────────┐
│        Consuming Application        │
├─────────────────────────────────────┤
│ Spring Boot AutoConfiguration       │
│                                     │
│ EncryptionAutoConfiguration         │
│ EncryptionProperties                │
├─────────────────────────────────────┤
│ Spring / JPA Integration            │
│                                     │
│ EncryptionSpringConfig              │
│ EncryptedStringConverter            │
├─────────────────────────────────────┤
│         EncryptionService           │
├─────────────────────────────────────┤
│        lib-encrypt-core             │
│                                     │
│ EncryptionConfig                    │
│ EncryptionAlgorithm                 │
│ AesGcmEncryptionService             │
└─────────────────────────────────────┘
```

The Spring integration layer should not contain cryptographic algorithms.

Those rules belong to `lib-encrypt-core`.

Spring Boot auto-configuration should also not duplicate encryption logic.

Its responsibility is limited to:

```text
configuration binding
service construction
automatic registration
framework integration
```

## Separation of Responsibilities

The libraries have different responsibilities:

```text
lib-encrypt-core
├── EncryptionService contract
├── EncryptionConfig
├── EncryptionAlgorithm
├── AES-256-GCM implementation
├── IV generation
├── encryption behavior
├── decryption behavior
└── framework-independent cryptography

lib-encrypt-spring-core
├── Spring configuration
├── Spring Boot auto-configuration
├── EncryptionProperties
├── AutoConfiguration.imports metadata
├── EncryptionService bean creation
├── Base64 key decoding
├── JPA converter integration
└── persistence integration
```

This allows `lib-encrypt-core` to remain reusable in applications that do not
use Spring.

It also prevents Spring-specific concerns from being mixed with cryptographic
implementation.

## Spring Configuration vs Auto-Configuration

The module contains two related but different concepts.

### EncryptionSpringConfig

`EncryptionSpringConfig` represents explicit Spring configuration.

Conceptually:

```text
Application
      │
      ▼
EncryptionSpringConfig
      │
      ▼
EncryptionService
```

The configuration must be loaded explicitly by the Spring application.

### EncryptionAutoConfiguration

`EncryptionAutoConfiguration` integrates the same encryption infrastructure
with Spring Boot's automatic discovery mechanism.

Conceptually:

```text
Spring Boot
      │
      ▼
AutoConfiguration.imports
      │
      ▼
EncryptionAutoConfiguration
      │
      ▼
EncryptionService
```

The consuming Spring Boot application therefore does not need to explicitly
create the default encryption service.

## Configuration Binding

`EncryptionProperties` is registered through:

```java
@EnableConfigurationProperties(
        EncryptionProperties.class
)
```

Spring Boot binds external properties such as:

```yaml
galaxy:
  encryption:
    key: ${ENCRYPTION_KEY}
```

into the Java configuration object.

Conceptually:

```text
application.yml
      │
      ▼
Spring Environment
      │
      ▼
@ConfigurationProperties
      │
      ▼
EncryptionProperties
```

This keeps infrastructure configuration outside business code.

## Design Principles

This library follows these principles:

- Keep cryptographic algorithms in `lib-encrypt-core`.
- Keep Spring-specific integration outside the core library.
- Keep Spring Boot auto-configuration inside the Spring integration module.
- Use `AutoConfiguration.imports` for Spring Boot discovery.
- Use `@ConfigurationProperties` for external encryption configuration.
- Avoid storing encryption secrets directly in source code.
- Decode external Base64 key representation before creating core configuration.
- Delegate cryptographic behavior to `EncryptionService`.
- Avoid duplicating AES-GCM logic in Spring classes.
- Keep JPA converters focused on persistence integration.
- Use constructor injection in consuming applications.
- Use `@ConditionalOnMissingBean` to allow application customization.
- Keep auto-configuration focused on infrastructure rather than business logic.
- Preserve framework independence in `lib-encrypt-core`.

## Encryption and Masking

Encryption and masking address different concerns.

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

`lib-encrypt-spring-core` addresses the encryption and persistence portion of
that lifecycle.

`lib-mask-spring-core` can address presentation masking.

Conceptually:

```text
Database
   │
   │ encrypted data
   ▼
lib-encrypt-spring-core
   │
   │ decrypted application value
   ▼
Application
   │
   ▼
lib-mask-spring-core
   │
   │ masked representation
   ▼
API Consumer
```

The two libraries therefore complement rather than replace each other.

## Requirements

- Java 25
- Spring Framework 7+
- Spring Boot 4.x for auto-configuration
- Jakarta Persistence 3+
- `lib-encrypt-core` 1.0.0
- Maven 3.9+ recommended

## Maven Coordinates

```xml
<dependency>
    <groupId>pe.com.galaxy.enterprise.java.libs</groupId>
    <artifactId>lib-encrypt-spring-core</artifactId>
    <version>1.1.0</version>
</dependency>
```

`lib-encrypt-core` is declared as a dependency of this module and can therefore
be supplied transitively to consuming applications.

When `lib-enterprise-bom` manages the library version, consumers can omit the
explicit version.

For example:

```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>pe.com.galaxy.enterprise.java.libs</groupId>
            <artifactId>lib-enterprise-bom</artifactId>
            <version>...</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

Then:

```xml
<dependency>
    <groupId>pe.com.galaxy.enterprise.java.libs</groupId>
    <artifactId>lib-encrypt-spring-core</artifactId>
</dependency>
```

## Core Library Dependency

The module depends on:

```xml
<dependency>
    <groupId>pe.com.galaxy.enterprise.java.libs</groupId>
    <artifactId>lib-encrypt-core</artifactId>
    <version>1.0.0</version>
</dependency>
```

A recommended compatible combination is:

```text
lib-encrypt-core         1.0.0
lib-encrypt-spring-core  1.1.0
```

The two modules do not need to have identical versions because they evolve
independently.

`lib-encrypt-spring-core:1.1.0` adds Spring Boot functionality while continuing
to use the cryptographic behavior provided by `lib-encrypt-core:1.0.0`.

## Spring Boot Auto-Configuration Dependency

Version `1.1.0` uses Spring Boot's auto-configuration infrastructure.

The module therefore includes:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-autoconfigure</artifactId>
    <version>${spring.boot.version}</version>
</dependency>
```

This dependency provides infrastructure such as:

```text
@AutoConfiguration
@ConditionalOnClass
@ConditionalOnMissingBean
@EnableConfigurationProperties
@ConfigurationProperties
```

The encryption algorithms remain independent from Spring Boot because they
continue to reside in `lib-encrypt-core`.

## Spring Boot Usage

A consuming Spring Boot application adds:

```xml
<dependency>
    <groupId>pe.com.galaxy.enterprise.java.libs</groupId>
    <artifactId>lib-encrypt-spring-core</artifactId>
    <version>1.1.0</version>
</dependency>
```

Then configures:

```yaml
galaxy:
  encryption:
    key: ${ENCRYPTION_KEY}
```

The application's main class remains standard:

```java
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(
                Application.class,
                args
        );
    }
}
```

There is no need to manually add:

```java
@Bean
public EncryptionService encryptionService() {
    ...
}
```

Spring Boot discovers the library's auto-configuration automatically.

## Environment Variable Example

A consuming application can provide:

```text
ENCRYPTION_KEY=<BASE64_ENCODED_32_BYTE_KEY>
```

and reference it from configuration:

```yaml
galaxy:
  encryption:
    key: ${ENCRYPTION_KEY}
```

This prevents the key from being hard-coded inside:

```text
Java source code
application.yml committed to Git
application.properties committed to Git
```

The way secrets are provided to the runtime remains a responsibility of the
consuming application or deployment environment.

## Testing

Unit and integration tests should cover:

- Spring encryption configuration
- Spring Boot auto-configuration
- `EncryptionAutoConfiguration` context loading
- auto-configuration discovery metadata
- `EncryptionProperties` registration
- `EncryptionProperties` binding
- Base64 key decoding
- default `EncryptionService` creation
- creation of `AesGcmEncryptionService`
- valid AES-256 key configuration
- invalid Base64 key configuration
- invalid AES key length
- custom `EncryptionService` registration
- `@ConditionalOnMissingBean` behavior
- JPA converter encryption
- JPA converter decryption
- null persistence values
- encrypted value round trips
- invalid encrypted values
- application context startup with valid configuration

Tests for the actual AES-GCM algorithm should remain in `lib-encrypt-core`.

The Spring module should primarily test:

```text
configuration
dependency injection
auto-configuration
properties binding
JPA integration
```

## Auto-Configuration Test

Spring Boot auto-configuration can be tested using:

```text
ApplicationContextRunner
```

For example:

```java
class EncryptionAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner =
            new ApplicationContextRunner()
                    .withConfiguration(
                            AutoConfigurations.of(
                                    EncryptionAutoConfiguration.class
                            )
                    )
                    .withPropertyValues(
                            "galaxy.encryption.key=<VALID_BASE64_AES_256_KEY>"
                    );

    @Test
    void shouldAutoConfigureEncryptionService() {
        contextRunner.run(context -> {

            EncryptionService encryptionService =
                    context.getBean(
                            EncryptionService.class
                    );

            assertNotNull(
                    encryptionService
            );
        });
    }
}
```

This verifies that Spring Boot can build the encryption infrastructure without
manual application configuration.

## Custom Bean Test

The auto-configuration should also be tested to confirm that it backs off when
a custom encryption implementation exists.

Conceptually:

```java
@Test
void shouldBackOffWhenCustomEncryptionServiceExists() {

    contextRunner
            .withBean(
                    EncryptionService.class,
                    CustomEncryptionService::new
            )
            .run(context -> {

                assertEquals(
                        1,
                        context.getBeansOfType(
                                EncryptionService.class
                        ).size()
                );
            });
}
```

This verifies the expected behavior of:

```text
@ConditionalOnMissingBean
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
lib-encrypt-spring-core-1.1.0.jar
lib-encrypt-spring-core-1.1.0-sources.jar
lib-encrypt-spring-core-1.1.0-javadoc.jar
```

The main JAR also contains the Spring Boot auto-configuration metadata:

```text
META-INF/spring/
org.springframework.boot.autoconfigure.AutoConfiguration.imports
```

This metadata is included in the published artifact and allows Spring Boot
consumers to discover the encryption integration automatically.

## Versioning

Current stable version:

```text
1.1.0
```

### Version 1.0.0

Version `1.0.0` provides the initial Spring and JPA integration for
`lib-encrypt-core`.

The initial functionality includes:

```text
Spring integration
EncryptionService configuration
EncryptedStringConverter
JPA persistence integration
```

### Version 1.1.0

Version `1.1.0` adds Spring Boot auto-configuration.

The new functionality includes:

```text
EncryptionProperties
EncryptionAutoConfiguration
AutoConfiguration.imports
Automatic Spring Boot discovery
Automatic EncryptionService creation
Base64 key configuration
Configuration-property binding
Conditional bean registration
Custom EncryptionService override support
```

This is a backward-compatible feature addition and therefore increments the
minor version according to Semantic Versioning.

Existing Spring-based applications can continue to use their previous
configuration mechanisms.

Spring Boot applications can use the new automatic discovery mechanism.

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

## Version Compatibility

A recommended compatible combination is:

```text
lib-encrypt-core         1.0.0
lib-encrypt-spring-core  1.1.0
```

The Spring integration module depends on the core encryption library but
evolves independently from it.

The difference in minor versions does not indicate incompatibility.

It indicates that `lib-encrypt-spring-core` has gained additional
Spring-specific functionality while continuing to use the encryption behavior
provided by `lib-encrypt-core:1.0.0`.

## License

Licensed under the Apache License 2.0.

## Author

Oscar Guillinta