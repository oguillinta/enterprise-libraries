package pe.com.galaxy.enterprise.java.libs.lib_mapper_core.pagination;

import java.util.List;

/**
 * Represents a framework-neutral paginated result.
 *
 * <p>This type encapsulates the page content together with pagination
 * metadata without depending on a specific persistence or web framework.</p>
 *
 * @param content the elements contained in the current page
 * @param page the zero-based page index
 * @param size the configured page size
 * @param totalElements the total number of available elements
 * @param totalPages the total number of available pages
 * @param <T> the type of elements contained in the page
 * @since 1.0.0
 */
public record PageResult<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last
) {
}