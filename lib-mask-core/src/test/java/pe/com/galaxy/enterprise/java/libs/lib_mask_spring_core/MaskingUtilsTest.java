package pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core;

import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.model.MaskType;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the {@link MaskingUtils} convenience utility.
 *
 * <p>Verifies that the predefined masking strategies are correctly
 * registered and accessible through the static masking facade.</p>
 *
 * @since 0.0.1
 */
public class MaskingUtilsTest {

    @Test
    void shouldMaskEmail() {
        assertEquals(
                "o****@gmail.com",
                MaskingUtils.mask(
                        "oscar@gmail.com",
                        MaskType.EMAIL
                )
        );
    }

    @Test
    void shouldMaskCardNumber() {
        assertEquals(
                "4111********1111",
                MaskingUtils.mask(
                        "4111111111111111",
                        MaskType.CARD_NUMBER
                )
        );
    }

    @Test
    void shouldMaskPhone() {
        assertEquals(
                "*****5678",
                MaskingUtils.mask(
                        "999995678",
                        MaskType.PHONE
                )
        );
    }

    @Test
    void shouldMaskDocument() {
        assertEquals(
                "****5678",
                MaskingUtils.mask(
                        "12345678",
                        MaskType.DOCUMENT
                )
        );
    }

    @Test
    void shouldMaskAccountNumber() {
        assertEquals(
                "******7890",
                MaskingUtils.mask(
                        "1234567890",
                        MaskType.ACCOUNT_NUMBER
                )
        );
    }
}