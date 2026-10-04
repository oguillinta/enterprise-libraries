package pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.strategy;

import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.model.MaskType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link DocumentMaskingStrategyImpl}.
 *
 * <p>Verifies masking behavior for DNI, RUC, generic documents,
 * short values, and supported mask type.</p>
 *
 * @since 0.0.1
 */
public class DocumentMaskingStrategyImplTest {

    private final DocumentMaskingStrategyImpl strategy =
            new DocumentMaskingStrategyImpl();

    @Test
    void shouldSupportDocumentMaskType() {
        assertEquals(
                MaskType.DOCUMENT,
                strategy.supports()
        );
    }

    @Test
    void shouldMaskDniKeepingLastFourDigits() {
        assertEquals(
                "****5678",
                strategy.mask("12345678")
        );
    }

    @Test
    void shouldMaskRuc() {
        assertEquals(
                "20*****7890",
                strategy.mask("20123457890")
        );
    }

    @Test
    void shouldMaskGenericDocumentKeepingLastFourCharacters() {
        assertEquals(
                "**5678",
                strategy.mask("125678")
        );
    }

    @Test
    void shouldMaskEntireShortGenericDocument() {
        assertEquals(
                "****",
                strategy.mask("1234")
        );
    }

    @Test
    void shouldTrimDocumentBeforeMasking() {
        assertEquals(
                "****5678",
                strategy.mask(" 12345678 ")
        );
    }

    @Test
    void shouldReturnNullWhenValueIsNull() {
        assertNull(
                strategy.mask(null)
        );
    }
}