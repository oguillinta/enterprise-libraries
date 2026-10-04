package pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.strategy;

import pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.model.MaskType;

/**
 * Defines a strategy for masking a specific category of sensitive data.
 *
 * <p>Each implementation declares the {@link MaskType} it supports and
 * provides the corresponding masking behavior.</p>
 *
 * @since 0.0.1
 */
public interface MaskingStrategy {

    /**
     * Returns the masking type supported by this strategy.
     *
     * @return the supported mask type
     */
    MaskType supports();

    /**
     * Masks the specified value according to the strategy rules.
     *
     * @param value the value to mask
     * @return the masked representation of the value
     */
    String mask(String value);
}