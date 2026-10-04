package pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.strategy;

import pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.model.MaskType;

/**
 * Masking strategy for personal and business identification documents.
 *
 * <p>The strategy applies specialized rules according to the normalized
 * document length:</p>
 *
 * <ul>
 *     <li>8 characters: DNI masking</li>
 *     <li>11 characters: RUC masking</li>
 *     <li>Other lengths: generic document masking</li>
 * </ul>
 *
 * <p>Null or blank values are returned unchanged.</p>
 *
 * @since 0.0.1
 */
public class DocumentMaskingStrategyImpl
        implements MaskingStrategy {

    /**
     * Returns the mask type supported by this strategy.
     *
     * @return {@link MaskType#DOCUMENT}
     */
    @Override
    public MaskType supports() {
        return MaskType.DOCUMENT;
    }

    /**
     * Masks the specified document according to its normalized length.
     *
     * @param value document value to mask
     * @return the masked document
     */
    @Override
    public String mask(String value) {
        if (value == null || value.isBlank()) {
            return value;
        }

        String normalized = value.trim();

        return switch (normalized.length()) {
            case 8 -> maskDni(normalized);
            case 11 -> maskRuc(normalized);
            default -> maskGenericDocument(normalized);
        };
    }

    private String maskDni(String value) {
        return "****" + value.substring(4);
    }

    private String maskRuc(String value) {
        return value.substring(0, 2)
                + "*****"
                + value.substring(7);
    }

    private String maskGenericDocument(String value) {
        if (value.length() <= 4) {
            return "*".repeat(value.length());
        }

        return "*".repeat(value.length() - 4)
                + value.substring(value.length() - 4);
    }
}