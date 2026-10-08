package pe.com.galaxy.enterprise.java.libs.lib_string_spring_wrapper;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringServiceTest {

    private final StringService stringService =
            new StringServiceImpl();

    @Test
    void shouldNormalizeWhitespace() {
        String result =
                stringService.normalizeWhitespace(
                        "  Galaxy    Enterprise  "
                );

        assertEquals(
                "Galaxy Enterprise",
                result
        );
    }

    @Test
    void shouldDetectBlankValue() {
        assertTrue(
                stringService.isBlank("   ")
        );
    }

    @Test
    void shouldTruncateValue() {
        String result =
                stringService.truncate(
                        "123456789",
                        5
                );

        assertEquals(
                "12345",
                result
        );
    }
}