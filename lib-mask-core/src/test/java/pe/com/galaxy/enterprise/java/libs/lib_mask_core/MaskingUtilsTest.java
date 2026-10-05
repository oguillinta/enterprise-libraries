package pe.com.galaxy.enterprise.java.libs.lib_mask_core;

import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskingOptions;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskType;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MaskingUtilsTest {

    @Test
    void shouldMaskPersonNameUsingDefaultConfiguration() {
        String result =
                MaskingUtils.mask(
                        "Nasly",
                        MaskType.PERSON_NAME
                );

        assertEquals(
                "N****",
                result
        );
    }

    @Test
    void shouldMaskPersonNameUsingCustomConfiguration() {
        String result =
                MaskingUtils.mask(
                        "Nasly",
                        MaskType.PERSON_NAME,
                        new MaskingOptions(
                                2,
                                0,
                                '*'
                        )
                );

        assertEquals(
                "Na***",
                result
        );
    }

    @Test
    void shouldMaskEmailUsingCustomConfiguration() {
        String result =
                MaskingUtils.mask(
                        "nasly.gomez@email.com",
                        MaskType.EMAIL,
                        new MaskingOptions(
                                2,
                                2,
                                '*'
                        )
                );

        assertEquals(
                "na*******ez@email.com",
                result
        );
    }

    @Test
    void shouldMaskCardUsingCustomConfiguration() {
        String result =
                MaskingUtils.mask(
                        "4556123412345678",
                        MaskType.CARD_NUMBER,
                        new MaskingOptions(
                                6,
                                4,
                                '*'
                        )
                );

        assertEquals(
                "455612******5678",
                result
        );
    }
}