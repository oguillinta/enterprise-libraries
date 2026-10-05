package pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskingOptions;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class EmailMaskingStrategyImplTest {

    private EmailMaskingStrategyImpl strategy;

    @BeforeEach
    void setUp() {
        strategy =
                new EmailMaskingStrategyImpl();
    }

    @Test
    void shouldSupportEmailMaskType() {
        assertEquals(
                MaskType.EMAIL,
                strategy.supports()
        );
    }

    @Test
    void shouldMaskEmailUsingDefaultConfiguration() {
        String result =
                strategy.mask(
                        "nasly.gomez@email.com"
                );

        assertEquals(
                "n**********@email.com",
                result
        );
    }

    @Test
    void shouldMaskEmailUsingCustomVisiblePrefix() {
        MaskingOptions options =
                new MaskingOptions(
                        2,
                        0,
                        '*'
                );

        String result =
                strategy.mask(
                        "nasly.gomez@email.com",
                        options
                );

        assertEquals(
                "na*********@email.com",
                result
        );
    }

    @Test
    void shouldMaskEmailUsingCustomPrefixAndSuffix() {
        MaskingOptions options =
                new MaskingOptions(
                        2,
                        2,
                        '*'
                );

        String result =
                strategy.mask(
                        "nasly.gomez@email.com",
                        options
                );

        assertEquals(
                "na*******ez@email.com",
                result
        );
    }

    @Test
    void shouldMaskEmailUsingCustomMaskCharacter() {
        MaskingOptions options =
                new MaskingOptions(
                        2,
                        2,
                        '#'
                );

        String result =
                strategy.mask(
                        "nasly.gomez@email.com",
                        options
                );

        assertEquals(
                "na#######ez@email.com",
                result
        );
    }

    @Test
    void shouldKeepAtLeastOneLocalPartCharacterMasked() {
        MaskingOptions options =
                new MaskingOptions(
                        100,
                        100,
                        '*'
                );

        String result =
                strategy.mask(
                        "nasly@email.com",
                        options
                );

        assertEquals(
                "nasl*@email.com",
                result
        );
    }

    @Test
    void shouldLeaveSingleCharacterLocalPartUnchanged() {
        String result =
                strategy.mask(
                        "a@email.com"
                );

        assertEquals(
                "a@email.com",
                result
        );
    }

    @Test
    void shouldLeaveInvalidEmailWithoutAtSymbolUnchanged() {
        String result =
                strategy.mask(
                        "invalid-email"
                );

        assertEquals(
                "invalid-email",
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