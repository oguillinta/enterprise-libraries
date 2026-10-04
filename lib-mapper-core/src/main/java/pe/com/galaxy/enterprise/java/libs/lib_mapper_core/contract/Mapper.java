package pe.com.galaxy.enterprise.java.libs.lib_mapper_core.contract;

import java.util.List;

/**
 * Defines a one-way mapping contract between a source and target type
 *
 * @param <S> source type
 * @param <T> target type
 * @since 1.0.0
 */

public interface Mapper<S, T> {
    /**
     * Maps the given source object to the target type.
     * @param source object to map
     * @return mapped target object
     */
    T map(S source);

    List<T> map(List<S> source);
}
