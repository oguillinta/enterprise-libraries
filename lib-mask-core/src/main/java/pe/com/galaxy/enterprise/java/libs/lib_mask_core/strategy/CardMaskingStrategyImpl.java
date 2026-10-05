package pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy;

import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskingOptions;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskType;

import java.util.Objects;

/**
 * Masking strategy for payment card numbers.
 *
 * <p>The default behavior preserves the first four and final four
 * characters while masking the intermediate characters.</p>
 *
 * <p>Example:</p>
 *
 * <pre>
 * {@code
 * 4556123412345678
 * ->
 * 4556********5678
 * }
 * </pre>
 *
 * <p>The strategy supports configurable visible prefixes, suffixes,
 * and masking characters through {@link MaskingOptions}.</p>
 *
 * <p>For example, using six visible prefix characters and four visible
 * suffix characters:</p>
 *
 * <pre>
 * {@code
 * 4556123412345678
 * ->
 * 455612******5678
 * }
 * </pre>
 *
 * <p>At least one character always remains masked, preventing a
 * configuration from exposing the complete card number.</p>
 *
 * <p>Null or blank values are returned unchanged.</p>
 *
 * @since 0.0.1
 */
public final class CardMaskingStrategyImpl
        implements MaskingStrategy {

    private static final int DEFAULT_VISIBLE_PREFIX = 4;
    private static final int DEFAULT_VISIBLE_SUFFIX = 4;
    private static final char DEFAULT_MASK_CHARACTER = '*';

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
     * Masks a payment card number using the default strategy configuration.
     *
     * <p>The default configuration preserves the first four and last four
     * characters while masking all intermediate characters.</p>
     *
     * @param value payment card number to mask
     * @return masked payment card number, or the original value when it is
     *         {@code null} or blank
     */
    @Override
    public String mask(String value) {
        return mask(
                value,
                MaskingOptions.defaults()
        );
    }

    /**
     * Masks a payment card number using configurable masking options.
     *
     * <p>When an option delegates to the strategy default, this
     * implementation uses:</p>
     *
     * <ul>
     *     <li>visible prefix: {@value #DEFAULT_VISIBLE_PREFIX}</li>
     *     <li>visible suffix: {@value #DEFAULT_VISIBLE_SUFFIX}</li>
     *     <li>mask character: {@code *}</li>
     * </ul>
     *
     * <p>The requested prefix and suffix are constrained according to the
     * length of the supplied value so that at least one character remains
     * masked.</p>
     *
     * @param value payment card number to mask
     * @param options masking configuration
     * @return masked payment card number
     * @throws NullPointerException if {@code options} is {@code null}
     *
     * @since 1.2.0
     */
    @Override
    public String mask(
            String value,
            MaskingOptions options
    ) {
        if (value == null || value.isBlank()) {
            return value;
        }

        Objects.requireNonNull(
                options,
                "Masking options cannot be null"
        );

        int visiblePrefix =
                options.hasVisiblePrefixOverride()
                        ? options.visiblePrefix()
                        : DEFAULT_VISIBLE_PREFIX;

        int visibleSuffix =
                options.hasVisibleSuffixOverride()
                        ? options.visibleSuffix()
                        : DEFAULT_VISIBLE_SUFFIX;

        char maskCharacter =
                options.hasMaskCharacterOverride()
                        ? options.maskCharacter()
                        : DEFAULT_MASK_CHARACTER;

        return maskValue(
                value,
                visiblePrefix,
                visibleSuffix,
                maskCharacter
        );
    }

    /**
     * Applies prefix/suffix masking to the supplied card number.
     *
     * <p>The requested visible sections are safely constrained to the
     * available value length. At least one character is always replaced
     * by the masking character.</p>
     *
     * <p>For a single-character value, that character is completely
     * masked.</p>
     *
     * @param value card number to mask
     * @param requestedPrefix requested number of visible leading characters
     * @param requestedSuffix requested number of visible trailing characters
     * @param maskCharacter character used to replace hidden content
     * @return masked card number
     */
    private String maskValue(
            String value,
            int requestedPrefix,
            int requestedSuffix,
            char maskCharacter
    ) {
        int length =
                value.length();

        if (length == 1) {
            return String.valueOf(
                    maskCharacter
            );
        }

        int visiblePrefix =
                Math.min(
                        requestedPrefix,
                        length - 1
                );

        int remainingAfterPrefix =
                length - visiblePrefix;

        int visibleSuffix =
                Math.clamp(
                        remainingAfterPrefix - 1
                        ,
                        0,
                        requestedSuffix);

        int maskedCharacters =
                length
                        - visiblePrefix
                        - visibleSuffix;

        String prefix =
                value.substring(
                        0,
                        visiblePrefix
                );

        String masked =
                String.valueOf(
                        maskCharacter
                ).repeat(
                        maskedCharacters
                );

        String suffix =
                visibleSuffix == 0
                        ? ""
                        : value.substring(
                        length - visibleSuffix
                );

        return prefix
                + masked
                + suffix;
    }
}