package pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.strategy;

import pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.model.MaskType;

/**
 * Masking strategy for phone numbers.
 *
 * <p>The strategy preserves the final four characters of the phone number
 * while replacing all preceding characters with asterisks.</p>
 *
 * <p>Values containing four or fewer characters are completely masked.</p>
 *
 * @since 0.0.1
 */
public class PhoneMaskingStrategyImpl
        implements MaskingStrategy {

    /**
     * Returns the mask type supported by this strategy.
     *
     * @return {@link MaskType#PHONE}
     */
    @Override
    public MaskType supports() {
        return MaskType.PHONE;
    }

    /**
     * Masks the specified phone number.
     *
     * @param value phone number to mask
     * @return the masked phone number
     */
    @Override
    public String mask(String value) {
        int visibleDigits = 4;

        if (value.length() <= visibleDigits) {
            return "*".repeat(value.length());
        }

        return "*".repeat(value.length() - visibleDigits)
                + value.substring(value.length() - visibleDigits);
    }
}