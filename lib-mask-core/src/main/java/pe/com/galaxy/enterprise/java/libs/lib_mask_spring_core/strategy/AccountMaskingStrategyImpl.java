package pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.strategy;

import pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.model.MaskType;

/**
 * Masking strategy for bank account numbers.
 *
 * <p>The strategy removes whitespace and preserves only the final four
 * characters of the account number. All preceding characters are replaced
 * with asterisks.</p>
 *
 * <p>Values containing four or fewer characters are completely masked.
 * Null or blank values are returned unchanged.</p>
 *
 * @since 0.0.1
 */
public class AccountMaskingStrategyImpl
        implements MaskingStrategy {

    private static final int VISIBLE_SUFFIX = 4;

    /**
     * Returns the mask type supported by this strategy.
     *
     * @return {@link MaskType#ACCOUNT_NUMBER}
     */
    @Override
    public MaskType supports() {
        return MaskType.ACCOUNT_NUMBER;
    }

    /**
     * Masks the specified account number.
     *
     * @param value account number to mask
     * @return the masked account number
     */
    @Override
    public String mask(String value) {
        if (value == null || value.isBlank()) {
            return value;
        }

        String normalized = value.replaceAll("\\s", "");

        if (normalized.length() <= VISIBLE_SUFFIX) {
            return "*".repeat(normalized.length());
        }

        return "*".repeat(
                normalized.length() - VISIBLE_SUFFIX
        ) + normalized.substring(
                normalized.length() - VISIBLE_SUFFIX
        );
    }
}