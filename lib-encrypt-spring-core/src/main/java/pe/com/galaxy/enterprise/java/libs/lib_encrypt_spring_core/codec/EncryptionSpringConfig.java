package pe.com.galaxy.enterprise.java.libs.lib_encrypt_spring_core.codec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.contract.EncryptionService;
import pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.model.EncryptionAlgorithm;
import pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.model.EncryptionConfig;
import pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.service.AesGcmEncryptionService;

import java.util.Base64;

@Configuration
public class EncryptionSpringConfig {

    @Bean
    public EncryptionService encryptionService(
         @Value("${security.encryption.key}") String encodedKey
    ) {
        byte[] key = Base64.getDecoder().decode(encodedKey);

        EncryptionConfig config =
                new EncryptionConfig(
                        key,
                        EncryptionAlgorithm.AES_256_GCM
                );

        return new AesGcmEncryptionService(
                config
        );

    }
}
