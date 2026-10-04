package pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link MaskingException}.
 *
 * <p>Verifies exception message and cause propagation.</p>
 *
 * @since 0.0.1
 */
public class MaskingExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        MaskingException exception =
                new MaskingException(
                        "Masking failed"
                );

        assertEquals(
                "Masking failed",
                exception.getMessage()
        );
    }

    @Test
    void shouldCreateExceptionWithCause() {
        RuntimeException cause =
                new RuntimeException(
                        "Original failure"
                );

        MaskingException exception =
                new MaskingException(
                        "Masking failed",
                        cause
                );

        assertEquals(
                "Masking failed",
                exception.getMessage()
        );

        assertSame(
                cause,
                exception.getCause()
        );
    }
}