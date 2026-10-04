package pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.exception;

/**
 * Indicates that an encryption key does not satisfy the
 * requirements of the encryption algorithm.
 *
 * @since 1.0.0
 */
public class InvalidEncryptionKeyException
        extends EncryptionException {

    public InvalidEncryptionKeyException(String message) {
        super(message);
    }
}