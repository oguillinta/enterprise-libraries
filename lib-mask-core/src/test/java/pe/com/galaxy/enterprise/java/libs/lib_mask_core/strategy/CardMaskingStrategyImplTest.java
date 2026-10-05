package pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskingOptions;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CardMaskingStrategyImplTest {

    private CardMaskingStrategyImpl strategy;

    @BeforeEach
    void setUp() {
        strategy =
                new CardMaskingStrategyImpl();
    }

    @Test
    void shouldSupportCardNumberMaskType() {
        assertEquals(
                MaskType.CARD_NUMBER,
                strategy.supports()
        );
    }

    @Test
    void shouldMaskCardUsingDefaultConfiguration() {
        String result =
                strategy.mask(
                        "4556123412345678"
                );

        assertEquals(
                "4556********5678",
                result
        );
    }

    @Test
    void shouldMaskCardUsingCustomVisiblePrefix() {
        MaskingOptions options =
                new MaskingOptions(
                        6,
                        4,
                        '*'
                );

        String result =
                strategy.mask(
                        "4556123412345678",
                        options
                );

        assertEquals(
                "455612******5678",
                result
        );
    }

    @Test
    void shouldMaskCardUsingCustomPrefixAndSuffix() {
        MaskingOptions options =
                new MaskingOptions(
                        2,
                        2,
                        '*'
                );

        String result =
                strategy.mask(
                        "4556123412345678",
                        options
                );

        assertEquals(
                "45************78",
                result
        );
    }

    @Test
    void shouldMaskCardUsingCustomMaskCharacter() {
        MaskingOptions options =
                new MaskingOptions(
                        4,
                        4,
                        '#'
                );

        String result =
                strategy.mask(
                        "4556123412345678",
                        options
                );

        assertEquals(
                "4556########5678",
                result
        );
    }

    @Test
    void shouldKeepAtLeastOneCharacterMasked() {
        MaskingOptions options =
                new MaskingOptions(
                        100,
                        100,
                        '*'
                );

        String result =
                strategy.mask(
                        "1234",
                        options
                );

        assertEquals(
                "123*",
                result
        );
    }

    @Test
    void shouldMaskSingleCharacterValue() {
        String result =
                strategy.mask(
                        "4"
                );

        assertEquals(
                "*",
                result
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