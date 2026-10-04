package pe.com.galaxy.enterprise.java.libs.lib_log_core.context;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_log_core.model.LogFields;

import static org.junit.jupiter.api.Assertions.*;

public class MdcLogContextHolderTest {
    private MdcLogContextHolder contextHolder;

    @BeforeEach
    void setUp() {
        contextHolder = new MdcLogContextHolder();
        contextHolder.clear();
    }

    @AfterEach
    void tearDown() {
        contextHolder.clear();
    }

    @Test
    void shouldStoreAndRetrieveCorrelationId() {
        contextHolder.put(
                LogFields.CORRELATION_ID,
                "corr-8f531b2c"
        );

        assertEquals(
                "corr-8f531b2c",
                contextHolder.get(
                        LogFields.CORRELATION_ID
                )
        );
    }

    @Test
    void shouldStoreMultipleContextValues() {
        contextHolder.put(
                LogFields.CORRELATION_ID,
                "corr-8f531b2c"
        );

        contextHolder.put(
                LogFields.TRACE_ID,
                "trace-84d63a92"
        );

        contextHolder.put(
                LogFields.APPLICATION,
                "credit-card-payment-api"
        );

        assertEquals(
                "corr-8f531b2c",
                contextHolder.get(LogFields.CORRELATION_ID)
        );

        assertEquals(
                "trace-84d63a92",
                contextHolder.get(LogFields.TRACE_ID)
        );

        assertEquals(
                "credit-card-payment-api",
                contextHolder.get(
                        LogFields.APPLICATION
                )
        );
    }

    @Test
    void shouldRemoveContextValue() {
        contextHolder.put(
                LogFields.TRACE_ID,
                "trace-84d63a92"
        );

        contextHolder.remove(
                LogFields.TRACE_ID
        );

        assertNull(
                contextHolder.get(
                        LogFields.TRACE_ID
                )
        );
    }

    @Test
    void shouldClearAllContextValues() {
        contextHolder.put(
                LogFields.CORRELATION_ID,
                "corr-8f531b2c"
        );

        contextHolder.put(
                LogFields.APPLICATION,
                "credit-card-payment-api"
        );

        contextHolder.clear();

        assertNull(
                contextHolder.get(
                        LogFields.CORRELATION_ID
                )
        );

        assertNull(
                contextHolder.get(
                        LogFields.APPLICATION
                )
        );
    }

    @Test
    void shouldIgnoreNullValue() {
        contextHolder.put(
                LogFields.CORRELATION_ID,
                "corr-8f531b2c"
        );

        contextHolder.put(
                LogFields.CORRELATION_ID,
                null
        );

        assertEquals(
                "corr-8f531b2c",
                contextHolder.get(
                        LogFields.CORRELATION_ID
                )
        );
    }

    @Test
    void shouldIgnoreNullKey() {
        assertDoesNotThrow(
                () -> contextHolder.put(
                        null,
                        "corr-8f531b2c"
                )
        );
    }

}
