package pe.com.galaxy.enterprise.java.libs.lib_mask_core.exception;

/**
 * Exception thrown when a masking operation cannot be completed.
 *
 * <p>This exception represents failures related to masking strategy
 * resolution or execution.</p>
 *
 * @since 0.0.1
 */
public class MaskingException extends RuntimeException {

    /**
     * Creates a masking exception with the specified message.
     *
     * @param message description of the masking failure
     */
    public MaskingException(String message) {
        super(message);
    }

    /**
     * Creates a masking exception with the specified message and cause.
     *
     * @param message description of the masking failure
     * @param cause underlying cause of the failure
     */
    public MaskingException(
            String message,
            Throwable cause
    ) {
        super(message, cause);
    }
}