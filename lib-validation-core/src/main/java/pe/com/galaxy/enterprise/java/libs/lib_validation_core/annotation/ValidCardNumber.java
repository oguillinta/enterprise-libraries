package pe.com.galaxy.enterprise.java.libs.lib_validation_core.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import pe.com.galaxy.enterprise.java.libs.lib_validation_core.validator.CardNumberValidator;

import java.lang.annotation.*;

import static pe.com.galaxy.enterprise.java.libs.lib_validation_core.constant.ValidationMessages.INVALID_CARD_NUMBER;

@Documented
@Constraint(validatedBy = CardNumberValidator.class)
@Target({
        ElementType.FIELD,
        ElementType.METHOD,
        ElementType.PARAMETER,
        ElementType.RECORD_COMPONENT,
        ElementType.ANNOTATION_TYPE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCardNumber {

    String message() default INVALID_CARD_NUMBER;

    Class<?>[] groups() default {};

    Class<? extends Payload> [] payload() default {};
}
