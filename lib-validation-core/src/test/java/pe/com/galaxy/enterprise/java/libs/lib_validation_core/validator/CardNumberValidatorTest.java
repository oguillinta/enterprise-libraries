package pe.com.galaxy.enterprise.java.libs.lib_validation_core.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the {@link CardNumberValidator}.
 *
 * <p>Verifies card-number normalization, length validation,
 * Luhn checksum validation, and optional-value behavior.</p>
 *
 * @since 0.0.1
 */
public class CardNumberValidatorTest {

    private final CardNumberValidator validator = new CardNumberValidator();

    @Test
    void shouldAcceptValidCardNumber() {
        assertTrue(
                validator.isValid(
                        "4111111111111111",
                        null
                )
        );
    }

    @Test
    void shouldAcceptCardNumberWithSpaces() {
        assertTrue(
                validator.isValid(
                        "4111 1111 1111 1111",
                        null
                )
        );
    }

    @Test
    void shouldAcceptCardNumberWithHyphens() {
        assertTrue(
                validator.isValid(
                        "4111-1111-1111-1111",
                        null
                )
        );
    }

    @Test
    void shouldRejectInvalidLuhnChecksum() {
        assertFalse(
                validator.isValid(
                        "4111111111111112",
                        null
                )
        );
    }

    @Test
    void shouldRejectCardNumberBelowMinimumLength() {
        assertFalse(
                validator.isValid(
                        "411111111111",
                        null
                )
        );
    }

    @Test
    void shouldRejectNonNumericCardNumber() {
        assertFalse(
                validator.isValid(
                        "4111-AAAA-1111-1111",
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
                        " ",
                        null
                )
        );
    }
}
