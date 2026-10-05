package pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.jackson;

import pe.com.galaxy.enterprise.java.libs.lib_mask_core.contract.MaskerService;
import pe.com.galaxy.enterprise.java.libs.lib_mask_spring_core.annotation.Masked;
import tools.jackson.databind.BeanDescription;
import tools.jackson.databind.SerializationConfig;
import tools.jackson.databind.ser.BeanPropertyWriter;
import tools.jackson.databind.ser.ValueSerializerModifier;

import java.util.List;
import java.util.Objects;

/**
 * Assigns {@link MaskingValueSerializer} to properties annotated with
 * {@link Masked}.
 *
 * @since 1.0.0
 */
public final class MaskingValueSerializerModifier
        extends ValueSerializerModifier {

    private final MaskerService maskerService;

    /**
     * Creates the serializer modifier.
     *
     * @param maskerService masking service
     */
    public MaskingValueSerializerModifier(
            MaskerService maskerService
    ) {
        this.maskerService =
                Objects.requireNonNull(
                        maskerService,
                        "Masker service cannot be null"
                );
    }

    /**
     * Configures masking serializers for annotated String properties.
     *
     * @param config serialization configuration
     * @param beanDescription bean metadata
     * @param beanProperties serialized properties
     * @return configured properties
     */
    @Override
    public List<BeanPropertyWriter> changeProperties(
            SerializationConfig config,
            BeanDescription.Supplier beanDescription,
            List<BeanPropertyWriter> beanProperties
    ) {

        for (BeanPropertyWriter property :
                beanProperties) {

            Masked masked =
                    property.getAnnotation(
                            Masked.class
                    );

            if (masked == null) {
                continue;
            }

            Class<?> propertyType =
                    property.getType()
                            .getRawClass();

            if (!String.class.equals(
                    propertyType
            )) {
                throw new IllegalStateException(
                        "@Masked can only be applied to String properties: "
                                + property.getName()
                );
            }

            property.assignSerializer(
                    new MaskingValueSerializer(
                            maskerService,
                            masked
                    )
            );
        }

        return beanProperties;
    }
}