package pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.jackson;

import pe.com.galaxy.enterprise.java.libs.lib_mask_core.contract.MaskerService;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskingOptions;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskType;
import pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.annotation.Masked;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

import java.util.Objects;

/**
 * Jackson serializer that masks String values using {@link MaskerService}.
 *
 * @since 1.0.0
 */
public final class MaskingValueSerializer
        extends StdSerializer<Object> {

    private final MaskerService maskerService;
    private final MaskType maskType;
    private final MaskingOptions maskingOptions;

    /**
     * Creates a serializer from the supplied masking annotation.
     *
     * @param maskerService masking service
     * @param masked masking configuration
     */
    public MaskingValueSerializer(
            MaskerService maskerService,
            Masked masked
    ) {
        super(Object.class);

        this.maskerService =
                Objects.requireNonNull(
                        maskerService,
                        "Masker service cannot be null"
                );

        Objects.requireNonNull(
                masked,
                "Masked annotation cannot be null"
        );

        this.maskType =
                masked.value();

        this.maskingOptions =
                new MaskingOptions(
                        masked.visiblePrefix(),
                        masked.visibleSuffix(),
                        masked.maskCharacter()
                );
    }

    /**
     * Masks and writes the serialized value.
     *
     * @param value value to serialize
     * @param generator JSON generator
     * @param context serialization context
     *
     * @throws JacksonException if serialization fails
     * @throws IllegalArgumentException if the value is not a String
     */
    @Override
    public void serialize(
            Object value,
            JsonGenerator generator,
            SerializationContext context
    ) throws JacksonException {

        if (!(value instanceof String stringValue)) {
            throw new IllegalArgumentException(
                    "@Masked can only be applied to String properties"
            );
        }

        String maskedValue =
                maskerService.mask(
                        stringValue,
                        maskType,
                        maskingOptions
                );

        generator.writeString(
                maskedValue
        );
    }
}