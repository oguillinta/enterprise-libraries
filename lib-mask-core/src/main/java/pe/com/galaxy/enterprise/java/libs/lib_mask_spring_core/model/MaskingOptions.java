package pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.model;

/**
 * Defines configurable options for generic masking operations.
 *
 * <p>The options specify how many characters remain visible at the beginning
 * and end of a value together with the character used to hide the remaining
 * content.</p>
 *
 * @param visiblePrefix number of characters to preserve at the beginning
 * @param visibleSuffix number of characters to preserve at the end
 * @param maskCharacter character used to replace hidden content
 * @since 0.0.1
 */
public record MaskingOptions(
        int visiblePrefix,
        int visibleSuffix,
        char maskCharacter
) {
}