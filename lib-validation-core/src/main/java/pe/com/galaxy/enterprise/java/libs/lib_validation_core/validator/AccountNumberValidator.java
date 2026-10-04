package pe.com.galaxy.enterprise.java.libs.lib_validation_core.validator;

import jakarta.validation.ConstraintDeclarationException;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pe.com.galaxy.enterprise.java.libs.lib_validation_core.annotation.ValidAccountNumber;

/**
 * Validates bank account numbers according to the configured
 * numeric length constraints.
 *
 * @since 1.0.0
 */
public final class AccountNumberValidator
        implements ConstraintValidator<ValidAccountNumber, String> {

    private int minLength;
    private int maxLength;

    @Override
    public void initialize(
            ValidAccountNumber annotation
    ) {
        this.minLength = annotation.minLength();
        this.maxLength = annotation.maxLength();

        if (minLength < 1) {
            throw new ConstraintDeclarationException(
                    "Minimum account number length must be greater than zero"
            );
        }

        if (maxLength < minLength) {
            throw new ConstraintDeclarationException(
                    "Maximum account number length cannot be lower than minimum length"
            );
        }
    }

    @Override
    public boolean isValid(
            String value,
            ConstraintValidatorContext context
    ) {
        if (value == null || value.isBlank()) {
            return true;
        }

        if (value.length() < minLength ||
                value.length() > maxLength) {
            return false;
        }

        return value.chars()
                .allMatch(character ->
                        character >= '0' &&
                                character <= '9'
                );
    }
}