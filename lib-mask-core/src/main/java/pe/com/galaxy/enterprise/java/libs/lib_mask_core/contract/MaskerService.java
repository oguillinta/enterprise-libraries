package pe.com.galaxy.enterprise.java.libs.lib_mask_core.contract;

import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskingOptions;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskType;

/**
 * Defines operations for masking sensitive information.
 *
 * @since 0.0.1
 */
public interface MaskerService {

    /**
     * Masks a value using the default behavior associated with
     * the specified mask type.
     *
     * @param value value to mask
     * @param type masking category
     * @return masked value
     */
    String mask(
            String value,
            MaskType type
    );

    /**
     * Masks a value using the specified masking options.
     *
     * <p>Strategies that do not support configurable options may
     * continue using their default behavior.</p>
     *
     * @param value value to mask
     * @param type masking category
     * @param options masking configuration
     * @return masked value
     *
     * @since 1.2.0
     */
    String mask(
            String value,
            MaskType type,
            MaskingOptions options
    );
}