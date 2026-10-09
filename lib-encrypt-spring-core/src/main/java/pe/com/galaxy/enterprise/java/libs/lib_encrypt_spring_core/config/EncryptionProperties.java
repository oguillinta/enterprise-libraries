package pe.com.galaxy.enterprise.java.libs.lib_encrypt_spring_core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for encryption support.
 *
 * @since 1.1.0
 */
@ConfigurationProperties(prefix = "security.encryption")
public class EncryptionProperties {

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}