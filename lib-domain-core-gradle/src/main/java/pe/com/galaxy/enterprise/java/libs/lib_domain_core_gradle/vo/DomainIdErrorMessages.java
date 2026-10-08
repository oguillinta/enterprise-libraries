package pe.com.galaxy.enterprise.java.libs.lib_domain_core_gradle.vo;

/**
 * Defines error messages used when validating domain identifiers.
 *
 * @since 0.0.1
 */
public final class DomainIdErrorMessages {

    private DomainIdErrorMessages() {
    }

    public static final String DOMAIN_ID_REQUIRED_CODE =
            "ID_REQUIRED_CODE";

    /**
     * Message used when a domain identifier is null or blank.
     */
    public static final String DOMAIN_ID_REQUIRED_MESSAGE =
            "ID cannot be null or blank";

    /**
     * Message used when a domain identifier is not a valid UUID.
     */
    public static final String DOMAIN_ID_INVALID_FORMAT_MESSAGE =
            "ID must be a valid UUID";
}