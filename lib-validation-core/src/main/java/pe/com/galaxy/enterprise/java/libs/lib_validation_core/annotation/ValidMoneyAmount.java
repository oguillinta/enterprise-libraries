package pe.com.galaxy.enterprise.java.libs.lib_validation_core.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import pe.com.galaxy.enterprise.java.libs.lib_validation_core.validator.MoneyAmountValidator;

import java.lang.annotation.*;

import static pe.com.galaxy.enterprise.java.libs.lib_validation_core.constant.ValidationMessages.INVALID_MONEY_AMOUNT;

@Documented
@Constraint(validatedBy = MoneyAmountValidator.class)
@Target({
        ElementType.FIELD,
        ElementType.METHOD,
        ElementType.PARAMETER,
        ElementType.RECORD_COMPONENT,
        ElementType.ANNOTATION_TYPE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidMoneyAmount {

    String message() default INVALID_MONEY_AMOUNT;

    String min() default "0.01";

    String max() default "99999999999999999.99";

    int scale() default 2;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
