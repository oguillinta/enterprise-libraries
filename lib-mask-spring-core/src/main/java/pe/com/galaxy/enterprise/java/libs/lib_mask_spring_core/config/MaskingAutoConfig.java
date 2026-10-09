package pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Import;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.contract.MaskerService;


/**
 * Spring Boot auto-configuration for masking support.
 *
 * <p>Automatically loads the masking Spring configuration when the
 * masking infrastructure is available on the classpath.</p>
 *
 * @since 1.3.0
 */
@AutoConfiguration
@ConditionalOnClass(MaskerService.class)
@Import(MaskingSpringConfig.class)
public class MaskingAutoConfig {
}