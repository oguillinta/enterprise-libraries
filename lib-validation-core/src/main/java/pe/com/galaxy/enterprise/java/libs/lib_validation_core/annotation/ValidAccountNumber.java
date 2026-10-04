package pe.com.galaxy.enterprise.java.libs.lib_validation_core.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import pe.com.galaxy.enterprise.java.libs.lib_validation_core.validator.AccountNumberValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static pe.com.galaxy.enterprise.java.libs.lib_validation_core.constant.ValidationMessages.INVALID_ACCOUNT_NUMBER;

/**
 * Validates the structural format of a bank account number.
 *
 * <p>Bank account number formats may vary between financial institutions.
 * Therefore, this constraint validates a configurable numeric length rather
 * than assuming a single bank-specific format.</p>
 *
 * <p>This constraint validates structure only and does not verify that
 * the account exists or belongs to a particular customer.</p>
 *
 * @since 1.0.0
 */
@Documented
@Constraint(validatedBy = AccountNumberValidator.class)
@Target({
        FIELD,
        METHOD,
        PARAMETER,
        RECORD_COMPONENT,
        ANNOTATION_TYPE
})
@Retention(RUNTIME)

public @interface ValidAccountNumber {
    /**
     * Minimum accepted number of digits.
     *
     * @return minimum account number length
     */
    int minLength() default 6;

    /**
     * Maximum accepted number of digits.
     *
     * @return maximum account number length
     */
    int maxLength() default 20;

    String message() default INVALID_ACCOUNT_NUMBER;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
