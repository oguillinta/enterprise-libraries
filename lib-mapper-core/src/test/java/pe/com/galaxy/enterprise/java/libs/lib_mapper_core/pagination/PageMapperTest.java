package pe.com.galaxy.enterprise.java.libs.lib_mapper_core.pagination;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Unit tests for the {@link PageMapper} pagination mapping contract.
 *
 * <p>Verifies content transformation while preserving pagination
 * metadata.</p>
 *
 * @since 0.0.1
 */
public class PageMapperTest {

    @Test
    void shouldMapCustomerPageAndPreservePaginationMetadata() {
        PageResult<CustomerEntity> source =
                new PageResult<>(
                        List.of(
                                new CustomerEntity(
                                        "CUS-10021",
                                        "María López Torres",
                                        "maria.lopez@example.com"
                                ),
                                new CustomerEntity(
                                        "CUS-10022",
                                        "Juan Pérez García",
                                        "juan.perez@example.com"
                                )
                        ),
                        2,
                        20,
                        125,
                        7,
                        false,
                        false
                );

        PageMapper<CustomerEntity, CustomerResponse> mapper =
                new CustomerPageMapper();

        PageResult<CustomerResponse> result =
                mapper.map(source);

        assertEquals(
                List.of(
                        new CustomerResponse(
                                "CUS-10021",
                                "María López Torres",
                                "maria.lopez@example.com"
                        ),
                        new CustomerResponse(
                                "CUS-10022",
                                "Juan Pérez García",
                                "juan.perez@example.com"
                        )
                ),
                result.content()
        );

        assertEquals(
                source.page(),
                result.page()
        );

        assertEquals(
                source.size(),
                result.size()
        );

        assertEquals(
                source.totalElements(),
                result.totalElements()
        );

        assertEquals(
                source.totalPages(),
                result.totalPages()
        );

        assertFalse(result.first());
        assertFalse(result.last());
    }

    private record CustomerEntity(
            String customerId,
            String fullName,
            String email
    ) {
    }

    private record CustomerResponse(
            String customerId,
            String fullName,
            String email
    ) {
    }

    private static final class CustomerPageMapper
            implements PageMapper<
            CustomerEntity,
            CustomerResponse
            > {

        @Override
        public PageResult<CustomerResponse> map(
                PageResult<CustomerEntity> source
        ) {
            List<CustomerResponse> content =
                    source.content()
                            .stream()
                            .map(customer ->
                                    new CustomerResponse(
                                            customer.customerId(),
                                            customer.fullName(),
                                            customer.email()
                                    )
                            )
                            .toList();

            return new PageResult<>(
                    content,
                    source.page(),
                    source.size(),
                    source.totalElements(),
                    source.totalPages(),
                    source.first(),
                    source.last()
            );
        }
    }
}