package pe.com.galaxy.enterprise.java.libs.lib_mapper_core.pagination;

/**
 * Defines a contract for mapping paginated results from one element type
 * to another while preserving the pagination metadata.
 *
 * <p>Only the page content is transformed. Pagination information such as
 * page number, size, total elements and total pages should remain unchanged.</p>
 *
 * @param <S> the source element type
 * @param <T> the target element type
 * @since 1.0.0
 */
public interface PageMapper<S, T> {

    /**
     * Maps a paginated source result to a paginated target result.
     *
     * @param source the paginated source result to map
     * @return a paginated result containing mapped target elements
     */
    PageResult<T> map(PageResult<S> source);
}