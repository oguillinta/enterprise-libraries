# lib-mask-spring-core

Spring, Spring Boot, and Jackson integration for `lib-mask-core`.

This library provides Spring configuration, Spring Boot auto-configuration, and Jackson serialization support
for applying reusable data-masking strategies exposed by `lib-mask-core`.

It enables declarative masking of sensitive fields while keeping the actual
masking algorithms isolated in the core library.

Starting with version `1.3.0`, the library can be discovered automatically by
Spring Boot without requiring consumers to explicitly import the masking
configuration.

## Features

- Spring integration for `lib-mask-core`
- Spring Boot auto-configuration
- Automatic `MaskerService` configuration
- Automatic discovery through `AutoConfiguration.imports`
- No explicit `@Import` required in Spring Boot applications
- Jackson serialization integration
- Declarative field masking through `@Masked`
- Mask type selection through `MaskType`
- Configurable visible prefix and suffix
- Configurable masking character
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
│   ├── MaskingSpringConfig
│   └── MaskingAutoConfiguration
│
└── jackson
    ├── MaskingValueSerializer
    └── MaskingValueSerializerModifier
```

The Spring Boot auto-configuration metadata is located under:

```text
src/main/resources
└── META-INF
    └── spring
        └── org.springframework.boot.autoconfigure.AutoConfiguration.imports
```

## Core Dependency

`lib-mask-spring-core` builds on top of:

```text
lib-mask-core
```

The core library contains the masking contracts, strategies, handler, models,
and masking rules.

This module contains only the Spring, Spring Boot, and Jackson integration
required to apply those rules automatically in Spring-based applications.

Conceptually:

```text
lib-mask-core
      │
      │ masking contracts and strategies
      ▼
lib-mask-spring-core
      │
      │ Spring + Spring Boot + Jackson integration
      ▼
Consuming Application
```

This separation allows the masking algorithms to remain independent from
Spring and Jackson.

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

Starting with version `1.2.0`, the annotation can also provide optional masking
configuration such as visible prefix, visible suffix, and masking character.

For example:

```java
@Masked(
        value = MaskType.CARD_NUMBER,
        visiblePrefix = 4,
        visibleSuffix = 4,
        maskCharacter = '*'
)
private String cardNumber;
```

The actual masking behavior remains implemented by `lib-mask-core`.

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

## Configurable Masking

Starting with version `1.2.0`, `@Masked` supports optional customization of
masking behavior.

The configurable values include:

```text
visiblePrefix
visibleSuffix
maskCharacter
```

When these values are not explicitly configured, the default behavior defined
by the corresponding masking strategy is used.

Conceptually:

```java
@Masked(
        value = MaskType.EMAIL,
        visiblePrefix = 2,
        maskCharacter = '#'
)
private String email;
```

The Spring integration converts the annotation metadata into
`MaskingOptions` and delegates the masking operation to `MaskerService`.

The actual masking rules remain part of `lib-mask-core`.

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

`MaskingSpringConfig` remains responsible for the actual Spring bean
definitions.

Spring Boot auto-configuration does not duplicate those bean definitions.
Instead, it automatically imports this existing configuration.

## Spring Boot Auto-Configuration

Starting with version `1.3.0`, `lib-mask-spring-core` supports Spring Boot
auto-configuration.

The module provides:

```text
MaskingAutoConfiguration
```

This class is registered as a Spring Boot auto-configuration and automatically
loads the existing `MaskingSpringConfig` when the masking infrastructure is
available on the classpath.

Conceptually:

```text
Spring Boot Application
        │
        ▼
AutoConfiguration.imports
        │
        ▼
MaskingAutoConfiguration
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
        ▼
Jackson masking integration
```

The auto-configuration class can be defined conceptually as:

```java
@AutoConfiguration
@ConditionalOnClass(MaskerService.class)
@Import(MaskingSpringConfig.class)
public class MaskingAutoConfiguration {
}
```

The class intentionally contains very little logic.

The existing `MaskingSpringConfig` remains responsible for creating the
masking infrastructure.

`MaskingAutoConfiguration` is responsible only for allowing Spring Boot to
discover and activate that configuration automatically.

## Auto-Configuration Discovery

Spring Boot discovers the masking auto-configuration through:

```text
META-INF/spring/
org.springframework.boot.autoconfigure.AutoConfiguration.imports
```

The file contains:

```text
pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.configuration.MaskingAutoConfiguration
```

Spring Boot reads this metadata during application startup.

The resulting process is:

```text
Spring Boot starts
        │
        ▼
AutoConfiguration.imports is discovered
        │
        ▼
MaskingAutoConfiguration is loaded
        │
        ▼
MaskingSpringConfig is imported
        │
        ▼
Masking beans are registered
```

This removes the need for consuming Spring Boot applications to manually
import the masking configuration.

Before version `1.3.0`, a consuming application could explicitly load the
configuration:

```java
@Import(MaskingSpringConfig.class)
```

Starting with version `1.3.0`, this explicit import is not required in a
Spring Boot application when the auto-configuration is active.

## Jackson Integration

### MaskingValueSerializer

`MaskingValueSerializer` applies masking during Jackson serialization.

It delegates the actual masking operation to the configured `MaskerService`
instead of implementing masking rules directly.

The serializer obtains the masking type and optional masking configuration
from the `@Masked` annotation.

This preserves the separation between:

```text
serialization concern
```

and:

```text
masking concern
```

The serializer is responsible for applying masking during serialization.

The masking strategies remain responsible for determining how each kind of
sensitive value is protected.

### MaskingValueSerializerModifier

`MaskingValueSerializerModifier` customizes Jackson property serialization.

It identifies properties configured for masking and assigns the appropriate
masking serializer.

This allows masking to occur automatically when an annotated object is
serialized.

The application therefore does not need to manually invoke `MaskerService`
for every sensitive field returned through an API.

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

When custom options are configured, the flow becomes:

```text
@Masked metadata
      │
      ▼
MaskType + masking configuration
      │
      ▼
MaskingOptions
      │
      ▼
MaskerService
      │
      ▼
Specific MaskingStrategy
      │
      ▼
Customized masked value
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
│   Spring Boot AutoConfiguration │
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

Spring Boot auto-configuration should also not duplicate those algorithms or
the existing Spring configuration.

Its responsibility is limited to automatic discovery and activation.

## Separation of Responsibilities

The libraries have different responsibilities:

```text
lib-mask-core
├── masking contracts
├── mask types
├── masking options
├── masking strategies
├── masking handler
└── framework-independent behavior

lib-mask-spring-core
├── Spring configuration
├── Spring Boot auto-configuration
├── AutoConfiguration.imports metadata
├── @Masked integration
├── annotation-to-MaskingOptions conversion
└── Jackson serialization support
```

This allows `lib-mask-core` to remain reusable in applications that do not use
Spring.

It also allows Spring applications to reuse the same masking behavior without
duplicating the algorithms.

## Spring Configuration vs Auto-Configuration

The module contains two related but different responsibilities.

### MaskingSpringConfig

`MaskingSpringConfig` defines the masking infrastructure used by Spring:

```text
MaskerService
MaskingStrategyHandler
Masking strategies
Jackson integration
```

### MaskingAutoConfiguration

`MaskingAutoConfiguration` allows Spring Boot to discover and load that
configuration automatically.

Conceptually:

```text
MaskingSpringConfig
        │
        │ defines beans
        ▼
Masking infrastructure
```

while:

```text
MaskingAutoConfiguration
        │
        │ discovers/imports
        ▼
MaskingSpringConfig
```

This keeps the auto-configuration layer small and prevents duplication of
Spring bean definitions.

## Design Principles

This library follows these principles:

- Keep concrete masking algorithms in `lib-mask-core`.
- Keep Spring-specific integration outside the core library.
- Keep Spring Boot auto-configuration inside the Spring integration module.
- Reuse `MaskingSpringConfig` instead of duplicating bean definitions.
- Use `AutoConfiguration.imports` for Spring Boot discovery.
- Use annotation-driven masking for serialization concerns.
- Delegate masking behavior to `MaskerService`.
- Delegate configurable masking behavior through `MaskingOptions`.
- Avoid duplicating masking rules in Jackson serializers.
- Keep sensitive-data protection reusable across Spring applications.
- Allow new `MaskType` values to be integrated consistently.
- Keep application DTOs free from explicit masking code.
- Keep auto-configuration focused on integration rather than business logic.

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

The Spring Boot auto-configuration capability does not change this
responsibility.

It only simplifies how the masking integration is activated in Spring Boot
applications.

## Requirements

- Java 25
- Spring Framework 7+
- Spring Boot 4.x for auto-configuration
- Jackson
- `lib-mask-core` 1.2.0
- Maven 3.9+ recommended

## Maven Coordinates

```xml
<dependency>
    <groupId>pe.com.galaxy.enterprise.java.libs</groupId>
    <artifactId>lib-mask-spring-core</artifactId>
    <version>1.3.0</version>
</dependency>
```

`lib-mask-core` is declared as a dependency of this module and can therefore
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
    <artifactId>lib-mask-spring-core</artifactId>
</dependency>
```

## Core Library Dependency

The module depends on:

```xml
<dependency>
    <groupId>pe.com.galaxy.enterprise.java.libs</groupId>
    <artifactId>lib-mask-core</artifactId>
    <version>1.2.0</version>
</dependency>
```

Keeping both modules on compatible versions is recommended.

For example:

```text
lib-mask-core         1.2.0
lib-mask-spring-core  1.3.0
```

The two libraries do not need to have identical versions because they evolve
independently.

`lib-mask-core:1.2.0` contains the masking behavior required by
`lib-mask-spring-core:1.3.0`.

## Spring Boot Auto-Configuration Dependency

Version `1.3.0` uses Spring Boot's auto-configuration infrastructure.

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
```

used by `MaskingAutoConfiguration`.

The masking algorithms remain independent from Spring Boot because they
continue to reside in `lib-mask-core`.

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

## Usage with Custom Masking Options

Applications can optionally customize supported masking behavior directly from
the annotation.

For example:

```java
public record PaymentResponse(

        @Masked(
                value = MaskType.CARD_NUMBER,
                visiblePrefix = 4,
                visibleSuffix = 4
        )
        String cardNumber

) {
}
```

A value such as:

```text
4111111111111111
```

can be serialized as:

```text
4111********1111
```

The annotation only supplies configuration.

The actual transformation remains delegated to `lib-mask-core`.

## Spring Boot Usage

Starting with version `1.3.0`, Spring Boot applications do not need to manually
import `MaskingSpringConfig`.

The application can simply include the library dependency:

```xml
<dependency>
    <groupId>pe.com.galaxy.enterprise.java.libs</groupId>
    <artifactId>lib-mask-spring-core</artifactId>
    <version>1.3.0</version>
</dependency>
```

The application's main class remains unchanged:

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

There is no need to add:

```java
@Import(MaskingSpringConfig.class)
```

Spring Boot discovers `MaskingAutoConfiguration` automatically through
`AutoConfiguration.imports`.

## Auto-Configuration Flow

The complete startup flow is:

```text
Application adds lib-mask-spring-core
        │
        ▼
Spring Boot starts
        │
        ▼
AutoConfiguration.imports discovered
        │
        ▼
MaskingAutoConfiguration loaded
        │
        ▼
MaskingSpringConfig imported
        │
        ▼
Default MaskerService created
        │
        ▼
Default MaskingStrategy set registered
        │
        ▼
Jackson masking integration registered
        │
        ▼
@Masked works during serialization
```

This allows the consuming application to focus only on declaring which fields
must be masked.

## Testing

Unit tests should cover:

- Spring masking configuration
- Spring Boot auto-configuration
- `MaskingAutoConfiguration` context loading
- auto-configuration discovery metadata
- default `MaskerService` creation
- registration of all default masking strategies
- `@Masked` metadata handling
- configurable masking annotation metadata
- conversion to `MaskingOptions`
- Jackson serializer selection
- Jackson masking serialization
- email masking during serialization
- card masking during serialization
- document masking during serialization
- account-number masking during serialization
- phone masking during serialization
- personal-name masking during serialization
- custom visible prefix
- custom visible suffix
- custom mask character
- null values
- blank values
- unsupported or invalid masking configuration

Tests for individual masking algorithms should remain in `lib-mask-core`.

The Spring module should primarily test framework integration.

Auto-configuration tests should verify that the Spring Boot application context
can create the masking infrastructure without requiring explicit application
configuration.

For example:

```java
class MaskingAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner =
            new ApplicationContextRunner()
                    .withConfiguration(
                            AutoConfigurations.of(
                                    MaskingAutoConfiguration.class
                            )
                    );

    @Test
    void shouldAutoConfigureMaskerService() {
        contextRunner.run(context -> {
            MaskerService maskerService =
                    context.getBean(
                            MaskerService.class
                    );

            assertNotNull(maskerService);
        });
    }
}
```

This test verifies the integration behavior introduced by version `1.3.0`.

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
lib-mask-spring-core-1.3.0.jar
lib-mask-spring-core-1.3.0-sources.jar
lib-mask-spring-core-1.3.0-javadoc.jar
```

The main JAR also contains the Spring Boot auto-configuration metadata:

```text
META-INF/spring/
org.springframework.boot.autoconfigure.AutoConfiguration.imports
```

This metadata is included in the published artifact and allows Spring Boot
consumers to discover the masking integration automatically.

## Versioning

Current stable version:

```text
1.3.0
```

### Version 1.1.0

Version `1.1.0` adds Spring/Jackson integration support for:

```text
MaskType.PERSON_NAME
```

through the personal-name masking strategy provided by
`lib-mask-core:1.1.0`.

### Version 1.2.0

Version `1.2.0` adds configurable masking support.

The `@Masked` annotation can provide options such as:

```text
visiblePrefix
visibleSuffix
maskCharacter
```

These options are delegated to the configurable masking infrastructure
provided by `lib-mask-core:1.2.0`.

### Version 1.3.0

Version `1.3.0` adds Spring Boot auto-configuration.

The new functionality includes:

```text
MaskingAutoConfiguration
AutoConfiguration.imports
Automatic Spring Boot discovery
Automatic MaskingSpringConfig loading
No explicit @Import required
```

This is a backward-compatible feature addition and therefore increments the
minor version according to Semantic Versioning.

Existing Spring-based applications can continue to use the previous
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
lib-mask-core         1.2.0
lib-mask-spring-core  1.3.0
```

The Spring integration module depends on the core masking library but evolves
independently from it.

The difference in minor versions does not indicate incompatibility.

It indicates that `lib-mask-spring-core` has gained additional
Spring-specific functionality while continuing to use the masking behavior
provided by `lib-mask-core:1.2.0`.

## License

Licensed under the Apache License 2.0.

## Author

Oscar Guillinta