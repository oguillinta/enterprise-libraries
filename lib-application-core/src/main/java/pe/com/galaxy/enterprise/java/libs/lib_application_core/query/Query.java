package pe.com.galaxy.enterprise.java.libs.lib_application_core.query;

/**
 * Represents an application query that requests data without expressing
 * an intention to modify application state.
 *
 * <p>The generic type parameter represents the result returned when the query
 * is handled.</p>
 *
 * <p>Concrete queries should contain only the criteria required to retrieve
 * the requested information.</p>
 *
 * @param <R> the result type produced when the query is handled
 * @since 0.0.1
 */
public interface Query<R> {
}