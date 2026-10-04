package pe.com.galaxy.enterprise.java.libs.lib_log_core.factory;

import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_log_core.contract.LogContextHolder;
import pe.com.galaxy.enterprise.java.libs.lib_log_core.contract.LogService;
import pe.com.galaxy.enterprise.java.libs.lib_log_core.service.Slf4jLogService;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Slf4jLogServiceFactory}.
 *
 * <p>Verifies creation of SLF4J-backed logging services for
 * application source classes.</p>
 *
 * @since 0.0.1
 */
public class Slf4jLogServiceFactoryTest {

    @Test
    void shouldCreateSlf4jLogServiceForSourceClass() {
        LogContextHolder contextHolder =
                new TestLogContextHolder();

        Slf4jLogServiceFactory factory =
                new Slf4jLogServiceFactory(
                        contextHolder
                );

        LogService result =
                factory.getLogger(
                        PurchaseAuthorizationService.class
                );

        assertNotNull(result);

        assertInstanceOf(
                Slf4jLogService.class,
                result
        );
    }

    @Test
    void shouldCreateIndependentLoggerInstances() {
        LogContextHolder contextHolder =
                new TestLogContextHolder();

        Slf4jLogServiceFactory factory =
                new Slf4jLogServiceFactory(
                        contextHolder
                );

        LogService purchaseLogger =
                factory.getLogger(
                        PurchaseAuthorizationService.class
                );

        LogService customerLogger =
                factory.getLogger(
                        CustomerEligibilityService.class
                );

        assertNotSame(
                purchaseLogger,
                customerLogger
        );
    }

    private static final class PurchaseAuthorizationService {
    }

    private static final class CustomerEligibilityService {
    }

    private static final class TestLogContextHolder
            implements LogContextHolder {

        private final Map<String, String> values =
                new LinkedHashMap<>();

        @Override
        public void put(
                String key,
                String value
        ) {
            values.put(
                    key,
                    value
            );
        }

        @Override
        public String get(String key) {
            return values.get(key);
        }

        @Override
        public void remove(String key) {
            values.remove(key);
        }

        @Override
        public void clear() {
            values.clear();
        }
    }
}