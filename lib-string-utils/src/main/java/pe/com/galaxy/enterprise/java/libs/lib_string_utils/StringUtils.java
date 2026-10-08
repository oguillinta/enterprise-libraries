package pe.com.galaxy.enterprise.java.libs.lib_string_utils;

/**
 * Provides common operations for working with strings.
 *
 * @since 1.0.0
 */
public final class StringUtils {

    private StringUtils() {
    }

    /**
     * Determines whether a value is null, empty, or contains only whitespace.
     *
     * @param value value to evaluate
     * @return {@code true} when the value is blank
     */
    public static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    /**
     * Returns {@code null} when the supplied value is blank.
     *
     * @param value value to evaluate
     * @return the original value or {@code null}
     */
    public static String nullIfBlank(String value) {
        return isBlank(value) ? null : value;
    }

    /**
     * Removes leading and trailing whitespace and collapses internal
     * whitespace into a single space.
     *
     * @param value value to normalize
     * @return normalized value, or {@code null} when the input is null
     */
    public static String normalizeWhitespace(String value) {
        if (value == null) {
            return null;
        }

        return value.strip()
                .replaceAll("\\s+", " ");
    }

    /**
     * Truncates a string to the specified maximum length.
     *
     * @param value value to truncate
     * @param maxLength maximum allowed length
     * @return truncated value
     * @throws IllegalArgumentException when {@code maxLength} is negative
     */
    public static String truncate(String value, int maxLength) {
        if (value == null) {
            return null;
        }

        if (maxLength < 0) {
            throw new IllegalArgumentException(
                    "Maximum length cannot be negative"
            );
        }

        return value.length() <= maxLength
                ? value
                : value.substring(0, maxLength);
    }

    /**
     * Returns the supplied value when it is not null.
     *
     * @param value value to evaluate
     * @param defaultValue value returned when input is null
     * @return input or default value
     */
    public static String defaultIfNull(
            String value,
            String defaultValue
    ) {
        return value != null
                ? value
                : defaultValue;
    }
}