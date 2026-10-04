package pe.com.galaxy.enterprise.java.libs.lib_log_core.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import pe.com.galaxy.enterprise.java.libs.lib_log_core.contract.LogContextHolder;
import pe.com.galaxy.enterprise.java.libs.lib_log_core.model.LogFields;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Slf4jLogService}.
 *
 * <p>Verifies log levels, event information, structured attributes,
 * contextual logging fields, and exception logging.</p>
 *
 * @since 0.0.1
 */
public class Slf4jLogServiceTest {

    private TestLogContextHolder contextHolder;
    private Slf4jLogService logService;

    private Logger logger;
    private ListAppender<ILoggingEvent> appender;
    private Level previousLevel;

    @BeforeEach
    void setUp() {
        contextHolder =
                new TestLogContextHolder();

        logService =
                new Slf4jLogService(
                        PurchaseAuthorizationService.class,
                        contextHolder
                );

        logger =
                (Logger) LoggerFactory.getLogger(
                        PurchaseAuthorizationService.class
                );

        previousLevel = logger.getLevel();

        logger.setLevel(
                Level.DEBUG
        );

        appender =
                new ListAppender<>();

        appender.start();

        logger.addAppender(
                appender
        );
    }

    @AfterEach
    void tearDown() {
        logger.detachAppender(
                appender
        );

        appender.stop();

        logger.setLevel(
                previousLevel
        );
    }

    @Test
    void shouldWriteDebugLog() {
        logService.debug(
                "PURCHASE_VALIDATION_STARTED",
                "Validating purchase authorization request"
        );

        ILoggingEvent logEvent =
                lastEvent();

        assertEquals(
                Level.DEBUG,
                logEvent.getLevel()
        );

        assertTrue(
                logEvent.getFormattedMessage()
                        .contains(
                                "event=PURCHASE_VALIDATION_STARTED"
                        )
        );

        assertTrue(
                logEvent.getFormattedMessage()
                        .contains(
                                "message=Validating purchase authorization request"
                        )
        );
    }

    @Test
    void shouldWriteInfoLog() {
        logService.info(
                "PURCHASE_AUTHORIZED",
                "Purchase authorized successfully"
        );

        ILoggingEvent logEvent =
                lastEvent();

        assertEquals(
                Level.INFO,
                logEvent.getLevel()
        );

        assertTrue(
                logEvent.getFormattedMessage()
                        .contains(
                                "event=PURCHASE_AUTHORIZED"
                        )
        );
    }

    @Test
    void shouldWriteWarningLog() {
        logService.warn(
                "AVAILABLE_CREDIT_LOW",
                "Available credit is below configured threshold"
        );

        ILoggingEvent logEvent =
                lastEvent();

        assertEquals(
                Level.WARN,
                logEvent.getLevel()
        );

        assertTrue(
                logEvent.getFormattedMessage()
                        .contains(
                                "event=AVAILABLE_CREDIT_LOW"
                        )
        );
    }

    @Test
    void shouldWriteErrorLog() {
        logService.error(
                "PURCHASE_AUTHORIZATION_FAILED",
                "Unable to authorize purchase"
        );

        ILoggingEvent logEvent =
                lastEvent();

        assertEquals(
                Level.ERROR,
                logEvent.getLevel()
        );

        assertTrue(
                logEvent.getFormattedMessage()
                        .contains(
                                "event=PURCHASE_AUTHORIZATION_FAILED"
                        )
        );
    }

    @Test
    void shouldIncludeLoggingContext() {
        contextHolder.put(
                LogFields.CORRELATION_ID,
                "corr-8f531b2c"
        );

        contextHolder.put(
                LogFields.TRACE_ID,
                "trace-84d63a92"
        );

        contextHolder.put(
                LogFields.SPAN_ID,
                "span-612fbc38"
        );

        contextHolder.put(
                LogFields.APPLICATION,
                "credit-card-payment-api"
        );

        contextHolder.put(
                LogFields.ENVIRONMENT,
                "qa"
        );

        logService.info(
                "PURCHASE_RECEIVED",
                "Purchase authorization request received"
        );

        String message =
                lastEvent()
                        .getFormattedMessage();

        assertTrue(
                message.contains(
                        "correlationId=corr-8f531b2c"
                )
        );

        assertTrue(
                message.contains(
                        "traceId=trace-84d63a92"
                )
        );

        assertTrue(
                message.contains(
                        "spanId=span-612fbc38"
                )
        );

        assertTrue(
                message.contains(
                        "application=credit-card-payment-api"
                )
        );

        assertTrue(
                message.contains(
                        "environment=qa"
                )
        );
    }

    @Test
    void shouldWriteInfoLogWithStructuredAttributes() {
        Map<String, Object> attributes =
                new LinkedHashMap<>();

        attributes.put(
                "purchaseId",
                "PUR-2026-000184"
        );

        attributes.put(
                "merchantId",
                "MER-000871"
        );

        attributes.put(
                "amount",
                "257.90"
        );

        attributes.put(
                "currency",
                "PEN"
        );

        logService.info(
                "PURCHASE_AUTHORIZED",
                "Purchase authorization completed",
                attributes
        );

        String message =
                lastEvent()
                        .getFormattedMessage();

        assertTrue(
                message.contains(
                        "purchaseId=PUR-2026-000184"
                )
        );

        assertTrue(
                message.contains(
                        "merchantId=MER-000871"
                )
        );

        assertTrue(
                message.contains(
                        "amount=257.90"
                )
        );

        assertTrue(
                message.contains(
                        "currency=PEN"
                )
        );
    }

    @Test
    void shouldWriteErrorLogWithThrowable() {
        IllegalStateException cause =
                new IllegalStateException(
                        "Authorization provider unavailable"
                );

        logService.error(
                "AUTHORIZATION_PROVIDER_ERROR",
                "External authorization operation failed",
                cause
        );

        ILoggingEvent logEvent =
                lastEvent();

        assertEquals(
                Level.ERROR,
                logEvent.getLevel()
        );

        assertNotNull(
                logEvent.getThrowableProxy()
        );

        assertEquals(
                IllegalStateException.class.getName(),
                logEvent.getThrowableProxy()
                        .getClassName()
        );

        assertEquals(
                "Authorization provider unavailable",
                logEvent.getThrowableProxy()
                        .getMessage()
        );
    }

    @Test
    void shouldExcludeNullContextValues() {
        contextHolder.put(
                LogFields.CORRELATION_ID,
                "corr-8f531b2c"
        );

        logService.info(
                "PURCHASE_RECEIVED",
                "Purchase request received"
        );

        String message =
                lastEvent()
                        .getFormattedMessage();

        assertTrue(
                message.contains(
                        "correlationId=corr-8f531b2c"
                )
        );

        assertFalse(
                message.contains(
                        "traceId="
                )
        );

        assertFalse(
                message.contains(
                        "spanId="
                )
        );
    }

    @Test
    void shouldExcludeBlankContextValues() {
        contextHolder.put(
                LogFields.CORRELATION_ID,
                "corr-8f531b2c"
        );

        contextHolder.put(
                LogFields.TRACE_ID,
                "   "
        );

        logService.info(
                "PURCHASE_RECEIVED",
                "Purchase request received"
        );

        String message =
                lastEvent()
                        .getFormattedMessage();

        assertTrue(
                message.contains(
                        "correlationId=corr-8f531b2c"
                )
        );

        assertFalse(
                message.contains(
                        "traceId="
                )
        );
    }

    private ILoggingEvent lastEvent() {
        assertFalse(
                appender.list.isEmpty()
        );

        return appender.list.getLast();
    }

    private static final class PurchaseAuthorizationService {
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