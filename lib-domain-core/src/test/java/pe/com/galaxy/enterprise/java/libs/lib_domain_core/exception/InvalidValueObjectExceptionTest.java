package pe.com.galaxy.enterprise.java.libs.lib_domain_core.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the {@link InvalidValueObjectException}.
 *
 * <p>Verifies that the exception correctly exposes the domain error
 * code and message provided during its creation.</p>
 *
 * @since 0.0.1
 */
public class InvalidValueObjectExceptionTest {

    @Test
    void shouldExposeExpectedCodeAndMessage() {

        InvalidValueObjectException exception =
                new InvalidValueObjectException(
                        "INVALID_VALUE",
                        "Value is invalid"
                );

        assertEquals("INVALID_VALUE", exception.code());
        assertEquals("Value is invalid", exception.getMessage());
    }
}