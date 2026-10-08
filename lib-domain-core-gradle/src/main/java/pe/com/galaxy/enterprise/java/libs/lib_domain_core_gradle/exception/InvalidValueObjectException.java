package pe.com.galaxy.enterprise.java.libs.lib_domain_core_gradle.exception;

/**
 * Exception thrown when a value object cannot be created because one or
 * more of its values violate domain invariants.
 *
 * @since 0.0.1
 */
public class InvalidValueObjectException extends DomainException {

    /**
     * Creates a new invalid value object exception.
     *
     * @param code unique code identifying the validation error
     * @param message human-readable description of the validation error
     */
    public InvalidValueObjectException(
            String code,
            String message
    ) {
        super(code, message);
    }
}