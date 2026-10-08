package pe.com.galaxy.enterprise.java.libs.lib_date_utils;

import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

public class DateUtilsTest {

    private static final Clock CLOCK =
            Clock.fixed(
                    Instant.parse(
                            "2026-10-08T12:00:00Z"
                    ),
                    ZoneOffset.UTC
            );

    @Test
    void shouldDetectExpiredYearMonth() {
        assertTrue(
                DateUtils.isExpired(
                        YearMonth.of(2026, 9),
                        CLOCK
                )
        );
    }

    @Test
    void shouldNotDetectCurrentMonthAsExpired() {
        assertFalse(
                DateUtils.isExpired(
                        YearMonth.of(2026, 10),
                        CLOCK
                )
        );
    }

    @Test
    void shouldCalculateDaysBetweenDates() {
        long result =
                DateUtils.daysBetween(
                        LocalDate.of(2026, 10, 1),
                        LocalDate.of(2026, 10, 8)
                );

        assertEquals(7, result);
    }
}