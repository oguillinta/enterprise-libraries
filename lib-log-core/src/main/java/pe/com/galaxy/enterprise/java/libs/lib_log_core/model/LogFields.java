package pe.com.galaxy.enterprise.java.libs.lib_log_core.model;

/**
 * Defines standardized field names used for logging and observability
 * context across applications.
 *
 * <p>Using common field names helps maintain consistent structured logs
 * and facilitates correlation, tracing, filtering, and analysis across
 * distributed services.</p>
 *
 * <p>This class cannot be instantiated.</p>
 *
 * @since 0.0.1
 */
public final class LogFields {

    public static final String CORRELATION_ID = "correlationId";
    public static final String TRACE_ID = "traceId";
    public static final String SPAN_ID = "spanId";
    public static final String APPLICATION = "application";
    public static final String ENVIRONMENT = "environment";
    public static final String EVENT = "event";
    public static final String OPERATION = "operation";
    public static final String STATUS = "status";
    public static final String DURATION_MS = "durationMs";

    private LogFields() {
    }
}