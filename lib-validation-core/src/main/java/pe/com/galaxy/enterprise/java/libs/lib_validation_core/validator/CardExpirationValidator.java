package pe.com.galaxy.enterprise.java.libs.lib_validation_core.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pe.com.galaxy.enterprise.java.libs.lib_validation_core.annotation.ValidCardExpiration;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class CardExpirationValidator implements ConstraintValidator<ValidCardExpiration, CharSequence> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM");

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null || value.toString().isBlank()) {
            return true;
        }

        try {
            YearMonth expiration =
                    YearMonth.parse(
                            value.toString(),
                            FORMATTER
                    );

            return !expiration.isBefore(
                    YearMonth.now()
            );

        } catch (DateTimeParseException ex) {
            return false;
        }
    }
}
