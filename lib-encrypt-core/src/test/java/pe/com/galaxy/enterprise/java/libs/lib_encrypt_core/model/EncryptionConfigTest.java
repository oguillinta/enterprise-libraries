package pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.model;

import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.exception.InvalidEncryptionKeyException;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link EncryptionConfig} encryption configuration.
 *
 * <p>Verifies AES-256 key validation, algorithm validation, and defensive
 * copying of encryption key material.</p>
 *
 * @since 0.0.1
 */
public class EncryptionConfigTest {

    private static final int AES_256_KEY_LENGTH = 32;

    @Test
    void shouldCreateEncryptionConfigWithValidKey() {
        byte[] key = createKey();

        EncryptionConfig config = new EncryptionConfig(
                key,
                EncryptionAlgorithm.AES_256_GCM
        );

        assertArrayEquals(key, config.key());

        assertEquals(
                EncryptionAlgorithm.AES_256_GCM,
                config.algorithm()
        );
    }

    @Test
    void shouldRejectNullEncryptionKey() {
        assertThrows(
                InvalidEncryptionKeyException.class,
                () -> new EncryptionConfig(
                        null,
                        EncryptionAlgorithm.AES_256_GCM
                )
        );
    }

    @Test
    void shouldRejectInvalidEncryptionKeyLength() {
        byte[] invalidKey = new byte[AES_256_KEY_LENGTH - 1];

        assertThrows(
                InvalidEncryptionKeyException.class,
                () -> new EncryptionConfig(
                        invalidKey,
                        EncryptionAlgorithm.AES_256_GCM
                )
        );
    }

    @Test
    void shouldRejectNullEncryptionAlgorithm() {
        assertThrows(
                NullPointerException.class,
                () -> new EncryptionConfig(
                        createKey(),
                        null
                )
        );
    }

    @Test
    void shouldDefensivelyCopyKeyDuringConstruction() {
        byte[] key = createKey();

        EncryptionConfig config = new EncryptionConfig(
                key,
                EncryptionAlgorithm.AES_256_GCM
        );

        byte expectedFirstByte = key[0];

        key[0] = 99;

        assertEquals(
                expectedFirstByte,
                config.key()[0]
        );
    }

    @Test
    void shouldReturnDefensivelyCopyOfEncryptionKey() {
        EncryptionConfig config = new EncryptionConfig(
                createKey(),
                EncryptionAlgorithm.AES_256_GCM
        );

        byte[] returnedKey = config.key();
        byte expectedFirstByte = returnedKey[0];

        returnedKey[0] = 99;

        assertEquals(
                expectedFirstByte,
                config.key()[0]
        );
    }


    private static byte[] createKey() {
        byte[] key = new byte[AES_256_KEY_LENGTH];

        Arrays.fill(
                key,
                (byte) 7
        );

        return key;
    }
}
