package pe.com.galaxy.enterprise.java.libs.lib_log_core.context;

import org.slf4j.MDC;
import pe.com.galaxy.enterprise.java.libs.lib_log_core.contract.LogContextHolder;

/**
 * SLF4J MDC-based implementation of {@link LogContextHolder}.
 *
 * <p>This implementation stores contextual logging values in the
 * SLF4J Mapped Diagnostic Context (MDC), allowing compatible logging
 * implementations to include those values in log entries.</p>
 *
 * <p>Null keys and values supplied to {@link #put(String, String)}
 * are ignored.</p>
 *
 * @since 0.0.1
 */
public final class MdcLogContextHolder
        implements LogContextHolder {

    /**
     * Stores a value in the current SLF4J MDC context.
     *
     * <p>If either {@code key} or {@code value} is {@code null},
     * the operation is ignored.</p>
     *
     * @param key context key
     * @param value context value
     */
    @Override
    public void put(String key, String value) {
        if (key != null && value != null) {
            MDC.put(key, value);
        }
    }

    /**
     * Returns the value associated with the specified MDC key.
     *
     * @param key context key
     * @return the associated value, or {@code null} if no value exists
     */
    @Override
    public String get(String key) {
        return MDC.get(key);
    }

    /**
     * Removes the specified key from the current MDC context.
     *
     * @param key context key to remove
     */
    @Override
    public void remove(String key) {
        MDC.remove(key);
    }

    /**
     * Removes all values from the current MDC context.
     */
    @Override
    public void clear() {
        MDC.clear();
    }
}