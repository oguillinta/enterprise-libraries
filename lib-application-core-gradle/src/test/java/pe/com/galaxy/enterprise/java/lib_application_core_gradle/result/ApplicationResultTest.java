package pe.com.galaxy.enterprise.java.lib_application_core_gradle.result;

import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_application_core_gradle.result.ApplicationResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Unit tests for the {@link ApplicationResult} application result model.
 *
 * <p>Verifies successful and failed application outcomes together with
 * their associated data, code, and message.</p>
 *
 * @since 0.0.1
 */
public class ApplicationResultTest {

    @Test
    void shouldCreateSuccessfulApplicationResult() {
        String data = "CUSTOMER-001";

        ApplicationResult<String> result = ApplicationResult.success(
                data,
                "SUCCESS",
                "Operation completed successfully"
        );

        assertEquals(data, result.data());
        assertEquals("SUCCESS", result.code());
        assertEquals(
                "Operation completed successfully",
                result.message()
        );
    }

    @Test
    void shouldCreateFailedApplicationResult() {
        ApplicationResult<String> result = ApplicationResult.failure(
                "CUSTOMER_NOT_FOUND",
                "Customer was not found"
        );

        assertNull(result.data());
        assertEquals("CUSTOMER_NOT_FOUND", result.code());
        assertEquals("Customer was not found", result.message());


    }
}
