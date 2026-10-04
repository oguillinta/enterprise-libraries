package pe.com.galaxy.enterprise.java.libs.lib_validation_core.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link EmailAddressValidator}.
 *
 * <p>Verifies valid and invalid email-address formats,
 * whitespace handling, and optional-value behavior.</p>
 *
 * @since 0.0.1
 */
public class EmailAddressValidatorTest {

    private final EmailAddressValidator validator =
            new EmailAddressValidator();

    @Test
    void shouldAcceptValidEmailAddress() {
        assertTrue(
                validator.isValid(
                        "oscar@example.com",
                        null
                )
        );
    }

    @Test
    void shouldAcceptEmailWithSupportedCharacters() {
        assertTrue(
                validator.isValid(
                        "oscar.guillinta+test@example.com",
                        null
                )
        );
    }

    @Test
    void shouldAcceptEmailSurroundedByWhitespace() {
        assertTrue(
                validator.isValid(
                        "  oscar@example.com  ",
                        null
                )
        );
    }

    @Test
    void shouldRejectEmailWithoutAtSymbol() {
        assertFalse(
                validator.isValid(
                        "oscarexample.com",
                        null
                )
        );
    }

    @Test
    void shouldRejectEmailWithoutDomainSuffix() {
        assertFalse(
                validator.isValid(
                        "oscar@example",
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