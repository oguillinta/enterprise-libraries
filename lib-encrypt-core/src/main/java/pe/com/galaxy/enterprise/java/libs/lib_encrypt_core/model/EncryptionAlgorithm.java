package pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.model;

/**
 * Defines the encryption algorithms supported by the library.
 *
 * <p>Each algorithm provides the cryptographic transformation required
 * by the Java Cryptography Architecture (JCA) to create the corresponding
 * {@link javax.crypto.Cipher} instance.</p>
 *
 * @since 0.0.1
 */
public enum EncryptionAlgorithm {

    /**
     * AES encryption using Galois/Counter Mode (GCM) without padding.
     */
    AES_256_GCM("AES/GCM/NoPadding");

    private final String transformation;

    EncryptionAlgorithm(String transformation) {
        this.transformation = transformation;
    }

    /**
     * Returns the JCA transformation associated with this algorithm.
     *
     * @return the cryptographic transformation
     */
    public String transformation() {
        return transformation;
    }
}