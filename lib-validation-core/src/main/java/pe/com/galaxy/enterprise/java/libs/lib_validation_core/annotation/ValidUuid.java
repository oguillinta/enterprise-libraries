package pe.com.galaxy.enterprise.java.libs.lib_validation_core.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import pe.com.galaxy.enterprise.java.libs.lib_validation_core.validator.UuidValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static pe.com.galaxy.enterprise.java.libs.lib_validation_core.constant.ValidationMessages.INVALID_UUID;


/**
 * Validates that a string represents a valid UUID.
 *
 * <p>This constraint validates format only. Use {@code @NotBlank}
 * when the value is also required.</p>
 *
 * @since 1.0.0
 */

@Documented
@Constraint(validatedBy = UuidValidator.class)
@Target({
        FIELD,
        METHOD,
        PARAMETER,
        RECORD_COMPONENT,
        ANNOTATION_TYPE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUuid {

    String message() default INVALID_UUID;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
