package pe.com.galaxy.enterprise.java.libs.lib_mapper_core.config;

import org.mapstruct.*;

/**
 * Shared MapStruct configuration used across enterprise applications
 *
 * @since 1.0.0
 */
@MapperConfig(
        componentModel = MappingConstants.ComponentModel.SPRING,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS
)
public interface CoreMapperConfig {
}
