package pe.com.galaxy.enterprise.java.lib_application_core_gradle.query;

import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_application_core_gradle.query.Query;
import pe.com.galaxy.enterprise.java.libs.lib_application_core_gradle.query.QueryHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the {@link QueryHandler} contract.
 *
 * <p>Verifies that queries can be handled through implementations of
 * the generic query handler abstraction.</p>
 *
 * @since 0.0.1
 */
public class QueryHandlerTest {

    @Test
    void shouldHandleQueryAndReturnExpectedResult() {

        TestQuery query = new TestQuery("CUSTOMER-001");

        QueryHandler<TestQuery, String> handler = new TestQueryHandler();

        String result = handler.handle(query);

        assertEquals("Customer: CUSTOMER-001", result);

    }

    private record TestQuery(String customerId) implements Query<String> {}

    private static final class TestQueryHandler implements QueryHandler<TestQuery, String> {

        @Override
        public String handle(TestQuery query) {
            return "Customer: " + query.customerId();
        }
    }
}
