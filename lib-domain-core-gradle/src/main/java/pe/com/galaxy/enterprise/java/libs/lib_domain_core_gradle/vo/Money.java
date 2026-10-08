package pe.com.galaxy.enterprise.java.libs.lib_domain_core_gradle.vo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;
import java.util.Objects;

/**
 * Represents an immutable monetary value composed of an amount and currency.
 *
 * <p>The amount is normalized according to the default number of fraction
 * digits defined by the associated {@link Currency}. Arithmetic and
 * comparison operations require both monetary values to use the same
 * currency.</p>
 *
 * @param amount monetary amount
 * @param currency currency associated with the amount
 * @since 0.0.1
 */
public record Money(
        BigDecimal amount,
        Currency currency
) {

    private static final String INVALID_CURRENCY_MESSAGE =
            "Money values must use the same currency";

    /**
     * Creates a monetary value.
     *
     * @throws NullPointerException if {@code amount} or {@code currency} is null
     * @throws ArithmeticException if the amount contains more fractional digits
     *         than supported by the currency
     */
    public Money {
        Objects.requireNonNull(amount);
        Objects.requireNonNull(currency);

        amount = amount.setScale(
                currency.getDefaultFractionDigits(),
                RoundingMode.UNNECESSARY
        );
    }

    /**
     * Creates a monetary value using the specified amount and currency.
     *
     * @param amount monetary amount
     * @param currency currency of the monetary value
     * @return a new monetary value
     */
    public static Money of(
            BigDecimal amount,
            Currency currency
    ) {
        return new Money(amount, currency);
    }

    /**
     * Creates a zero monetary value for the specified currency.
     *
     * @param currency currency of the monetary value
     * @return zero-valued money in the specified currency
     */
    public static Money zero(Currency currency) {
        return new Money(BigDecimal.ZERO, currency);
    }

    /**
     * Adds another monetary value to this value.
     *
     * @param other monetary value to add
     * @return the resulting monetary value
     * @throws IllegalArgumentException if both values use different currencies
     */
    public Money add(Money other) {
        requireSameCurrency(other);
        return new Money(amount.add(other.amount), currency);
    }

    /**
     * Subtracts another monetary value from this value.
     *
     * @param other monetary value to subtract
     * @return the resulting monetary value
     * @throws IllegalArgumentException if both values use different currencies
     */
    public Money subtract(Money other) {
        requireSameCurrency(other);
        return new Money(amount.subtract(other.amount), currency);
    }

    /**
     * Determines whether this value is lower than another monetary value.
     *
     * @param other monetary value to compare against
     * @return {@code true} if this value is lower; otherwise {@code false}
     * @throws IllegalArgumentException if both values use different currencies
     */
    public boolean isLessThan(Money other) {
        requireSameCurrency(other);
        return amount.compareTo(other.amount) < 0;
    }

    /**
     * Determines whether this value is greater than another monetary value.
     *
     * @param other monetary value to compare against
     * @return {@code true} if this value is greater; otherwise {@code false}
     * @throws IllegalArgumentException if both values use different currencies
     */
    public boolean isGreaterThan(Money other) {
        requireSameCurrency(other);
        return amount.compareTo(other.amount) > 0;
    }

    /**
     * Determines whether the monetary amount is positive.
     *
     * @return {@code true} if the amount is greater than zero
     */
    public boolean isPositive() {
        return amount.signum() > 0;
    }

    /**
     * Determines whether the monetary amount is negative.
     *
     * @return {@code true} if the amount is lower than zero
     */
    public boolean isNegative() {
        return amount.signum() < 0;
    }

    /**
     * Determines whether the monetary amount is zero.
     *
     * @return {@code true} if the amount is zero
     */
    public boolean isZero() {
        return amount.signum() == 0;
    }

    private void requireSameCurrency(Money other) {
        Objects.requireNonNull(other);

        if (!currency.equals(other.currency)) {
            throw new IllegalArgumentException(
                    INVALID_CURRENCY_MESSAGE
            );
        }
    }
}