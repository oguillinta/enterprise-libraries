package pe.com.galaxy.enterprise.java.libs.lib_domain_core.vo;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Money} value object.
 *
 * <p>Verifies the main behaviors defined by {@link Money}, including
 * creation, arithmetic operations, comparisons, and currency consistency
 * validation.</p>
 *
 * @since 0.0.1
 */
public class MoneyTest {

    private static final Currency PEN = Currency.getInstance("PEN");
    private static final Currency USD = Currency.getInstance("USD");

    @Test
    void shouldCreateMoneyWithExpectedAmountAndCurrency() {
        Money money = Money.of(
                new BigDecimal("150.00"),
                PEN
        );

        assertEquals(new BigDecimal("150.00"), money.amount());
        assertEquals(PEN, money.currency());
    }

    @Test
    void shouldAddMoneyWithSameCurrency() {
        Money first = Money.of(new BigDecimal("100.00"), PEN);
        Money second = Money.of(new BigDecimal("50.00"), PEN);

        Money result = first.add(second);

        assertEquals(new BigDecimal("150.00"), result.amount());
        assertEquals(PEN, result.currency());
    }

    @Test
    void shouldSubtractMoneyWithSameCurrency() {
        Money first = Money.of(new BigDecimal("100.00"), PEN);
        Money second = Money.of(new BigDecimal("40.00"), PEN);

        Money result = first.subtract(second);

        assertEquals(new BigDecimal("60.00"), result.amount());
        assertEquals(PEN, result.currency());
    }

    @Test
    void shouldReturnTrueWhenAmountIsLessThanAnotherMoney() {
        Money first = Money.of(new BigDecimal("50.00"), PEN);
        Money second = Money.of(new BigDecimal("100"), PEN);

        assertTrue(first.isLessThan(second));
    }

    @Test
    void shouldRejectAdditionUsingDifferentCurrencies() {
        Money pen = Money.of(new BigDecimal("100.00"), PEN);
        Money usd = Money.of(new BigDecimal("100.00"), USD);

        IllegalArgumentException exception =
                assertThrows(
                        IllegalArgumentException.class,
                        () -> pen.add(usd)
                );

        assertEquals(
                "Money values must use the same currency",
                exception.getMessage()
        );
    }
}