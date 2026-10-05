package pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy;

import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskingOptions;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskType;

/**
 * Defines a masking algorithm associated with a specific
 * {@link MaskType}.
 *
 * @since 0.0.1
 */
public interface MaskingStrategy {

    /**
     * Returns the mask type supported by this strategy.
     *
     * @return supported mask type
     */
    MaskType supports();

    /**
     * Masks the specified value using the strategy's default behavior.
     *
     * @param value value to mask
     * @return masked value
     */
    String mask(String value);

    /**
     * Masks the specified value using configurable masking options.
     *
     * <p>The default implementation preserves backward compatibility
     * by delegating to {@link #mask(String)}. Strategies that support
     * configurable options can override this method.</p>
     *
     * @param value value to mask
     * @param options masking configuration
     * @return masked value
     *
     * @since 1.2.0
     */
    default String mask(
            String value,
            MaskingOptions options
    ) {
        return mask(value);
    }
}