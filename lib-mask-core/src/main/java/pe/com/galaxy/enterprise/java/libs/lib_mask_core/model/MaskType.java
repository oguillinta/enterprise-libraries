package pe.com.galaxy.enterprise.java.libs.lib_mask_core.model;

/**
 * Defines the categories of sensitive values supported by the masking library.
 *
 * @since 0.0.1
 */
public enum MaskType {

    /**
     * Email address masking.
     */
    EMAIL,

    /**
     * Payment card number masking.
     */
    CARD_NUMBER,

    /**
     * Phone number masking.
     */
    PHONE,

    /**
     * Personal or business document masking.
     */
    DOCUMENT,

    /**
     * Bank account number masking.
     */
    ACCOUNT_NUMBER,

    /**
     * Person name masking.
     */
    PERSON_NAME
}