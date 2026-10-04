package pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.handler;

import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.exception.MaskingException;
import pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.model.MaskType;
import pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.strategy.MaskingStrategy;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link MaskingStrategyHandler}.
 *
 * <p>Verifies strategy registration, strategy resolution, delegation,
 * duplicate detection, and handling of null or blank values.</p>
 *
 * @since 0.0.1
 */
public class MaskingStrategyHandlerTest {

    @Test
    void shouldDelegateMaskingToRegisteredStrategy() {
        MaskingStrategy strategy = new TestMaskingStrategy(MaskType.EMAIL, "MASKED_EMAIL");

        MaskingStrategyHandler handler = new MaskingStrategyHandler(List.of(strategy));

        String result = handler.mask(
                "test@example.com",
                MaskType.EMAIL
        );

        assertEquals("MASKED_EMAIL", result);
    }

    @Test
    void shouldReturnNullWhenValueIsNull() {
        MaskingStrategyHandler handler = new MaskingStrategyHandler(List.of());

        assertNull(
                handler.mask(
                        null,
                        MaskType.EMAIL
                )
        );
    }

    @Test
    void shouldReturnBlankValueUnchanged() {
        MaskingStrategyHandler handler = new MaskingStrategyHandler(List.of());

        assertEquals(
                " ",
                handler.mask(" ", MaskType.EMAIL));
    }

    @Test
    void shouldRejectDuplicateMaskingStrategies() {
        MaskingStrategy first = new TestMaskingStrategy(MaskType.EMAIL, "FIRST");
        MaskingStrategy second = new TestMaskingStrategy(MaskType.EMAIL, "SECOND");

        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new MaskingStrategyHandler(
                        List.of(first, second)
                )
        );

        assertEquals(
                "Duplicate masking strategy for type: EMAIL",
                exception.getMessage()
        );
    }

    @Test
    void shouldThrowExceptionWhenStrategyIsNotRegistered() {
        MaskingStrategyHandler handler = new MaskingStrategyHandler(List.of());

        MaskingException exception = assertThrows(
                MaskingException.class,
                () -> handler.mask(
                        "999999999",
                        MaskType.PHONE
                )
        );

        assertEquals(
                "No masking strategy registered for type: PHONE",
                exception.getMessage()
        );
    }

    private record TestMaskingStrategy(
            MaskType type,
            String result
    ) implements MaskingStrategy {

        @Override
        public MaskType supports() {
            return type;
        }

        @Override
        public String mask(String value) {
            return result;
        }
    }


}
