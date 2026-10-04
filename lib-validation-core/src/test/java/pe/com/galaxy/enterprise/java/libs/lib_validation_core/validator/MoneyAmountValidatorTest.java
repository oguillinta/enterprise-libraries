package pe.com.galaxy.enterprise.java.libs.lib_validation_core.validator;

import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_validation_core.annotation.ValidMoneyAmount;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link MoneyAmountValidator}.
 *
 * <p>Verifies minimum and maximum monetary limits,
 * decimal scale restrictions, and optional-value behavior.</p>
 *
 * @since 0.0.1
 */
public class MoneyAmountValidatorTest {

    @Test
    void shouldAcceptValidMoneyAmount() throws Exception {
        MoneyAmountValidator validator =
                createValidator();

        assertTrue(
                validator.isValid(
                        new BigDecimal("150.50"),
                        null
                )
        );
    }

    @Test
    void shouldAcceptMinimumMoneyAmount() throws Exception {
        MoneyAmountValidator validator =
                createValidator();

        assertTrue(
                validator.isValid(
                        new BigDecimal("0.01"),
                        null
                )
        );
    }

    @Test
    void shouldAcceptMaximumMoneyAmount() throws Exception {
        MoneyAmountValidator validator =
                createValidator();

        assertTrue(
                validator.isValid(
                        new BigDecimal(
                                "99999999999999999.99"
                        ),
                        null
                )
        );
    }

    @Test
    void shouldRejectAmountBelowMinimum() throws Exception {
        MoneyAmountValidator validator =
                createValidator();

        assertFalse(
                validator.isValid(
                        BigDecimal.ZERO,
                        null
                )
        );
    }

    @Test
    void shouldRejectAmountAboveMaximum() throws Exception {
        MoneyAmountValidator validator =
                createValidator();

        assertFalse(
                validator.isValid(
                        new BigDecimal(
                                "100000000000000000.00"
                        ),
                        null
                )
        );
    }

    @Test
    void shouldRejectAmountWithTooManyDecimalPlaces()
            throws Exception {

        MoneyAmountValidator validator =
                createValidator();

        assertFalse(
                validator.isValid(
                        new BigDecimal("100.123"),
                        null
                )
        );
    }

    @Test
    void shouldAcceptNullValue() throws Exception {
        MoneyAmountValidator validator =
                createValidator();

        assertTrue(
                validator.isValid(
                        null,
                        null
                )
        );
    }

    private static MoneyAmountValidator createValidator()
            throws Exception {

        Field field =
                TestFixture.class.getDeclaredField(
                        "amount"
                );

        ValidMoneyAmount annotation =
                field.getAnnotation(
                        ValidMoneyAmount.class
                );

        MoneyAmountValidator validator =
                new MoneyAmountValidator();

        validator.initialize(annotation);

        return validator;
    }

    private static final class TestFixture {

        @ValidMoneyAmount
        private BigDecimal amount;
    }
}