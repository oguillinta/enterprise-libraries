package pe.com.galaxy.enterprise.java.libs.lib_validation_core.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the {@link CurrencyValidator}.
 *
 * <p>Verifies ISO 4217 currency-code validation and
 * optional-value behavior.</p>
 *
 * @since 0.0.1
 */
public class CurrencyValidatorTest {

    private final CurrencyValidator validator = new CurrencyValidator();

    @Test
    void shouldAcceptPenCurrency() {
        assertTrue(
                validator.isValid(
                        "PEN",
                        null
                )
        );
    }

    @Test
    void shouldAcceptUsdCurrency() {
        assertTrue(
                validator.isValid(
                        "USD",
                        null
                )
        );
    }

    @Test
    void shouldAcceptEurCurrency() {
        assertTrue(
                validator.isValid(
                        "EUR",
                        null
                )
        );
    }

    @Test
    void shouldRejectInvalidCurrency() {
        assertFalse(
                validator.isValid(
                        "ABC",
                        null
                )
        );
    }

    @Test
    void shouldRejectLowercaseCurrency() {
        assertFalse(
                validator.isValid(
                        "pen",
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
