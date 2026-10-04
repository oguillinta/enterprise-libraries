package pe.com.galaxy.enterprise.java.libs.lib_log_core.contract;

/**
 * Defines a factory for creating {@link LogService} instances associated
 * with application source classes.
 *
 * <p>The abstraction allows consumers to obtain loggers without depending
 * directly on the underlying logging framework.</p>
 *
 * @since 0.0.1
 */
public interface LogServiceFactory {

    /**
     * Creates a logger associated with the specified source class.
     *
     * @param source class for which the logger should be created
     * @return a logging service associated with the source class
     */
    LogService getLogger(Class<?> source);
}