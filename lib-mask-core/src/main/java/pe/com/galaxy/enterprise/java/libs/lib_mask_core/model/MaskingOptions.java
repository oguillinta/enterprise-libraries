package pe.com.galaxy.enterprise.java.libs.lib_mask_core.model;

/**
 * Defines optional masking configuration that can override
 * strategy-specific defaults.
 *
 * <p>A value of {@link #USE_STRATEGY_DEFAULT} indicates that the
 * corresponding numeric option should be resolved by the masking
 * strategy.</p>
 *
 * <p>A value of {@link #USE_STRATEGY_DEFAULT_CHARACTER} indicates
 * that the masking character should be resolved by the strategy.</p>
 *
 * @param visiblePrefix number of visible characters at the beginning
 * @param visibleSuffix number of visible characters at the end
 * @param maskCharacter character used to replace hidden characters
 *
 * @since 0.0.1
 */
public record MaskingOptions(
        int visiblePrefix,
        int visibleSuffix,
        char maskCharacter
) {

    public static final int USE_STRATEGY_DEFAULT = -1;

    public static final char USE_STRATEGY_DEFAULT_CHARACTER = '\0';

    /**
     * Validates masking option values.
     */
    public MaskingOptions {
        if (visiblePrefix < USE_STRATEGY_DEFAULT) {
            throw new IllegalArgumentException(
                    "Visible prefix cannot be lower than -1"
            );
        }

        if (visibleSuffix < USE_STRATEGY_DEFAULT) {
            throw new IllegalArgumentException(
                    "Visible suffix cannot be lower than -1"
            );
        }
    }

    /**
     * Creates options that delegate all decisions to the masking strategy.
     *
     * @return default masking options
     *
     * @since 1.2.0
     */
    public static MaskingOptions defaults() {
        return new MaskingOptions(
                USE_STRATEGY_DEFAULT,
                USE_STRATEGY_DEFAULT,
                USE_STRATEGY_DEFAULT_CHARACTER
        );
    }

    /**
     * Determines whether a custom visible prefix was specified.
     *
     * @return {@code true} when the prefix overrides the strategy default
     *
     * @since 1.2.0
     */
    public boolean hasVisiblePrefixOverride() {
        return visiblePrefix != USE_STRATEGY_DEFAULT;
    }

    /**
     * Determines whether a custom visible suffix was specified.
     *
     * @return {@code true} when the suffix overrides the strategy default
     *
     * @since 1.2.0
     */
    public boolean hasVisibleSuffixOverride() {
        return visibleSuffix != USE_STRATEGY_DEFAULT;
    }

    /**
     * Determines whether a custom masking character was specified.
     *
     * @return {@code true} when the character overrides the strategy default
     *
     * @since 1.2.0
     */
    public boolean hasMaskCharacterOverride() {
        return maskCharacter != USE_STRATEGY_DEFAULT_CHARACTER;
    }
}