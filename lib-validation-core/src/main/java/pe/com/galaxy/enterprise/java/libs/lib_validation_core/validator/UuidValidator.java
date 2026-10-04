package pe.com.galaxy.enterprise.java.libs.lib_validation_core.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pe.com.galaxy.enterprise.java.libs.lib_validation_core.annotation.ValidUuid;

import java.util.UUID;

/**
 * Validates string values against the standard UUID representation.
 *
 * @since 1.0.0
 */
public final class UuidValidator
        implements ConstraintValidator<ValidUuid, String> {

    @Override
    public boolean isValid(
            String value,
            ConstraintValidatorContext context
    ) {
        if (value == null || value.isBlank()) {
            return true;
        }

        try {
            UUID uuid = UUID.fromString(value);

            return uuid.toString()
                    .equalsIgnoreCase(value);

        } catch (IllegalArgumentException exception) {
            return false;
        }
    }
}