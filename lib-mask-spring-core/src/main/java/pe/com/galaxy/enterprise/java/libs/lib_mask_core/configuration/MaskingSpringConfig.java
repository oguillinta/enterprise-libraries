package pe.com.galaxy.enterprise.java.libs.lib_mask_core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.contract.MaskerService;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.handler.MaskingStrategyHandler;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.jackson.MaskingValueSerializerModifier;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.strategy.*;
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
                      new PhoneMaskingStrategyImpl()
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
