package pe.com.galaxy.enterprise.java.libs.lib_mask_core;

import pe.com.galaxy.enterprise.java.libs.lib_mask_core.contract.MaskerService;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.handler.MaskingStrategyHandler;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskType;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy.*;

import java.util.List;

/**
 * Convenience utility providing access to the default masking strategies
 * included in the library.
 *
 * <p>The utility maintains a predefined {@link MaskerService} containing
 * strategies for account numbers, card numbers, documents, email addresses,
 * and phone numbers.</p>
 *
 * <p>This class cannot be instantiated.</p>
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
                            new PhoneMaskingStrategyImpl()
                    )
            );

    private MaskingUtils() {
    }

    /**
     * Masks the specified value using the default strategy associated with
     * the requested {@link MaskType}.
     *
     * @param value value to mask
     * @param type type of masking to apply
     * @return the masked value
     */
    public static String mask(
            String value,
            MaskType type
    ) {
        return MASKER.mask(value, type);
    }
}