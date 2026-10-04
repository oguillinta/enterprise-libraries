package pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy;

import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskType;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the {@link CardMaskingStrategyImpl}.
 *
 * <p>Verifies card-number masking, short-value behavior,
 * and supported mask type.</p>
 *
 * @since 0.0.1
 */
public class CardMaskingStrategyImplTest {

    private final CardMaskingStrategyImpl strategy =
            new CardMaskingStrategyImpl();

    @Test
    void shouldSupportCardNumberMaskType() {
        assertEquals(
                MaskType.CARD_NUMBER,
                strategy.supports()
        );
    }

    @Test
    void shouldMaskCardKeepingFirstAndLastFourCharacters() {
        assertEquals(
                "4111********1111",
                strategy.mask("4111111111111111")
        );
    }

    @Test
    void shouldMaskEntireShortCardValue() {
        assertEquals(
                "*******",
                strategy.mask("1234567")
        );
    }

    @Test
    void shouldPreserveEightCharacterValue() {
        assertEquals(
                "12345678",
                strategy.mask("12345678")
        );
    }
}