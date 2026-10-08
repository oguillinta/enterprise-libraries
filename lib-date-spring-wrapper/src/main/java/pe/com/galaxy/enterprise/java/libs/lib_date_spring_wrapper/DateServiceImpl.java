package pe.com.galaxy.enterprise.java.libs.lib_date_spring_wrapper;

import pe.com.galaxy.enterprise.java.libs.lib_date_utils.DateUtils;

import java.time.Clock;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Objects;

/**
 * Default Spring-compatible wrapper around {@link DateUtils}.
 *
 * @since 1.0.0
 */
public final class DateServiceImpl
        implements DateService {

    private final Clock clock;

    public DateServiceImpl(Clock clock) {
        this.clock = Objects.requireNonNull(
                clock,
                "clock must not be null"
        );
    }

    @Override
    public boolean isPast(LocalDate date) {
        return DateUtils.isPast(
                date,
                clock
        );
    }

    @Override
    public boolean isExpired(
            YearMonth expiration
    ) {
        return DateUtils.isExpired(
                expiration,
                clock
        );
    }

    @Override
    public long daysBetween(
            LocalDate from,
            LocalDate to
    ) {
        return DateUtils.daysBetween(
                from,
                to
        );
    }

    @Override
    public LocalDate max(
            LocalDate first,
            LocalDate second
    ) {
        return DateUtils.max(
                first,
                second
        );
    }
}