package pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy;

import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskingOptions;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskType;

import java.util.Objects;

/**
 * Masking strategy for email addresses.
 *
 * <p>The strategy masks characters from the local part of an email address
 * while preserving the domain.</p>
 *
 * <p>The default behavior preserves the first character of the local part
 * and masks the remaining characters.</p>
 *
 * <p>Example:</p>
 *
 * <pre>
 * {@code
 * nasly.gomez@email.com
 * ->
 * n**********@email.com
 * }
 * </pre>
 *
 * <p>The strategy also supports configurable visible prefixes, suffixes,
 * and masking characters through {@link MaskingOptions}.</p>
 *
 * <p>For example, using a visible prefix of {@code 2} and suffix of
 * {@code 2}:</p>
 *
 * <pre>
 * {@code
 * nasly.gomez@email.com
 * ->
 * na*******ez@email.com
 * }
 * </pre>
 *
 * <p>Null or blank values are returned unchanged. Values that cannot be
 * interpreted as supported email addresses are also returned unchanged.</p>
 *
 * @since 0.0.1
 */
public final class EmailMaskingStrategyImpl
        implements MaskingStrategy {

    private static final int DEFAULT_VISIBLE_PREFIX = 1;
    private static final int DEFAULT_VISIBLE_SUFFIX = 0;
    private static final char DEFAULT_MASK_CHARACTER = '*';

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
     * Masks an email address using the default strategy configuration.
     *
     * <p>The default configuration preserves the first character of the
     * local part, masks the remaining local-part characters, and leaves
     * the domain unchanged.</p>
     *
     * @param value email address to mask
     * @return masked email address, or the original value when it is
     *         {@code null}, blank, or cannot be processed by this strategy
     */
    @Override
    public String mask(String value) {
        return mask(
                value,
                MaskingOptions.defaults()
        );
    }

    /**
     * Masks an email address using configurable masking options.
     *
     * <p>The prefix and suffix options are applied only to the local part
     * of the email address. The domain remains visible.</p>
     *
     * <p>When an option is configured to use the strategy default, this
     * implementation resolves it to:</p>
     *
     * <ul>
     *     <li>visible prefix: {@value #DEFAULT_VISIBLE_PREFIX}</li>
     *     <li>visible suffix: {@value #DEFAULT_VISIBLE_SUFFIX}</li>
     *     <li>mask character: {@code *}</li>
     * </ul>
     *
     * <p>At least one character of a local part containing two or more
     * characters remains masked.</p>
     *
     * @param value email address to mask
     * @param options masking configuration
     * @return masked email address
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

        int atIndex =
                value.indexOf('@');

        /*
         * Preserve the previous strategy behavior for malformed addresses
         * and one-character local parts.
         */
        if (atIndex <= 1) {
            return value;
        }

        String localPart =
                value.substring(
                        0,
                        atIndex
                );

        String domain =
                value.substring(
                        atIndex
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

        return maskLocalPart(
                localPart,
                visiblePrefix,
                visibleSuffix,
                maskCharacter
        ) + domain;
    }

    /**
     * Applies prefix/suffix masking to an email local part.
     *
     * <p>The requested prefix and suffix values are safely constrained to
     * the available length. The method guarantees that at least one
     * character remains masked.</p>
     *
     * @param value local part of the email address
     * @param requestedPrefix requested number of visible leading characters
     * @param requestedSuffix requested number of visible trailing characters
     * @param maskCharacter character used for hidden content
     * @return masked local part
     */
    private String maskLocalPart(
            String value,
            int requestedPrefix,
            int requestedSuffix,
            char maskCharacter
    ) {
        int length =
                value.length();

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