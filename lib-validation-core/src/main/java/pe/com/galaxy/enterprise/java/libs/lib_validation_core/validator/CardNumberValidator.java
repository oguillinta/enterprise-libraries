package pe.com.galaxy.enterprise.java.libs.lib_validation_core.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pe.com.galaxy.enterprise.java.libs.lib_validation_core.annotation.ValidCardNumber;

public class CardNumberValidator implements ConstraintValidator<ValidCardNumber, CharSequence> {

    @Override
    public boolean isValid(
            CharSequence value,
            ConstraintValidatorContext context) {

        if (value == null || value.toString().isBlank()) {
            return true;
        }

        String normalized =
                value.toString().replaceAll("[\\s-]", "");

        if (!normalized.matches("\\d{13,19}")) {
            return false;
        }

        return passesLuhn(normalized);
    }

    private boolean passesLuhn(String cardNumber) {

        int sum = 0;
        boolean doubleDigit = false;

        for (int i = cardNumber.length() - 1; i >= 0; i--) {

            int digit = cardNumber.charAt(i) - '0';

            if (doubleDigit) {
                digit *= 2;

                if (digit > 9) {
                    digit -= 9;
                }
            }

            sum += digit;
            doubleDigit = !doubleDigit;
        }

        return sum % 10 == 0;
    }
}
