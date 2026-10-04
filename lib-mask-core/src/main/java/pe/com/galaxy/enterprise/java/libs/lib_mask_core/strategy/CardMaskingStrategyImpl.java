package pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy;

import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskType;

/**
 * Masking strategy for payment card numbers.
 *
 * <p>The strategy preserves the first four and last four characters of the
 * card number while replacing the intermediate characters with asterisks.</p>
 *
 * <p>Values shorter than eight characters are completely masked.</p>
 *
 * @since 0.0.1
 */
public class CardMaskingStrategyImpl
        implements MaskingStrategy {

    /**
     * Returns the mask type supported by this strategy.
     *
     * @return {@link MaskType#CARD_NUMBER}
     */
    @Override
    public MaskType supports() {
        return MaskType.CARD_NUMBER;
    }

    /**
     * Masks the specified card number.
     *
     * @param value card number to mask
     * @return the masked card number
     */
    @Override
    public String mask(String value) {
        if (value.length() < 8) {
            return "*".repeat(value.length());
        }

        String prefix = value.substring(0, 4);
        String suffix = value.substring(value.length() - 4);

        return prefix
                + "*".repeat(value.length() - 8)
                + suffix;
    }
}