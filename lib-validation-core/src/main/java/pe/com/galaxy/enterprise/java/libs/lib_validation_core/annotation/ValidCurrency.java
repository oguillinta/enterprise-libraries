package pe.com.galaxy.enterprise.java.libs.lib_validation_core.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import pe.com.galaxy.enterprise.java.libs.lib_validation_core.validator.CurrencyValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static pe.com.galaxy.enterprise.java.libs.lib_validation_core.constant.ValidationMessages.INVALID_CURRENCY;

/**
 * Validates that a string represents a valid ISO 4217 currency code.
 *
 * <p>Examples include {@code PEN}, {@code USD}, and {@code EUR}.</p>
 *
 * @since 1.0.0
 */
@Documented
@Constraint(validatedBy = CurrencyValidator.class)
@Target({
        FIELD,
        METHOD,
        PARAMETER,
        RECORD_COMPONENT,
        ANNOTATION_TYPE
})
@Retention(RUNTIME)
public @interface ValidCurrency {

    String message() default INVALID_CURRENCY;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}