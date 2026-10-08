# lib-domain-core

Reusable domain primitives for Java applications following
Domain-Driven Design (DDD), Clean Architecture, and Hexagonal Architecture.

This library provides lightweight, framework-independent building blocks
for domain models, including strongly typed identifiers, monetary values,
aggregate roots, domain events, and domain-specific exceptions.

## Features

- Strongly typed domain identifiers backed by UUID
- Immutable monetary value object
- Currency-aware arithmetic and comparisons
- Aggregate root base class
- Domain event registration and retrieval
- Base domain exception abstraction
- Invalid value object exception
- Framework-independent design
- Suitable for DDD-oriented domain models
- Java 25 compatible

## Package Structure

```text
pe.com.galaxy.enterprise.java.libs.lib_domain_core
├── aggregate
│   └── AggregateRoot
├── event
│   └── DomainEvent
├── exception
│   ├── DomainException
│   └── InvalidValueObjectException
└── vo
    ├── DomainId
    ├── DomainIdErrorMessages
    └── Money