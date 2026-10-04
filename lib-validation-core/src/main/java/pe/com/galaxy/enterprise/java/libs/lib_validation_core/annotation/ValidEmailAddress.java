package pe.com.galaxy.enterprise.java.libs.lib_validation_core.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import pe.com.galaxy.enterprise.java.libs.lib_validation_core.validator.EmailAddressValidator;

import java.lang.annotation.*;

import static pe.com.galaxy.enterprise.java.libs.lib_validation_core.constant.ValidationMessages.INVALID_EMAIL_ADDRESS;

@Documented
@Constraint(validatedBy = EmailAddressValidator.class)
@Target({
        ElementType.FIELD,
        ElementType.METHOD,
        ElementType.RECORD_COMPONENT,
        ElementType.ANNOTATION_TYPE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEmailAddress {

    String message() default INVALID_EMAIL_ADDRESS;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
