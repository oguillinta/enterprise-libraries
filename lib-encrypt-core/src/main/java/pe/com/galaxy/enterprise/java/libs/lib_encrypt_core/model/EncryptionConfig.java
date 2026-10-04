package pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.model;

import pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.exception.InvalidEncryptionKeyException;

import java.util.Objects;

/**
 * Defines the configuration required to perform encryption operations.
 *
 * <p>The configuration contains the encryption key and the algorithm
 * that should be used by an {@code EncryptionService} implementation.</p>
 *
 * <p>The encryption key must contain exactly 32 bytes, corresponding
 * to a 256-bit AES key. The provided key is defensively copied during
 * construction and when accessed in order to preserve the immutability
 * of this configuration.</p>
 *
 * @param key the 256-bit encryption key
 * @param algorithm the encryption algorithm to use
 * @since 0.0.1
 */
public record EncryptionConfig(
        byte[] key,
        EncryptionAlgorithm algorithm
) {

    private static final int AES_256_KEY_LENGTH = 32;

    private static final String ENCRYPTION_ALGORITHM_REQUIRED_MESSAGE =
            "Encryption algorithm cannot be null";

    private static final String ENCRYPTION_KEY_REQUIRED_MESSAGE =
            "Encryption key cannot be null";

    private static final String INVALID_AES_256_KEY_LENGTH_MESSAGE =
            "AES-256 encryption key must contain exactly 32 bytes";

    /**
     * Creates and validates an encryption configuration.
     *
     * @throws NullPointerException if {@code algorithm} is {@code null}
     * @throws InvalidEncryptionKeyException if {@code key} is {@code null}
     *         or does not contain exactly 32 bytes
     */
    public EncryptionConfig {
        Objects.requireNonNull(
                algorithm,
                ENCRYPTION_ALGORITHM_REQUIRED_MESSAGE
        );

        if (key == null) {
            throw new InvalidEncryptionKeyException(
                    ENCRYPTION_KEY_REQUIRED_MESSAGE
            );
        }

        if (key.length != AES_256_KEY_LENGTH) {
            throw new InvalidEncryptionKeyException(
                    INVALID_AES_256_KEY_LENGTH_MESSAGE
            );
        }

        key = key.clone();
    }

    /**
     * Returns a defensive copy of the encryption key.
     *
     * @return a copy of the configured encryption key
     */
    @Override
    public byte[] key() {
        return key.clone();
    }
}