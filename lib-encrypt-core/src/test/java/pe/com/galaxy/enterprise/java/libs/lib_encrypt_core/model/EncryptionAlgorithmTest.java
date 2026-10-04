package pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the {@link EncryptionAlgorithm} enumeration.
 *
 * <p>Verifies the cryptographic transformation associated with supported
 * encryption algorithms.</p>
 *
 * @since 0.0.1
 */
public class EncryptionAlgorithmTest {

    @Test
    void shouldReturnAes256GcmTransformation() {
        assertEquals(
                "AES/GCM/NoPadding",
                EncryptionAlgorithm.AES_256_GCM.transformation()
        );
    }
}
