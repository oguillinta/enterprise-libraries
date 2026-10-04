package pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.service;

import pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.contract.EncryptionService;
import pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.exception.EncryptionException;
import pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.model.EncryptionConfig;
import pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.model.EncryptionResult;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;

/**
 * AES-256-GCM implementation of {@link EncryptionService}.
 *
 * <p>This service encrypts and decrypts text values using the Advanced
 * Encryption Standard (AES) in Galois/Counter Mode (GCM).</p>
 *
 * <p>A new cryptographically secure random initialization vector (IV) is
 * generated for every encryption operation. The implementation uses a
 * 12-byte IV and a 128-bit authentication tag.</p>
 *
 * <p>Plain text values are encoded using UTF-8. The resulting ciphertext
 * and initialization vector are represented as Base64-encoded strings
 * through {@link EncryptionResult}.</p>
 *
 * <p>The encryption key and cryptographic transformation are obtained from
 * the provided {@link EncryptionConfig}.</p>
 *
 * @since 0.0.1
 */
public final class AesGcmEncryptionService
        implements EncryptionService {

    private static final String KEY_ALGORITHM = "AES";

    private static final int IV_LENGTH_BYTES = 12;
    private static final int AUTHENTICATION_TAG_LENGTH_BITS = 128;

    private static final String ENCRYPTION_CONFIG_REQUIRED_MESSAGE =
            "Encryption configuration cannot be null";

    private static final String PLAIN_TEXT_REQUIRED_MESSAGE =
            "Plain text cannot be null";

    private static final String ENCRYPTED_VALUE_REQUIRED_MESSAGE =
            "Encrypted value cannot be null";

    private static final String ENCRYPTION_FAILED_MESSAGE =
            "Unable to encrypt value";

    private static final String DECRYPTION_FAILED_MESSAGE =
            "Unable to decrypt value";

    private final String transformation;
    private final SecretKey secretKey;
    private final SecureRandom secureRandom;

    /**
     * Creates a new AES-GCM encryption service using the specified
     * encryption configuration.
     *
     * <p>The configured key is converted into an AES {@link SecretKey},
     * while the configured algorithm provides the JCA transformation
     * used for encryption and decryption operations.</p>
     *
     * @param config encryption configuration containing the key and algorithm
     * @throws NullPointerException if {@code config} is {@code null}
     */
    public AesGcmEncryptionService(
            EncryptionConfig config
    ) {
        Objects.requireNonNull(
                config,
                ENCRYPTION_CONFIG_REQUIRED_MESSAGE
        );

        this.secretKey = new SecretKeySpec(
                config.key(),
                KEY_ALGORITHM
        );

        this.transformation =
                config.algorithm().transformation();

        this.secureRandom = new SecureRandom();
    }

    /**
     * Encrypts the specified plain text value using AES-GCM.
     *
     * <p>A new random 12-byte initialization vector is generated for every
     * invocation. The plain text is encoded as UTF-8 before encryption.</p>
     *
     * <p>The encrypted value and initialization vector are Base64 encoded
     * and returned together as an {@link EncryptionResult}.</p>
     *
     * @param plainText text value to encrypt
     * @return the encrypted value together with its initialization vector
     * @throws NullPointerException if {@code plainText} is {@code null}
     * @throws EncryptionException if the encryption operation cannot be completed
     */
    @Override
    public EncryptionResult encrypt(
            String plainText
    ) {
        Objects.requireNonNull(
                plainText,
                PLAIN_TEXT_REQUIRED_MESSAGE
        );

        try {
            byte[] iv = new byte[IV_LENGTH_BYTES];
            secureRandom.nextBytes(iv);

            Cipher cipher =
                    Cipher.getInstance(transformation);

            GCMParameterSpec spec =
                    new GCMParameterSpec(
                            AUTHENTICATION_TAG_LENGTH_BITS,
                            iv
                    );

            cipher.init(
                    Cipher.ENCRYPT_MODE,
                    secretKey,
                    spec
            );

            byte[] encrypted =
                    cipher.doFinal(
                            plainText.getBytes(
                                    StandardCharsets.UTF_8
                            )
                    );

            return new EncryptionResult(
                    Base64.getEncoder()
                            .encodeToString(encrypted),

                    Base64.getEncoder()
                            .encodeToString(iv)
            );

        } catch (GeneralSecurityException exception) {
            throw new EncryptionException(
                    ENCRYPTION_FAILED_MESSAGE,
                    exception
            );
        }
    }

    /**
     * Decrypts the specified encrypted value using AES-GCM.
     *
     * <p>The ciphertext and initialization vector contained in the
     * {@link EncryptionResult} are decoded from Base64 before performing
     * the decryption operation.</p>
     *
     * <p>The decrypted bytes are converted back to a string using UTF-8.</p>
     *
     * @param encryptedValue encrypted value together with the initialization vector
     * @return the decrypted plain text
     * @throws NullPointerException if {@code encryptedValue} is {@code null}
     * @throws EncryptionException if the encrypted value cannot be decoded
     *         or decrypted
     */
    @Override
    public String decrypt(
            EncryptionResult encryptedValue
    ) {
        Objects.requireNonNull(
                encryptedValue,
                ENCRYPTED_VALUE_REQUIRED_MESSAGE
        );

        try {
            byte[] encrypted =
                    Base64.getDecoder()
                            .decode(
                                    encryptedValue.cipherText()
                            );

            byte[] iv =
                    Base64.getDecoder()
                            .decode(
                                    encryptedValue.initializationVector()
                            );

            Cipher cipher =
                    Cipher.getInstance(transformation);

            GCMParameterSpec spec =
                    new GCMParameterSpec(
                            AUTHENTICATION_TAG_LENGTH_BITS,
                            iv
                    );

            cipher.init(
                    Cipher.DECRYPT_MODE,
                    secretKey,
                    spec
            );

            byte[] decrypted =
                    cipher.doFinal(encrypted);

            return new String(
                    decrypted,
                    StandardCharsets.UTF_8
            );

        } catch (
                GeneralSecurityException |
                IllegalArgumentException exception
        ) {
            throw new EncryptionException(
                    DECRYPTION_FAILED_MESSAGE,
                    exception
            );
        }
    }
}