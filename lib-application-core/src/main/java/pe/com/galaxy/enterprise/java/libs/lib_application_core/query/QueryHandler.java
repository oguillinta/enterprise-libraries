package pe.com.galaxy.enterprise.java.libs.lib_application_core.query;

/**
 * Defines the contract for handling an application {@link Query}.
 *
 * <p>A query handler coordinates the retrieval of application data and returns
 * the result associated with the handled query.</p>
 *
 * @param <Q> the query type handled by this component
 * @param <R> the result type produced by the query
 * @since 0.0.1
 */
public interface QueryHandler<Q extends Query<R>, R> {

    /**
     * Handles the specified query.
     *
     * @param query the query to execute
     * @return the result produced by the query execution
     */
    R handle(Q query);
}