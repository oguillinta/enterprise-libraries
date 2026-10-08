package pe.com.galaxy.enterprise.java.libs.lib_date_utils;

import java.time.Clock;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Provides common operations for working with dates.
 *
 * @since 1.0.0
 */
public final class DateUtils {

    private DateUtils() {
    }

    /**
     * Determines whether a date occurs before today.
     *
     * @param date date to evaluate
     * @param clock clock used to obtain the current date
     * @return {@code true} when the date is in the past
     */
    public static boolean isPast(
            LocalDate date,
            Clock clock
    ) {
        Objects.requireNonNull(date, "date must not be null");
        Objects.requireNonNull(clock, "clock must not be null");

        return date.isBefore(LocalDate.now(clock));
    }

    /**
     * Determines whether a year-month has already expired.
     *
     * @param expiration expiration year and month
     * @param clock clock used to obtain the current date
     * @return {@code true} when the year-month has expired
     */
    public static boolean isExpired(
            YearMonth expiration,
            Clock clock
    ) {
        Objects.requireNonNull(
                expiration,
                "expiration must not be null"
        );
        Objects.requireNonNull(
                clock,
                "clock must not be null"
        );

        return expiration.isBefore(
                YearMonth.now(clock)
        );
    }

    /**
     * Calculates the number of days between two dates.
     *
     * @param from initial date
     * @param to final date
     * @return number of days between both dates
     */
    public static long daysBetween(
            LocalDate from,
            LocalDate to
    ) {
        Objects.requireNonNull(from, "from must not be null");
        Objects.requireNonNull(to, "to must not be null");

        return ChronoUnit.DAYS.between(from, to);
    }

    /**
     * Returns the latest of two dates.
     *
     * @param first first date
     * @param second second date
     * @return latest date
     */
    public static LocalDate max(
            LocalDate first,
            LocalDate second
    ) {
        Objects.requireNonNull(first, "first must not be null");
        Objects.requireNonNull(second, "second must not be null");

        return first.isAfter(second)
                ? first
                : second;
    }
}