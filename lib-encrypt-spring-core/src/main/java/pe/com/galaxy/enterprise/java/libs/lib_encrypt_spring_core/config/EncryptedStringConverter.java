package pe.com.galaxy.enterprise.java.libs.lib_encrypt_spring_core.config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.contract.EncryptionService;
import pe.com.galaxy.enterprise.java.libs.lib_encrypt_spring_core.converter.EncryptionResultCodec;
import pe.com.galaxy.enterprise.java.libs.lib_encrypt_core.model.EncryptionResult;

@Converter
public class EncryptedStringConverter
        implements AttributeConverter<String, String> {

    private final EncryptionService encryptionService;

    public EncryptedStringConverter(
            EncryptionService encryptionService
    ) {
        this.encryptionService = encryptionService;
    }

    @Override
    public String convertToDatabaseColumn(
            String attribute
    ) {
        if (attribute == null) {
            return null;
        }

        EncryptionResult encrypted =
                encryptionService.encrypt(attribute);

        return EncryptionResultCodec.encode(encrypted);
    }

    @Override
    public String convertToEntityAttribute(
            String databaseValue
    ) {
        if (databaseValue == null) {
            return null;
        }

        EncryptionResult encrypted =
                EncryptionResultCodec.decode(
                        databaseValue
                );

        return encryptionService.decrypt(encrypted);
    }
}