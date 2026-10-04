package pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.strategy;

import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.model.MaskType;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the {@link PhoneMaskingStrategyImpl}.
 *
 * <p>Verifies phone-number masking, short-value behavior,
 * and supported mask type.</p>
 *
 * @since 0.0.1
 */
public class PhoneMaskingStrategyImplTest {

    private final PhoneMaskingStrategyImpl strategy =
            new PhoneMaskingStrategyImpl();

    @Test
    void shouldSupportPhoneMaskType() {
        assertEquals(
                MaskType.PHONE,
                strategy.supports()
        );
    }

    @Test
    void shouldMaskPhoneKeepingLastFourDigits() {
        assertEquals(
                "*****5678",
                strategy.mask("999995678")
        );
    }

    @Test
    void shouldMaskEntireShortPhoneValue() {
        assertEquals(
                "****",
                strategy.mask("1234")
        );
    }
}