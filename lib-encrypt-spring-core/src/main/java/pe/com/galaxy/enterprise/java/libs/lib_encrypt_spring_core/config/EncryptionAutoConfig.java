package pe.com.galaxy.enterprise.java.libs.lib_encrypt_spring_core.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.contract.EncryptionService;
import pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.model.EncryptionAlgorithm;
import pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.model.EncryptionConfig;
import pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.service.AesGcmEncryptionService;


import java.util.Base64;

/**
 * Spring Boot auto-configuration for encryption support.
 *
 * @since 1.1.0
 */
@AutoConfiguration
@ConditionalOnClass(EncryptionService.class)
@EnableConfigurationProperties(EncryptionProperties.class)
public class EncryptionAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public EncryptionService encryptionService(
            EncryptionProperties properties
    ) {
        byte[] key =
                Base64.getDecoder()
                        .decode(properties.getKey());

        EncryptionConfig config =
                new EncryptionConfig(
                        key,
                        EncryptionAlgorithm.AES_256_GCM
                );

        return new AesGcmEncryptionService(config);
    }
}