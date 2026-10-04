package pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy;

import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link AccountMaskingStrategyImpl}.
 *
 * <p>Verifies account-number masking, whitespace normalization,
 * short-value masking, and supported mask type.</p>
 *
 * @since 0.0.1
 */
public class AccountMaskingStrategyImplTest {

    private final AccountMaskingStrategyImpl strategy =
            new AccountMaskingStrategyImpl();

    @Test
    void shouldSupportAccountNumberMaskType() {
        assertEquals(
                MaskType.ACCOUNT_NUMBER,
                strategy.supports()
        );
    }

    @Test
    void shouldMaskAccountNumberKeepingLastFourCharacters() {
        assertEquals(
                "******7890",
                strategy.mask("1234567890")
        );
    }

    @Test
    void shouldRemoveSpacesBeforeMasking() {
        assertEquals(
                "******7890",
                strategy.mask("1234 5678 90")
        );
    }

    @Test
    void shouldMaskEntireShortAccountNumber() {
        assertEquals(
                "****",
                strategy.mask("1234")
        );
    }

    @Test
    void shouldReturnNullWhenValueIsNull() {
        assertNull(
                strategy.mask(null)
        );
    }

    @Test
    void shouldReturnBlankValueUnchanged() {
        assertEquals(
                "   ",
                strategy.mask("   ")
        );
    }
}