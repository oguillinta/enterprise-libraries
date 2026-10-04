package pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.contract.MaskerService;
import pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.handler.MaskingStrategyHandler;
import pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.jackson.MaskingValueSerializerModifier;
import pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.strategy.*;
import tools.jackson.databind.JacksonModule;
import tools.jackson.databind.module.SimpleModule;

import java.util.List;

@Configuration
public class MaskingSpringConfig {
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

    @Bean
    public JacksonModule maskingJacksonModule(
            MaskerService maskerService) {

        SimpleModule module =
                new SimpleModule("masking-module");

        module.setSerializerModifier(
                new MaskingValueSerializerModifier(
                        maskerService
                )
        );

        return module;
    }

}
