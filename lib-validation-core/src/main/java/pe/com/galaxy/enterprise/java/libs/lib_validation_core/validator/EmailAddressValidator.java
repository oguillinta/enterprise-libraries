package pe.com.galaxy.enterprise.java.libs.lib_validation_core.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pe.com.galaxy.enterprise.java.libs.lib_validation_core.annotation.ValidEmailAddress;

import java.util.regex.Pattern;

public final class EmailAddressValidator implements ConstraintValidator<ValidEmailAddress, CharSequence> {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null || value.toString().isBlank()) {
            return true;
        }

        return EMAIL_PATTERN.matcher(
                value.toString().trim()
        ).matches();
    }
}
