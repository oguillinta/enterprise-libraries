package pe.com.galaxy.enterprise.java.libs.lib_date_spring_wrapper;

import java.time.LocalDate;
import java.time.YearMonth;

/**
 * Provides object-oriented date operations backed by lib-date-utils.
 *
 * @since 1.0.0
 */
public interface DateService {

    boolean isPast(LocalDate date);

    boolean isExpired(YearMonth expiration);

    long daysBetween(
            LocalDate from,
            LocalDate to
    );

    LocalDate max(
            LocalDate first,
            LocalDate second
    );
}