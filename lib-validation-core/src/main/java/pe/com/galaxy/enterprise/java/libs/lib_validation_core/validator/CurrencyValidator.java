package pe.com.galaxy.enterprise.java.libs.lib_validation_core.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pe.com.galaxy.enterprise.java.libs.lib_validation_core.annotation.ValidCurrency;

import java.util.Currency;

/**
 * Validates ISO 4217 currency codes.
 *
 * @since 1.0.0
 */
public final class CurrencyValidator
        implements ConstraintValidator<ValidCurrency, String> {

    @Override
    public boolean isValid(
            String value,
            ConstraintValidatorContext context
    ) {
        if (value == null || value.isBlank()) {
            return true;
        }

        try {
            Currency.getInstance(value);
            return true;

        } catch (IllegalArgumentException exception) {
            return false;
        }
    }
}