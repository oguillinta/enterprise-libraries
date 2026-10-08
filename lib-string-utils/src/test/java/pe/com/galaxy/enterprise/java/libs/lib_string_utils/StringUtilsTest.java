package pe.com.galaxy.enterprise.java.libs.lib_string_utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringUtilsTest {

    @Test
    void shouldDetectBlankString() {
        assertTrue(StringUtils.isBlank("   "));
    }

    @Test
    void shouldNormalizeWhitespace() {
        String result =
                StringUtils.normalizeWhitespace(
                        "  Galaxy    Enterprise  "
                );

        assertEquals(
                "Galaxy Enterprise",
                result
        );
    }

    @Test
    void shouldTruncateValue() {
        String result =
                StringUtils.truncate(
                        "123456789",
                        5
                );

        assertEquals("12345", result);
    }
}