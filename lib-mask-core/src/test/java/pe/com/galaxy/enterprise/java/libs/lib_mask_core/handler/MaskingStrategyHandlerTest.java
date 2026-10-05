package pe.com.galaxy.enterprise.java.libs.lib_mask_core.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.contract.MaskerService;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.exception.MaskingException;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskingOptions;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskType;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy.CardMaskingStrategyImpl;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy.EmailMaskingStrategyImpl;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy.PersonNameMaskingStrategyImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MaskingStrategyHandlerTest {

    private MaskerService maskerService;

    @BeforeEach
    void setUp() {
        maskerService =
                new MaskingStrategyHandler(
                        List.of(
                                new EmailMaskingStrategyImpl(),
                                new CardMaskingStrategyImpl(),
                                new PersonNameMaskingStrategyImpl()
                        )
                );
    }

    @Test
    void shouldUseDefaultStrategyConfiguration() {
        String result =
                maskerService.mask(
                        "Nasly",
                        MaskType.PERSON_NAME
                );

        assertEquals(
                "N****",
                result
        );
    }

    @Test
    void shouldDelegateCustomOptionsToStrategy() {
        MaskingOptions options =
                new MaskingOptions(
                        2,
                        0,
                        '*'
                );

        String result =
                maskerService.mask(
                        "Nasly",
                        MaskType.PERSON_NAME,
                        options
                );

        assertEquals(
                "Na***",
                result
        );
    }

    @Test
    void shouldApplyCustomOptionsToEmailStrategy() {
        MaskingOptions options =
                new MaskingOptions(
                        2,
                        2,
                        '*'
                );

        String result =
                maskerService.mask(
                        "nasly.gomez@email.com",
                        MaskType.EMAIL,
                        options
                );

        assertEquals(
                "na*******ez@email.com",
                result
        );
    }

    @Test
    void shouldApplyCustomOptionsToCardStrategy() {
        MaskingOptions options =
                new MaskingOptions(
                        6,
                        4,
                        '*'
                );

        String result =
                maskerService.mask(
                        "4556123412345678",
                        MaskType.CARD_NUMBER,
                        options
                );

        assertEquals(
                "455612******5678",
                result
        );
    }

    @Test
    void shouldRejectMissingStrategy() {
        MaskerService limitedService =
                new MaskingStrategyHandler(
                        List.of(
                                new EmailMaskingStrategyImpl()
                        )
                );

        assertThrows(
                MaskingException.class,
                () -> limitedService.mask(
                        "4556123412345678",
                        MaskType.CARD_NUMBER
                )
        );
    }

    @Test
    void shouldRejectDuplicateStrategies() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new MaskingStrategyHandler(
                        List.of(
                                new EmailMaskingStrategyImpl(),
                                new EmailMaskingStrategyImpl()
                        )
                )
        );
    }

    @Test
    void shouldRejectNullMaskingOptions() {
        assertThrows(
                NullPointerException.class,
                () -> maskerService.mask(
                        "Nasly",
                        MaskType.PERSON_NAME,
                        null
                )
        );
    }

    @Test
    void shouldReturnNullValueUnchanged() {
        assertNull(
                maskerService.mask(
                        null,
                        MaskType.PERSON_NAME
                )
        );
    }

    @Test
    void shouldReturnBlankValueUnchanged() {
        assertEquals(
                "   ",
                maskerService.mask(
                        "   ",
                        MaskType.PERSON_NAME
                )
        );
    }
}