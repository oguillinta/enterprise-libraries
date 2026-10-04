package pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy;

import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskType;

/**
 * Masking strategy for email addresses.
 *
 * <p>The first character of the local part is preserved while the remaining
 * local-part characters are replaced with asterisks. The domain portion
 * remains unchanged.</p>
 *
 * <p>If the local part contains one character or less, or if a valid masking
 * position cannot be identified, the original value is returned unchanged.</p>
 *
 * @since 0.0.1
 */
public class EmailMaskingStrategyImpl
        implements MaskingStrategy {

    /**
     * Returns the mask type supported by this strategy.
     *
     * @return {@link MaskType#EMAIL}
     */
    @Override
    public MaskType supports() {
        return MaskType.EMAIL;
    }

    /**
     * Masks the local part of the specified email address.
     *
     * @param value email address to mask
     * @return the masked email address
     */
    @Override
    public String mask(String value) {
        int atIndex = value.indexOf('@');

        if (atIndex <= 1) {
            return value;
        }

        String localPart = value.substring(0, atIndex);
        String domain = value.substring(atIndex);

        return localPart.charAt(0)
                + "*".repeat(localPart.length() - 1)
                + domain;
    }
}