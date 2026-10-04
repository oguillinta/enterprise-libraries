package pe.com.galaxy.enterprise.java.libs.lib_log_core.factory;

import pe.com.galaxy.enterprise.java.libs.lib_log_core.contract.LogContextHolder;
import pe.com.galaxy.enterprise.java.libs.lib_log_core.contract.LogService;
import pe.com.galaxy.enterprise.java.libs.lib_log_core.contract.LogServiceFactory;
import pe.com.galaxy.enterprise.java.libs.lib_log_core.service.Slf4jLogService;

/**
 * SLF4J-based implementation of {@link LogServiceFactory}.
 *
 * <p>The factory creates {@link Slf4jLogService} instances associated
 * with the requested source class and provides them with the configured
 * {@link LogContextHolder}.</p>
 *
 * @since 0.0.1
 */
public class Slf4jLogServiceFactory
        implements LogServiceFactory {

    private final LogContextHolder contextHolder;

    /**
     * Creates a new SLF4J log service factory.
     *
     * @param contextHolder context provider used to enrich generated
     *                      log services
     */
    public Slf4jLogServiceFactory(
            LogContextHolder contextHolder
    ) {
        this.contextHolder = contextHolder;
    }

    /**
     * Creates an SLF4J-backed logger associated with the specified class.
     *
     * @param source class owning the logger
     * @return a new SLF4J-backed logging service
     */
    @Override
    public LogService getLogger(Class<?> source) {
        return new Slf4jLogService(
                source,
                contextHolder
        );
    }
}