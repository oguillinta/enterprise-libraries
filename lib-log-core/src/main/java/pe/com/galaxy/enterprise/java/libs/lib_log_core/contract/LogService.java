package pe.com.galaxy.enterprise.java.libs.lib_log_core.contract;

import java.util.Map;

/**
 * Defines standardized logging operations for enterprise applications.
 *
 * @since 1.0.0
 */
public interface LogService {

    /**
     * Writes a debug-level log entry.
     *
     * @param event stable identifier of the event
     * @param message human-readable log message
     */
    void debug(String event, String message);

    /**
     * Writes an info-level log entry.
     *
     * @param event stable identifier of the event
     * @param message human-readable log message
     */
    void info(String event, String message);

    /**
     * Writes an info-level log entry with additional structured attributes.
     *
     * @param event stable identifier of the event
     * @param message human-readable log message
     * @param attributes additional contextual attributes
     */
    void info(
            String event,
            String message,
            Map<String, Object> attributes
    );

    /**
     * Writes a warning-level log entry.
     *
     * @param event stable identifier of the event
     * @param message human-readable log message
     */
    void warn(String event, String message);

    /**
     * Writes an error-level log entry.
     *
     * @param event stable identifier of the event
     * @param message human-readable log message
     */
    void error(String event, String message);

    /**
     * Writes an error-level log entry including an exception.
     *
     * @param event stable identifier of the event
     * @param message human-readable log message
     * @param throwable associated exception
     */
    void error(
            String event,
            String message,
            Throwable throwable
    );
}