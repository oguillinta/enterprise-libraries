package pe.com.galaxy.enterprise.java.libs.lib_validation_core.validator;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for the {@link UuidValidator}.
 *
 * <p>Verifies canonical UUID representation, invalid formats,
 * and optional-value behavior.</p>
 *
 * @since 0.0.1
 */
public class UuidValidatorTest {

    private final UuidValidator validator = new UuidValidator();

    @Test
    void shouldAcceptValidUuid() {
        String uuid = UUID.randomUUID().toString();

        assertTrue(
                validator.isValid(
                        uuid,
                        null
                )
        );
    }

    @Test
    void shouldAcceptUppercaseUuid() {
        String uuid = UUID.randomUUID().toString().toUpperCase();

        assertTrue(
                validator.isValid(
                        uuid,
                        null
                )
        );
    }

    @Test
    void shouldRejectInvalidUuid() {
        assertFalse(
                validator.isValid(
                        "invalid-uuid",
                        null
                )
        );
    }

    @Test
    void shouldRejectNonCanonicalUuidRepresentation() {
        assertFalse(
                validator.isValid(
                        "1-1-1-1-1",
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
