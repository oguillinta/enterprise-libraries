package pe.com.galaxy.enterprise.java.libs.lib_log_core.factory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_log_core.context.MdcLogContextHolder;
import pe.com.galaxy.enterprise.java.libs.lib_log_core.contract.LogContextHolder;
import pe.com.galaxy.enterprise.java.libs.lib_log_core.contract.LogService;
import pe.com.galaxy.enterprise.java.libs.lib_log_core.model.LogFields;
import pe.com.galaxy.enterprise.java.libs.lib_log_core.service.Slf4jLogService;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link LogFactory}.
 *
 * <p>Verifies creation of SLF4J-backed log services and access to the
 * shared logging context.</p>
 *
 * @since 1.1.0
 */
public class LogFactoryTest {

    @AfterEach
    void tearDown() {
        LogFactory.context().clear();
    }

    @Test
    void shouldCreateSlf4jLogServiceForSourceClass() {
        LogService result =
                LogFactory.getLogger(
                        PurchaseAuthorizationService.class
                );

        assertNotNull(result);

        assertInstanceOf(
                Slf4jLogService.class,
                result
        );
    }

    @Test
    void shouldCreateIndependentLogServiceInstances() {
        LogService first =
                LogFactory.getLogger(
                        PurchaseAuthorizationService.class
                );

        LogService second =
                LogFactory.getLogger(
                        CustomerEligibilityService.class
                );

        assertNotSame(
                first,
                second
        );
    }

    @Test
    void shouldReturnSharedLogContextHolder() {
        LogContextHolder first =
                LogFactory.context();

        LogContextHolder second =
                LogFactory.context();

        assertSame(
                first,
                second
        );
    }

    @Test
    void shouldUseMdcLogContextHolder() {
        LogContextHolder contextHolder =
                LogFactory.context();

        assertInstanceOf(
                MdcLogContextHolder.class,
                contextHolder
        );
    }

    @Test
    void shouldStoreContextValueUsingSharedContext() {
        LogFactory.context().put(
                LogFields.CORRELATION_ID,
                "corr-8f531b2c"
        );

        assertEquals(
                "corr-8f531b2c",
                LogFactory.context().get(
                        LogFields.CORRELATION_ID
                )
        );
    }

    @Test
    void shouldClearSharedContext() {
        LogFactory.context().put(
                LogFields.APPLICATION,
                "credit-card-payment-api"
        );

        LogFactory.context().clear();

        assertNull(
                LogFactory.context().get(
                        LogFields.APPLICATION
                )
        );
    }

    private static final class PurchaseAuthorizationService {
    }

    private static final class CustomerEligibilityService {
    }
}