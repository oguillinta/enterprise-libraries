package pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy;

import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskingOptions;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskType;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Masks personal names while preserving a configurable number of
 * characters from each name component.
 *
 * <p>The default behavior preserves the first character of every
 * component and masks all remaining characters.</p>
 *
 * <p>Examples using default configuration:</p>
 *
 * <ul>
 *     <li>{@code Nasly -> N****}</li>
 *     <li>{@code Gomez -> G****}</li>
 *     <li>{@code Maria Lopez -> M**** L****}</li>
 * </ul>
 *
 * <p>Configurable example with a visible prefix of two:</p>
 *
 * <ul>
 *     <li>{@code Nasly -> Na***}</li>
 *     <li>{@code Gomez -> Go***}</li>
 * </ul>
 *
 * <p>At least one character of every name component remains masked.</p>
 *
 * @since 1.1.0
 */
public final class PersonNameMaskingStrategyImpl
        implements MaskingStrategy {

    private static final int DEFAULT_VISIBLE_PREFIX = 1;
    private static final int DEFAULT_VISIBLE_SUFFIX = 0;
    private static final char DEFAULT_MASK_CHARACTER = '*';

    /**
     * Returns the mask type supported by this strategy.
     *
     * @return {@link MaskType#PERSON_NAME}
     */
    @Override
    public MaskType supports() {
        return MaskType.PERSON_NAME;
    }

    /**
     * Masks a personal name using the default strategy configuration.
     *
     * @param value personal name
     * @return masked personal name
     */
    @Override
    public String mask(String value) {
        return mask(
                value,
                MaskingOptions.defaults()
        );
    }

    /**
     * Masks a personal name using configurable options.
     *
     * <p>Each whitespace-separated name component is masked
     * independently.</p>
     *
     * @param value personal name
     * @param options masking configuration
     * @return masked personal name
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

        return Arrays.stream(
                        value.trim()
                                .split("\\s+")
                )
                .map(part ->
                        maskNamePart(
                                part,
                                visiblePrefix,
                                visibleSuffix,
                                maskCharacter
                        )
                )
                .collect(
                        Collectors.joining(" ")
                );
    }

    private String maskNamePart(
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