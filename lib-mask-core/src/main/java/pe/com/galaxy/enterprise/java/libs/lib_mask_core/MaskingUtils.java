package pe.com.galaxy.enterprise.java.libs.lib_mask_core;

import pe.com.galaxy.enterprise.java.libs.lib_mask_core.contract.MaskerService;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.handler.MaskingStrategyHandler;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskingOptions;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskType;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy.AccountMaskingStrategyImpl;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy.CardMaskingStrategyImpl;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy.DocumentMaskingStrategyImpl;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy.EmailMaskingStrategyImpl;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy.PersonNameMaskingStrategyImpl;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy.PhoneMaskingStrategyImpl;

import java.util.List;

/**
 * Convenience facade exposing the default masking strategies included
 * with the library.
 *
 * @since 0.0.1
 */
public final class MaskingUtils {

    private static final MaskerService MASKER =
            new MaskingStrategyHandler(
                    List.of(
                            new AccountMaskingStrategyImpl(),
                            new CardMaskingStrategyImpl(),
                            new DocumentMaskingStrategyImpl(),
                            new EmailMaskingStrategyImpl(),
                            new PhoneMaskingStrategyImpl(),
                            new PersonNameMaskingStrategyImpl()
                    )
            );

    private MaskingUtils() {
    }

    /**
     * Masks a value using the default behavior associated with the
     * specified type.
     *
     * @param value value to mask
     * @param type masking type
     * @return masked value
     */
    public static String mask(
            String value,
            MaskType type
    ) {
        return MASKER.mask(
                value,
                type
        );
    }

    /**
     * Masks a value using custom masking options.
     *
     * @param value value to mask
     * @param type masking type
     * @param options masking configuration
     * @return masked value
     *
     * @since 1.2.0
     */
    public static String mask(
            String value,
            MaskType type,
            MaskingOptions options
    ) {
        return MASKER.mask(
                value,
                type,
                options
        );
    }
}