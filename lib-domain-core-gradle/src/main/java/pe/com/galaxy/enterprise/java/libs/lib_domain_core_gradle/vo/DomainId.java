package pe.com.galaxy.enterprise.java.libs.lib_domain_core_gradle.vo;

import pe.com.galaxy.enterprise.java.libs.lib_domain_core_gradle.exception.InvalidValueObjectException;

import java.util.Objects;
import java.util.UUID;

import static pe.com.galaxy.enterprise.java.libs.lib_domain_core_gradle.vo.DomainIdErrorMessages.DOMAIN_ID_REQUIRED_CODE;
import static pe.com.galaxy.enterprise.java.libs.lib_domain_core_gradle.vo.DomainIdErrorMessages.DOMAIN_ID_REQUIRED_MESSAGE;

/**
 * Base type for strongly typed domain identifiers backed by a {@link UUID}.
 *
 * <p>This class provides common identifier behavior such as value validation,
 * equality, hash code generation, and string representation. Concrete domain
 * identifiers should extend this class to create type-safe identifiers for
 * domain entities and aggregates.</p>
 *
 * <p>Example:</p>
 *
 * <pre>{@code
 * public final class CustomerId extends DomainId {
 *
 *     public CustomerId(UUID value) {
 *         super(value);
 *     }
 * }
 * }</pre>
 *
 * @since 0.0.1
 */
public abstract class DomainId {

    private final UUID value;

    /**
     * Creates a domain identifier backed by the specified UUID.
     *
     * @param value UUID representing the domain identifier
     * @throws InvalidValueObjectException if {@code value} is {@code null}
     */
    protected DomainId(UUID value) {
        if (value == null) {
            throw new InvalidValueObjectException(
                    DOMAIN_ID_REQUIRED_CODE,
                    DOMAIN_ID_REQUIRED_MESSAGE
            );
        }

        this.value = value;
    }

    /**
     * Returns the UUID value represented by this domain identifier.
     *
     * @return the underlying UUID value
     */
    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        DomainId domainId = (DomainId) other;

        return Objects.equals(value, domainId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}