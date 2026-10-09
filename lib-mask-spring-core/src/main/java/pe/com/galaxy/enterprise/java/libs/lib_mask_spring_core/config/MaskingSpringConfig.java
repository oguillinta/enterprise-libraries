package pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.contract.MaskerService;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.handler.MaskingStrategyHandler;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy.AccountMaskingStrategyImpl;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy.CardMaskingStrategyImpl;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy.DocumentMaskingStrategyImpl;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy.EmailMaskingStrategyImpl;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy.PersonNameMaskingStrategyImpl;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy.PhoneMaskingStrategyImpl;
import pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.jackson.MaskingValueSerializerModifier;
import tools.jackson.databind.JacksonModule;
import tools.jackson.databind.module.SimpleModule;

import java.util.List;

/**
 * Configures masking services and Jackson integration for Spring applications.
 *
 * @since 1.0.0
 */
@Configuration(proxyBeanMethods = false)
public class MaskingSpringConfig {

    /**
     * Creates the default masking service.
     *
     * @return masking service
     */
    @Bean
    public MaskerService maskerService() {
        return new MaskingStrategyHandler(
                List.of(
                        new AccountMaskingStrategyImpl(),
                        new CardMaskingStrategyImpl(),
                        new DocumentMaskingStrategyImpl(),
                        new EmailMaskingStrategyImpl(),
                        new PhoneMaskingStrategyImpl(),
                        new PersonNameMaskingStrategyImpl()
                )
        );
    }

    /**
     * Creates the Jackson module used for annotation-driven masking.
     *
     * @param maskerService masking service
     * @return Jackson masking module
     */
    @Bean
    public JacksonModule maskingJacksonModule(
            MaskerService maskerService
    ) {
        SimpleModule module =
                new SimpleModule();

        module.setSerializerModifier(
                new MaskingValueSerializerModifier(
                        maskerService
                )
        );

        return module;
    }
}