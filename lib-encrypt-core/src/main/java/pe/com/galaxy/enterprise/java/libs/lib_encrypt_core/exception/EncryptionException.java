package pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.exception;

/**
 * Base exception for failures occurring during encryption
 * or decryption operations.
 *
 * @since 1.0.0
 */
public class EncryptionException extends RuntimeException {

    public EncryptionException(String message) {
        super(message);
    }

    public EncryptionException(
            String message,
            Throwable cause
    ) {
        super(message, cause);
    }
}