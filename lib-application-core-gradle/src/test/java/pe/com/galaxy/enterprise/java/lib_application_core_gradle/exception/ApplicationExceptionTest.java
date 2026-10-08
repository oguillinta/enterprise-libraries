package pe.com.galaxy.enterprise.java.lib_application_core_gradle.exception;

import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_application_core_gradle.exception.ApplicationException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * Unit tests for the {@link ApplicationException}.
 *
 * <p>Verifies application error code, message, and cause propagation.</p>
 *
 * @since 0.0.1
 */
public class ApplicationExceptionTest {

    @Test
    void shouldCreateExceptionWithCodeAndMessage() {
        ApplicationException exception = new ApplicationException(
                "APPLICATION_ERROR",
                "Application operation failed"
        );

        assertEquals("APPLICATION_ERROR", exception.code());
        assertEquals("Application operation failed", exception.getMessage());
    }

    @Test
    void shouldCreateExceptionWithCause() {
        RuntimeException cause = new RuntimeException("Original failure");

        ApplicationException exception = new ApplicationException(
                "APPLICATION_ERROR",
                "Application operation failed",
                cause
        );

        assertEquals("APPLICATION_ERROR", exception.code());
        assertEquals("Application operation failed", exception.getMessage());
        assertSame(cause, exception.getCause());
    }
}
