package pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.jackson;


import pe.com.galaxy.enterprise.java.libs.lib_mask_core.contract.MaskerService;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.model.MaskType;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

public final class MaskingValueSerializer
        extends StdSerializer<Object> {

    private final MaskerService maskerService;
    private final MaskType maskType;

    public MaskingValueSerializer(
            MaskerService maskerService,
            MaskType maskType) {

        super(Object.class);

        this.maskerService = maskerService;
        this.maskType = maskType;
    }

    @Override
    public void serialize(
            Object value,
            JsonGenerator generator,
            SerializationContext context)
            throws JacksonException {

        if (value == null) {
            generator.writeNull();
            return;
        }

        if (!(value instanceof CharSequence text)) {
            throw new IllegalArgumentException(
                    "@Masked can only be applied to CharSequence properties"
            );
        }

        String masked =
                maskerService.mask(
                        text.toString(),
                        maskType
                );

        generator.writeString(masked);
    }
}