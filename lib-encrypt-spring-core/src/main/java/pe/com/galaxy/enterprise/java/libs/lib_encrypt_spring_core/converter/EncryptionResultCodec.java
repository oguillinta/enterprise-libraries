package pe.com.galaxy.enterprise.java.libs.lib_encrypt_spring_core.converter;

import pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.model.EncryptionResult;

public final class EncryptionResultCodec {

    private static final String VERSION = "v1";
    private static final String SEPARATOR = ":";

    private EncryptionResultCodec() {
    }

    public static String encode(
            EncryptionResult result
    ) {
        return VERSION
                + SEPARATOR
                + result.initializationVector()
                + SEPARATOR
                + result.cipherText();
    }

    public static EncryptionResult decode(
            String value
    ) {
        String[] parts = value.split(
                SEPARATOR,
                3
        );

        if (parts.length != 3 ||
                !VERSION.equals(parts[0])) {

            throw new IllegalArgumentException(
                    "Invalid encrypted value format"
            );
        }

        return new EncryptionResult(
                parts[2],
                parts[1]
        );
    }
}
