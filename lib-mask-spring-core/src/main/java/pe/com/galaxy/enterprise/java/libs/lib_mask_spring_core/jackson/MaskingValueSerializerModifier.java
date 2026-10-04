package pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.jackson;

import pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.annotation.Masked;
import pe.com.galaxy.enterprise.java.libs.lib_mask_core.contract.MaskerService;
import tools.jackson.databind.BeanDescription;
import tools.jackson.databind.SerializationConfig;
import tools.jackson.databind.ser.BeanPropertyWriter;
import tools.jackson.databind.ser.ValueSerializerModifier;

import java.util.List;

public final class MaskingValueSerializerModifier
        extends ValueSerializerModifier {

    private final MaskerService maskerService;

    public MaskingValueSerializerModifier(
            MaskerService maskerService) {

        this.maskerService = maskerService;
    }

    @Override
    public List<BeanPropertyWriter> changeProperties(
            SerializationConfig config,
            BeanDescription.Supplier beanDescription,
            List<BeanPropertyWriter> properties) {

        for (BeanPropertyWriter property : properties) {

            Masked masked =
                    property.getAnnotation(Masked.class);

            if (masked == null) {
                continue;
            }

            property.assignSerializer(
                    new MaskingValueSerializer(
                            maskerService,
                            masked.value()
                    )
            );
        }

        return properties;
    }
}