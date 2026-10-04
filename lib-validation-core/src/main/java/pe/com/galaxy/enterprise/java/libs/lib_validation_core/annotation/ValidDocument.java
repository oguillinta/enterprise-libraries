package pe.com.galaxy.enterprise.java.libs.lib_validation_core.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import pe.com.galaxy.enterprise.java.libs.lib_validation_core.model.DocumentType;
import pe.com.galaxy.enterprise.java.libs.lib_validation_core.validator.DocumentValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.RECORD_COMPONENT;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static pe.com.galaxy.enterprise.java.libs.lib_validation_core.constant.ValidationMessages.INVALID_DOCUMENT;

/**
 * Validates an identification document according to the configured
 * {@link DocumentType}.
 *
 * <p>The constraint validates document format and, where applicable,
 * structural rules such as check digits. It does not verify that a
 * document was actually issued by an authority.</p>
 *
 * @since 1.0.0
 */
@Documented
@Constraint(validatedBy = DocumentValidator.class)
@Target({
        FIELD,
        METHOD,
        PARAMETER,
        RECORD_COMPONENT,
        ANNOTATION_TYPE
})
@Retention(RUNTIME)
public @interface ValidDocument {

    /**
     * Defines the document type whose validation rules must be applied.
     *
     * @return document type
     */
    DocumentType type();

    String message() default INVALID_DOCUMENT;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}