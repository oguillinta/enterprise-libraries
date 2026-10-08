package pe.com.galaxy.enterprise.java.libs.lib_date_spring_wrapper;

import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

public class DateServiceTest {

    private static final Clock CLOCK =
            Clock.fixed(
                    Instant.parse(
                            "2026-10-08T12:00:00Z"
                    ),
                    ZoneOffset.UTC
            );

    private final DateService dateService =
            new DateServiceImpl(
                    CLOCK
            );

    @Test
    void shouldDetectExpiredYearMonth() {
        assertTrue(
                dateService.isExpired(
                        YearMonth.of(2026, 9)
                )
        );
    }

    @Test
    void shouldNotDetectCurrentMonthAsExpired() {
        assertFalse(
                dateService.isExpired(
                        YearMonth.of(2026, 10)
                )
        );
    }

    @Test
    void shouldCalculateDaysBetweenDates() {
        long result =
                dateService.daysBetween(
                        LocalDate.of(2026, 10, 1),
                        LocalDate.of(2026, 10, 8)
                );

        assertEquals(
                7,
                result
        );
    }
}