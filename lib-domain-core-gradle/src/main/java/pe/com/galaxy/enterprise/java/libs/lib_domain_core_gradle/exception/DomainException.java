package pe.com.galaxy.enterprise.java.libs.lib_domain_core_gradle.exception;

/**
 * Base exception for errors produced by domain rules.
 *
 * <p>Each domain exception contains an error code that can be used by
 * higher layers to identify the type of business or domain failure.</p>
 *
 * @since 0.0.1
 */
public abstract class DomainException extends RuntimeException {

    private final String code;

    /**
     * Creates a new domain exception.
     *
     * @param code unique code identifying the domain error
     * @param message human-readable description of the error
     */
    protected DomainException(
            String code,
            String message
    ) {
        super(message);
        this.code = code;
    }

    /**
     * Returns the code associated with the domain error.
     *
     * @return the domain error code
     */
    public String code() {
        return code;
    }
}