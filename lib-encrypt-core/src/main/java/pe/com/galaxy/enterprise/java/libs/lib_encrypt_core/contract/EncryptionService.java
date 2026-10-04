package pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.contract;

import pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.model.EncryptionResult;

/**
 * Defines operations for encrypting and decrypting sensitive information.
 *
 * @since 1.0.0
 */
public interface EncryptionService {

    /**
     * Encrypts the supplied plain-text value.
     *
     * @param plainText value to encrypt
     * @return the encrypted result
     */
    EncryptionResult encrypt(String plainText);

    /**
     * Decrypts an encrypted value.
     *
     * @param encryptedValue encrypted value to decrypt
     * @return the original plain-text value
     */
    String decrypt(EncryptionResult encryptedValue);
}
