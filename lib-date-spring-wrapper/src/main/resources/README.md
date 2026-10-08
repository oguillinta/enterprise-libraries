# lib-date-spring-wrapper

Spring wrapper library providing an injectable object-oriented API over `lib-date-utils` for date and expiration handling.

## Purpose

`lib-date-utils` exposes reusable date operations through static methods.

Example:

```java
boolean expired =
        DateUtils.isExpired(
                expiration,
                Clock.systemUTC()
        );