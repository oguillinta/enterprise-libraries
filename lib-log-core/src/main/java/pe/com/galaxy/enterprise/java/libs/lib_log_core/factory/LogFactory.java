package pe.com.galaxy.enterprise.java.libs.lib_log_core.factory;

import pe.com.galaxy.enterprise.java.libs.lib_log_core.context.MdcLogContextHolder;
import pe.com.galaxy.enterprise.java.libs.lib_log_core.contract.LogContextHolder;
import pe.com.galaxy.enterprise.java.libs.lib_log_core.contract.LogService;
import pe.com.galaxy.enterprise.java.libs.lib_log_core.service.Slf4jLogService;

/**
 * Provides convenient access to {@link LogService} instances.
 *
 * <p>This factory uses an MDC-backed logging context and does not require
 * dependency injection or a framework container.</p>
 *
 * @since 0.0.1
 */
public final class LogFactory {

    private static final LogContextHolder CONTEXT_HOLDER =
            new MdcLogContextHolder();

    private LogFactory() {
    }

    /**
     * Returns a logging service associated with the specified source class.
     *
     * @param source class owning the logger
     * @return logging service associated with the source class
     */
    public static LogService getLogger(
            Class<?> source
    ) {
        return new Slf4jLogService(
                source,
                CONTEXT_HOLDER
        );
    }

    /**
     * Returns the shared logging context holder.
     *
     * @return logging context holder
     */
    public static LogContextHolder context() {
        return CONTEXT_HOLDER;
    }
}