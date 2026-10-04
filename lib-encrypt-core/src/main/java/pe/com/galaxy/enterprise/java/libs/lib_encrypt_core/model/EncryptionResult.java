package pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.model;

/**
 * Represents the result of an encryption operation.
 *
 * <p>The encrypted value and initialization vector are represented as
 * Base64-encoded strings so they can be safely stored or transported
 * using text-based formats.</p>
 *
 * <p>The initialization vector must be preserved together with the
 * ciphertext because it is required to decrypt the encrypted value.</p>
 *
 * @param cipherText the Base64-encoded encrypted value
 * @param initializationVector the Base64-encoded initialization vector
 * @since 0.0.1
 */
public record EncryptionResult(
        String cipherText,
        String initializationVector
) {
}