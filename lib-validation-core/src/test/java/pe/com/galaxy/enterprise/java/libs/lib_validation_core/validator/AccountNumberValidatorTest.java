package pe.com.galaxy.enterprise.java.libs.lib_validation_core.validator;

import jakarta.validation.ConstraintDeclarationException;
import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_validation_core.annotation.ValidAccountNumber;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link AccountNumberValidator}.
 *
 * <p>Verifies numeric format validation, configurable length constraints,
 * optional-value behavior, and invalid constraint configuration.</p>
 *
 * @since 0.0.1
 */
public class AccountNumberValidatorTest {

    @Test
    void shouldAcceptValidAccountNumber() throws Exception {
        AccountNumberValidator validator =
                createValidator("validAccount");

        assertTrue(
                validator.isValid(
                        "1234567890",
                        null
                )
        );
    }

    @Test
    void shouldRejectAccountNumberBelowMinimumLength() throws Exception {
        AccountNumberValidator validator =
                createValidator("validAccount");

        assertFalse(
                validator.isValid(
                        "12345",
                        null
                )
        );
    }

    @Test
    void shouldRejectAccountNumberAboveMaximumLength() throws Exception {
        AccountNumberValidator validator =
                createValidator("validAccount");

        assertFalse(
                validator.isValid(
                        "123456789012345678901",
                        null
                )
        );
    }

    @Test
    void shouldRejectAccountNumberContainingNonNumericCharacters()
            throws Exception {

        AccountNumberValidator validator =
                createValidator("validAccount");

        assertFalse(
                validator.isValid(
                        "12345ABC90",
                        null
                )
        );
    }

    @Test
    void shouldAcceptNullValue() throws Exception {
        AccountNumberValidator validator =
                createValidator("validAccount");

        assertTrue(
                validator.isValid(
                        null,
                        null
                )
        );
    }

    @Test
    void shouldAcceptBlankValue() throws Exception {
        AccountNumberValidator validator =
                createValidator("validAccount");

        assertTrue(
                validator.isValid(
                        "   ",
                        null
                )
        );
    }

    @Test
    void shouldRejectInvalidMinimumLengthConfiguration()
            throws Exception {

        ValidAccountNumber annotation =
                annotationFrom("invalidMinimum");

        AccountNumberValidator validator =
                new AccountNumberValidator();

        assertThrows(
                ConstraintDeclarationException.class,
                () -> validator.initialize(annotation)
        );
    }

    @Test
    void shouldRejectMaximumLengthBelowMinimumLength()
            throws Exception {

        ValidAccountNumber annotation =
                annotationFrom("invalidRange");

        AccountNumberValidator validator =
                new AccountNumberValidator();

        assertThrows(
                ConstraintDeclarationException.class,
                () -> validator.initialize(annotation)
        );
    }

    private static AccountNumberValidator createValidator(
            String fieldName
    ) throws Exception {

        AccountNumberValidator validator =
                new AccountNumberValidator();

        validator.initialize(
                annotationFrom(fieldName)
        );

        return validator;
    }

    private static ValidAccountNumber annotationFrom(
            String fieldName
    ) throws Exception {

        Field field =
                TestFixture.class.getDeclaredField(fieldName);

        return field.getAnnotation(
                ValidAccountNumber.class
        );
    }

    private static final class TestFixture {

        @ValidAccountNumber
        private String validAccount;

        @ValidAccountNumber(minLength = 0)
        private String invalidMinimum;

        @ValidAccountNumber(
                minLength = 10,
                maxLength = 5
        )
        private String invalidRange;
    }
}