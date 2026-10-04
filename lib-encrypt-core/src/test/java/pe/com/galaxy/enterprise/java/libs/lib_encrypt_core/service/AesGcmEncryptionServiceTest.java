package pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.exception.EncryptionException;
import pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.model.EncryptionAlgorithm;
import pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.model.EncryptionConfig;
import pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.model.EncryptionResult;

import java.util.Arrays;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link AesGcmEncryptionService}.
 *
 * <p>Verifies AES-256-GCM encryption and decryption, random initialization
 * vector generation, UTF-8 support, input validation, and authenticated
 * decryption failure scenarios.</p>
 *
 * @since 0.0.1
 */
public class AesGcmEncryptionServiceTest {
    private static final int AES_256_KEY_LENGTH = 32;
    private static final int GCM_IV_LENGTH_BYTES = 12;

    private AesGcmEncryptionService encryptionService;

    @BeforeEach
    void setUp() {
            EncryptionConfig config = new EncryptionConfig(
                    createKey((byte) 7),
            EncryptionAlgorithm.AES_256_GCM);

            encryptionService = new AesGcmEncryptionService(config);
    }

    @Test
    void shouldEncryptAndDecryptValue() {
        String plainText = "Sensitive customer information";

        EncryptionResult encrypted = encryptionService.encrypt(plainText);

        String decrypted = encryptionService.decrypt(encrypted);

        assertEquals(
                plainText,
                decrypted
        );
    }

    @Test
    void shouldEncryptUtf8Value() {
        String plainText = "Transferencia de S/. 1000 - Perú";

        EncryptionResult encrypted = encryptionService.encrypt(plainText);

        String decrypted = encryptionService.decrypt(encrypted);

        assertEquals(
                plainText,
                decrypted
        );
    }

    @Test
    void shouldNotExposePlainTextAsCipherText() {
        String plainText = "Sensitive value";

        EncryptionResult result = encryptionService.encrypt(plainText);

        assertNotEquals(
                plainText,
                result.cipherText()
        );
    }

    @Test
    void shouldGenerateExpectedIvLength() {
        EncryptionResult result = encryptionService.encrypt("Sensitive value");

        byte[] iv = Base64.getDecoder().decode(
                result.initializationVector()
        );

        assertEquals(
                GCM_IV_LENGTH_BYTES,
                iv.length
        );
    }

    @Test
    void shouldGenerateDifferentIvForEachEncryption() {
        String plainText = "Same sensitive value";

        EncryptionResult first = encryptionService.encrypt(plainText);

        EncryptionResult second = encryptionService.encrypt(plainText);

        assertNotEquals(
                first.initializationVector(),
                second.initializationVector()
        );
    }

    @Test
    void shouldProduceDifferentCipherTextForSamePlainText() {
        String plainText = "Same sensitive value";

        EncryptionResult first = encryptionService.encrypt(plainText);

        EncryptionResult second = encryptionService.encrypt(plainText);

        assertNotEquals(
                first.cipherText(),
                second.cipherText()
        );
    }

    @Test
    void shouldRejectNullPlainText() {
        assertThrows(
                NullPointerException.class,
                () -> encryptionService.encrypt(null)
        );
    }

    @Test
    void shouldRejectInvalidBase64CipherTest() {
        EncryptionResult valid = encryptionService.encrypt("Sensitive value");

        EncryptionResult invalid = new EncryptionResult(
                "%%%INVALID_BASE64%%%",
                valid.initializationVector()
        );

        assertThrows(
                EncryptionException.class,
                () -> encryptionService.decrypt(invalid)
        );
    }

    @Test
    void shouldRejectDecryptionUsingDifferentKey() {
        EncryptionResult encrypted = encryptionService.encrypt("Sensitive value");

        EncryptionConfig differentConfig = new EncryptionConfig(
                createKey((byte) 15),
                EncryptionAlgorithm.AES_256_GCM
        );

        AesGcmEncryptionService differentService = new AesGcmEncryptionService(differentConfig);

        assertThrows(
                EncryptionException.class,
                () -> differentService.decrypt(encrypted)
        );
    }

    private static byte[] createKey(byte value) {
        byte[] key = new byte[AES_256_KEY_LENGTH];

        Arrays.fill(key, value);

        return key;
    }


}
