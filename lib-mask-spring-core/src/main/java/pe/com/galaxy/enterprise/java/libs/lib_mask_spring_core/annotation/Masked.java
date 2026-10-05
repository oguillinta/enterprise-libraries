package pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.annotation;

import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskingOptions;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Applies masking to a String property during JSON serialization.
 *
 * @since 1.0.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.FIELD,
        ElementType.METHOD,
        ElementType.RECORD_COMPONENT
})
public @interface Masked {

    /**
     * Returns the masking type to apply.
     *
     * @return masking type
     */
    MaskType value();

    /**
     * Returns the number of visible leading characters.
     *
     * @return visible prefix or the strategy default
     *
     * @since 1.2.0
     */
    int visiblePrefix()
            default MaskingOptions.USE_STRATEGY_DEFAULT;

    /**
     * Returns the number of visible trailing characters.
     *
     * @return visible suffix or the strategy default
     *
     * @since 1.2.0
     */
    int visibleSuffix()
            default MaskingOptions.USE_STRATEGY_DEFAULT;

    /**
     * Returns the character used to mask hidden content.
     *
     * @return masking character or the strategy default
     *
     * @since 1.2.0
     */
    char maskCharacter()
            default MaskingOptions.USE_STRATEGY_DEFAULT_CHARACTER;
}