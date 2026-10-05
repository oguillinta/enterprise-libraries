package pe.com.galaxy.enterprise.java.libs.lib_mask_core.model;

/**
 * Defines the supported categories of sensitive information
 * that can be masked.
 *
 * @since 0.0.1
 */
public enum MaskType {

    EMAIL,
    CARD_NUMBER,
    PHONE,
    DOCUMENT,
    ACCOUNT_NUMBER,
    PERSON_NAME
}