package pe.com.galaxy.enterprise.java.libs.lib_validation_core.validator;

import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_validation_core.annotation.ValidDocument;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pe.com.galaxy.enterprise.java.libs.lib_validation_core.model.DocumentType.*;

import pe.com.galaxy.enterprise.java.libs.lib_validation_core.model.DocumentType.*;

import java.lang.reflect.Field;

/**
 * Unit tests for the {@link DocumentValidator}.
 *
 * <p>Verifies validation rules for DNI, RUC, Carné de Extranjería,
 * passport, and optional document values.</p>
 *
 * @since 0.0.1
 */
public class DocumentValidatorTest {

    @Test
    void shouldAcceptValidDni() throws Exception {
        DocumentValidator validator = createValidator("dni");

        assertTrue(
                validator.isValid(
                        "12345678",
                        null
                )
        );
    }

    @Test
    void shouldRejectInvalidDniLength() throws Exception {
        DocumentValidator validator = createValidator("dni");

        assertFalse(
                validator.isValid(
                        "1234567",
                        null
                )
        );
    }

    @Test
    void shouldRejectNonNumericDni() throws Exception {
        DocumentValidator validator = createValidator("dni");

        assertFalse(
                validator.isValid(
                        "1234ABCD",
                        null
                )
        );
    }

    @Test
    void shouldAcceptValidRuc() throws Exception {
        DocumentValidator validator = createValidator("ruc");

        assertTrue(
                validator.isValid(
                        "20123456786",
                        null
                )
        );
    }

    @Test
    void shouldRejectRucWithInvalidCheckDigit() throws Exception{
        DocumentValidator validator = createValidator("ruc");

        assertFalse(
                validator.isValid(
                        "20123456789",
                        null
                )
        );
    }

    @Test
    void shouldAcceptValidCe() throws Exception {
        DocumentValidator validator = createValidator("ce");

        assertTrue(
                validator.isValid(
                        "ABC12345",
                        null
                )
        );
    }

    @Test
    void shouldRejectInvalidCe() throws Exception {
        DocumentValidator validator = createValidator("ce");

        assertFalse(
                validator.isValid(
                        "ABC",
                        null
                )
        );
    }

    @Test
    void shouldAcceptValidPassport() throws Exception {
        DocumentValidator validator = createValidator("passport");

        assertTrue(
                validator.isValid(
                        "ABC123456",
                        null
                )
        );
    }

    @Test
    void shouldRejectInvalidPassport() throws Exception {
        DocumentValidator validator = createValidator("passport");

        assertFalse(
                validator.isValid(
                        "AB12",
                        null
                )
        );
    }

    @Test
    void shouldAcceptNullDocument() throws Exception {
        DocumentValidator validator =
                createValidator("dni");

        assertTrue(
                validator.isValid(
                        null,
                        null
                )
        );
    }

    @Test
    void shouldAcceptBlankDocument() throws Exception {
        DocumentValidator validator =
                createValidator("dni");

        assertTrue(
                validator.isValid(
                        "",
                        null
                )
        );
    }

    private static DocumentValidator createValidator(
            String fieldName
    ) throws Exception {

        Field field =
                TestFixture.class.getDeclaredField(
                        fieldName
                );

        ValidDocument annotation =
                field.getAnnotation(
                        ValidDocument.class
                );

        DocumentValidator validator =
                new DocumentValidator();

        validator.initialize(annotation);

        return validator;
    }

    private static final class TestFixture {

        @ValidDocument(
                type = DNI
        )
        private String dni;

        @ValidDocument(
                type = RUC
        )
        private String ruc;

        @ValidDocument(
                type = CE
        )
        private String ce;

        @ValidDocument(
                type = PASSPORT
        )
        private String passport;
    }
}
