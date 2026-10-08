# lib-string-spring-wrapper

Framework-independent wrapper library providing an object-oriented API over `lib-string-utils` for dependency injection and application integration.

## Purpose

`lib-string-utils` exposes its functionality through static utility methods:

```java
String normalized =
        StringUtils.normalizeWhitespace(value);