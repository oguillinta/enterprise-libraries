package pe.com.galaxy.enterprise.java.libs.lib_mask_core.contract;

import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskType;

/**
 * Defines the contract for masking sensitive values.
 *
 * <p>Implementations select an appropriate masking strategy based on the
 * specified {@link MaskType} and return a masked representation of the
 * original value.</p>
 *
 * @since 0.0.1
 */
public interface MaskerService {

    /**
     * Masks the specified value according to the requested mask type.
     *
     * @param value the value to mask
     * @param type the type of masking to apply
     * @return the masked value
     */
    String mask(String value, MaskType type);
}