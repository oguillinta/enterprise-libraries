package pe.com.galaxy.enterprise.java.libs.lib_validation_core.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pe.com.galaxy.enterprise.java.libs.lib_validation_core.annotation.ValidDocument;
import pe.com.galaxy.enterprise.java.libs.lib_validation_core.model.DocumentType;

import java.util.regex.Pattern;

/**
 * Validates identification documents according to their configured type.
 *
 * @since 1.0.0
 */
public final class DocumentValidator
        implements ConstraintValidator<ValidDocument, String> {

    private static final Pattern DNI_PATTERN =
            Pattern.compile("\\d{8}");

    private static final Pattern RUC_PATTERN =
            Pattern.compile("\\d{11}");

    private static final Pattern CE_PATTERN =
            Pattern.compile("[A-Za-z0-9]{8,12}");

    private static final Pattern PASSPORT_PATTERN =
            Pattern.compile("[A-Za-z0-9]{6,12}");

    private static final int[] RUC_WEIGHTS =
            {5, 4, 3, 2, 7, 6, 5, 4, 3, 2};

    private DocumentType documentType;

    @Override
    public void initialize(ValidDocument annotation) {
        this.documentType = annotation.type();
    }

    @Override
    public boolean isValid(
            String value,
            ConstraintValidatorContext context
    ) {
        if (value == null || value.isBlank()) {
            return true;
        }

        return switch (documentType) {
            case DNI -> isValidDni(value);
            case RUC -> isValidRuc(value);
            case CE -> isValidCe(value);
            case PASSPORT -> isValidPassport(value);
        };
    }

    private boolean isValidDni(String value) {
        return DNI_PATTERN.matcher(value).matches();
    }

    private boolean isValidRuc(String value) {
        if (!RUC_PATTERN.matcher(value).matches()) {
            return false;
        }

        int sum = 0;

        for (int index = 0; index < RUC_WEIGHTS.length; index++) {
            int digit = Character.digit(
                    value.charAt(index),
                    10
            );

            sum += digit * RUC_WEIGHTS[index];
        }

        int remainder = sum % 11;
        int expectedCheckDigit = 11 - remainder;

        if (expectedCheckDigit == 10) {
            expectedCheckDigit = 0;
        } else if (expectedCheckDigit == 11) {
            expectedCheckDigit = 1;
        }

        int actualCheckDigit =
                Character.digit(
                        value.charAt(10),
                        10
                );

        return expectedCheckDigit == actualCheckDigit;
    }

    private boolean isValidCe(String value) {
        return CE_PATTERN.matcher(value).matches();
    }

    private boolean isValidPassport(String value) {
        return PASSPORT_PATTERN.matcher(value).matches();
    }
}