package pe.com.galaxy.enterprise.java.libs.lib_log_core.contract;

/**
 * Defines a contract for managing contextual values associated with the
 * current logging execution context.
 *
 * <p>Contextual values can be used to enrich log entries with information
 * such as correlation identifiers, trace identifiers, application names,
 * operations, and execution status.</p>
 *
 * <p>Implementations may store the context using mechanisms such as
 * SLF4J MDC or another context propagation strategy.</p>
 *
 * @since 0.0.1
 */
public interface LogContextHolder {

    /**
     * Associates the specified value with the given context key.
     *
     * @param key context key
     * @param value context value
     */
    void put(String key, String value);

    /**
     * Returns the value associated with the specified context key.
     *
     * @param key context key
     * @return the associated value, or {@code null} if no value is present
     */
    String get(String key);

    /**
     * Removes the value associated with the specified context key.
     *
     * @param key context key to remove
     */
    void remove(String key);

    /**
     * Removes all values from the current logging context.
     */
    void clear();
}