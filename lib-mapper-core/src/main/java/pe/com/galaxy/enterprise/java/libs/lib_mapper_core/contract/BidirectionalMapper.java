package pe.com.galaxy.enterprise.java.libs.lib_mapper_core.contract;

import java.util.List;

/**
 * Defines a bidirectional mapping contract between two types.
 * @param <D> domain type
 * @param <E> external or persistence type
 * @since 1.0.0
 */
public interface BidirectionalMapper<D, E> {
    /**
     * Maps the domain object to the external representation
     *
     * @param domain domain object
     * @return mapped external representation
     */
    E toExternal(D domain);

    /**
     * Maps the external representation to the domain type.
     * @param external external representation
     * @return mapped domain object
     */
    D toDomain(E external);

    List<E> toExternal(List<D> domain);
    List<D> toDomain(List<E> external);
}
