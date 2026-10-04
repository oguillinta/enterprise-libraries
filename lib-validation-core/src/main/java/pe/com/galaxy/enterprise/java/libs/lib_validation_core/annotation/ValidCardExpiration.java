package pe.com.galaxy.enterprise.java.libs.lib_validation_core.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import pe.com.galaxy.enterprise.java.libs.lib_validation_core.validator.CardExpirationValidator;

import java.lang.annotation.*;

import static pe.com.galaxy.enterprise.java.libs.lib_validation_core.constant.ValidationMessages.INVALID_CARD_EXPIRATION;

@Documented
@Constraint(validatedBy = CardExpirationValidator.class)
@Target({
        ElementType.FIELD,
        ElementType.METHOD,
        ElementType.PARAMETER,
        ElementType.RECORD_COMPONENT,
        ElementType.ANNOTATION_TYPE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCardExpiration {

    String message() default INVALID_CARD_EXPIRATION;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
