package pe.com.galaxy.enterprise.java.libs.lib_log_core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pe.com.galaxy.enterprise.java.libs.lib_log_core.contract.LogContextHolder;
import pe.com.galaxy.enterprise.java.libs.lib_log_core.contract.LogService;
import pe.com.galaxy.enterprise.java.libs.lib_log_core.model.LogFields;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * SLF4J-based implementation of {@link LogService}.
 *
 * <p>This service provides standardized logging operations using SLF4J
 * while enriching log entries with contextual information obtained from
 * a {@link LogContextHolder}.</p>
 *
 * <p>Contextual information can include correlation identifiers,
 * distributed tracing identifiers, application and environment names,
 * operation information, status, and execution duration.</p>
 *
 * <p>Only non-null and non-blank contextual values are included in the
 * generated context map.</p>
 *
 * @since 0.0.1
 */
public final class Slf4jLogService
        implements LogService {

    private final Logger logger;
    private final LogContextHolder contextHolder;

    /**
     * Creates an SLF4J-backed logging service associated with the specified
     * source class.
     *
     * <p>The supplied context holder is used to enrich log entries with
     * contextual information.</p>
     *
     * @param source class owning the logger
     * @param contextHolder provider of contextual logging values
     */
    public Slf4jLogService(
            Class<?> source,
            LogContextHolder contextHolder
    ) {
        this.contextHolder = contextHolder;
        this.logger = LoggerFactory.getLogger(source);
    }

    /**
     * Writes a debug-level log entry including the current logging context.
     *
     * @param event stable identifier of the logged event
     * @param message human-readable log message
     */
    @Override
    public void debug(
            String event,
            String message
    ) {
        logger.debug(
                "event={} message={} context={}",
                event,
                message,
                context()
        );
    }

    /**
     * Writes an info-level log entry including the current logging context.
     *
     * @param event stable identifier of the logged event
     * @param message human-readable log message
     */
    @Override
    public void info(
            String event,
            String message
    ) {
        logger.info(
                "event={} message={} context={}",
                event,
                message,
                context()
        );
    }

    /**
     * Writes an info-level log entry including the current logging context
     * and additional structured attributes.
     *
     * @param event stable identifier of the logged event
     * @param message human-readable log message
     * @param attributes additional structured attributes associated with
     *                   the event
     */
    @Override
    public void info(
            String event,
            String message,
            Map<String, Object> attributes
    ) {
        logger.info(
                "event={} message={} context={} attributes={}",
                event,
                message,
                context(),
                attributes
        );
    }

    /**
     * Writes a warning-level log entry including the current logging context.
     *
     * @param event stable identifier of the logged event
     * @param message human-readable log message
     */
    @Override
    public void warn(
            String event,
            String message
    ) {
        logger.warn(
                "event={} message={} context={}",
                event,
                message,
                context()
        );
    }

    /**
     * Writes an error-level log entry including the current logging context.
     *
     * @param event stable identifier of the logged event
     * @param message human-readable log message
     */
    @Override
    public void error(
            String event,
            String message
    ) {
        logger.error(
                "event={} message={} context={}",
                event,
                message,
                context()
        );
    }

    /**
     * Writes an error-level log entry including the current logging context
     * and the associated exception.
     *
     * @param event stable identifier of the logged event
     * @param message human-readable log message
     * @param throwable exception associated with the failure
     */
    @Override
    public void error(
            String event,
            String message,
            Throwable throwable
    ) {
        logger.error(
                "event={} message={} context={}",
                event,
                message,
                context(),
                throwable
        );
    }

    private Map<String, String> context() {
        Map<String, String> context =
                new LinkedHashMap<>();

        putIfPresent(
                context,
                LogFields.CORRELATION_ID
        );

        putIfPresent(
                context,
                LogFields.TRACE_ID
        );

        putIfPresent(
                context,
                LogFields.SPAN_ID
        );

        putIfPresent(
                context,
                LogFields.APPLICATION
        );

        putIfPresent(
                context,
                LogFields.ENVIRONMENT
        );

        putIfPresent(
                context,
                LogFields.EVENT
        );

        putIfPresent(
                context,
                LogFields.OPERATION
        );

        putIfPresent(
                context,
                LogFields.STATUS
        );

        putIfPresent(
                context,
                LogFields.DURATION_MS
        );

        return context;
    }

    private void putIfPresent(
            Map<String, String> target,
            String key
    ) {
        String value =
                contextHolder.get(key);

        if (value != null && !value.isBlank()) {
            target.put(
                    key,
                    value
            );
        }
    }
}