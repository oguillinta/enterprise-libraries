package pe.com.galaxy.enterprise.java.libs.lib_application_core_gradle.exception;

/**
 * Base exception for failures occurring in the application layer.
 *
 * <p>This exception is intended for errors related to use-case execution,
 * orchestration, or interaction with application ports, rather than
 * violations of domain business rules.</p>
 *
 * @since 0.0.1
 */
public class ApplicationException extends RuntimeException {

    private final String code;

    /**
     * Creates a new application exception with the specified error code
     * and message.
     *
     * @param code unique code identifying the application error
     * @param message human-readable description of the error
     */
    public ApplicationException(
            String code,
            String message
    ) {
        super(message);
        this.code = code;
    }

    /**
     * Creates a new application exception with the specified error code,
     * message, and root cause.
     *
     * @param code unique code identifying the application error
     * @param message human-readable description of the error
     * @param cause the underlying cause of the application failure
     */
    public ApplicationException(
            String code,
            String message,
            Throwable cause
    ) {
        super(message, cause);
        this.code = code;
    }

    /**
     * Returns the code associated with the application error.
     *
     * @return the application error code
     */
    public String code() {
        return code;
    }
}