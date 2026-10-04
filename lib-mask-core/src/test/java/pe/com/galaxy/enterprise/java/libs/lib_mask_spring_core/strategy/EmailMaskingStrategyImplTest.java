package pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.strategy;

import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.model.MaskType;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the {@link EmailMaskingStrategyImpl}.
 *
 * <p>Verifies email masking behavior and supported mask type.</p>
 *
 * @since 0.0.1
 */
public class EmailMaskingStrategyImplTest {

    private final EmailMaskingStrategyImpl strategy =
            new EmailMaskingStrategyImpl();

    @Test
    void shouldSupportEmailMaskType() {
        assertEquals(
                MaskType.EMAIL,
                strategy.supports()
        );
    }

    @Test
    void shouldMaskEmailLocalPart() {
        assertEquals(
                "o****@gmail.com",
                strategy.mask("oscar@gmail.com")
        );
    }

    @Test
    void shouldReturnEmailUnchangedWhenLocalPartHasOneCharacter() {
        assertEquals(
                "a@gmail.com",
                strategy.mask("a@gmail.com")
        );
    }

    @Test
    void shouldReturnValueUnchangedWhenAtSymbolDoesNotExist() {
        assertEquals(
                "invalid-email",
                strategy.mask("invalid-email")
        );
    }
}