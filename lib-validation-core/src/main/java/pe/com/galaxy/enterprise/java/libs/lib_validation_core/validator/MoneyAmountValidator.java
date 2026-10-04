package pe.com.galaxy.enterprise.java.libs.lib_validation_core.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import pe.com.galaxy.enterprise.java.libs.lib_validation_core.annotation.ValidMoneyAmount;

import java.math.BigDecimal;

public final class MoneyAmountValidator implements ConstraintValidator<ValidMoneyAmount, BigDecimal> {
    private BigDecimal min;
    private BigDecimal max;
    private int scale;

    @Override
    public void initialize(ValidMoneyAmount annotation) {
        this.min = new BigDecimal(annotation.min());
        this.max = new BigDecimal(annotation.max());
        this.scale = annotation.scale();
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.compareTo(min) >= 0
                && value.compareTo(max) <= 0
                && value.scale() <= scale;
    }
}
