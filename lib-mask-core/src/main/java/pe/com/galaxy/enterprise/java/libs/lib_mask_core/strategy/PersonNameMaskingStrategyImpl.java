package pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy;

import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskType;

/**
 * Masks personal names while preserving the first character of each
 * name component.
 *
 * <p>Examples:</p>
 *
 * <ul>
 *     <li>{@code Nasly -> N****}</li>
 *     <li>{@code Gomez -> G****}</li>
 *     <li>{@code Maria Lopez -> M**** L****}</li>
 * </ul>
 *
 * <p>Null or blank values are returned unchanged.</p>
 *
 * @since 1.1.0
 */
public final class PersonNameMaskingStrategyImpl
        implements MaskingStrategy {

    private static final char MASK_CHARACTER = '*';

    @Override
    public MaskType supports() {
        return MaskType.PERSON_NAME;
    }

    @Override
    public String mask(String value) {
        if (value == null || value.isBlank()) {
            return value;
        }

        String normalized = value.trim();

        String[] parts =
                normalized.split("\\s+");

        StringBuilder result =
                new StringBuilder();

        for (int index = 0; index < parts.length; index++) {

            if (index > 0) {
                result.append(' ');
            }

            result.append(
                    maskNamePart(parts[index])
            );
        }

        return result.toString();
    }

    private String maskNamePart(String value) {
        if (value.length() == 1) {
            return String.valueOf(
                    MASK_CHARACTER
            );
        }

        return value.charAt(0)
                + String.valueOf(MASK_CHARACTER)
                .repeat(value.length() - 1);
    }
}