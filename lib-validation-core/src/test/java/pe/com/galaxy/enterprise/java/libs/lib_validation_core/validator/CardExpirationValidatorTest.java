package pe.com.galaxy.enterprise.java.libs.lib_validation_core.validator;

import org.junit.jupiter.api.Test;

import java.time.YearMonth;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the {@link CardExpirationValidator}.
 *
 * <p>Verifies expiration-date format, current and future dates,
 * expired dates, and optional-value behavior.</p>
 *
 * @since 0.0.1
 */
public class CardExpirationValidatorTest {

    private final CardExpirationValidator validator = new CardExpirationValidator();

    @Test
    void shouldAcceptCurrentMonth() {
         String value = YearMonth.now().toString();

         assertTrue(
                 validator.isValid(
                         value,
                         null
                 )
         );
    }

    @Test
    void shouldAcceptFutureExpiration() {
        String value = YearMonth.now().plusYears(2).toString();

        assertTrue(
                validator.isValid(
                        value,
                        null)
        );
    }

    @Test
    void shouldRejectExpiredCard() {
        String value = YearMonth.now().minusMonths(1).toString();

        assertFalse(
                validator.isValid(
                        value,
                        null
                )
        );
    }

    @Test
    void shouldRejectInvalidExpirationFormat() {
        assertFalse(
                validator.isValid(
                        "12/2030",
                        null
                )
        );
    }

    @Test
    void shouldRejectInvalidMonth() {
        assertFalse(
                validator.isValid(
                        "2030-13",
                        null
                )
        );
    }

    @Test
    void shouldAcceptNullValue() {
        assertTrue(
                validator.isValid(
                        null,
                        null
                )
        );
    }

    @Test
    void shouldAcceptBlankValue() {
        assertTrue(
                validator.isValid(
                        "",
                        null
                )
        );
    }
}
