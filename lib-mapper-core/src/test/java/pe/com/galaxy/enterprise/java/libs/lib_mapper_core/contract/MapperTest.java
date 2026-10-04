package pe.com.galaxy.enterprise.java.libs.lib_mapper_core.contract;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the {@link Mapper} mapping contract.
 *
 * <p>Verifies one-way mapping for individual values and collections.</p>
 *
 * @since 0.0.1
 */
public class MapperTest {
    @Test
    void shouldMapCustomerEntityToResponse() {
        Mapper<CustomerEntity, CustomerResponse> mapper = new CustomerMapper();

        CustomerEntity source = new CustomerEntity(
                "CUS-10001",
                "María López Torres",
                "maria.lopez@example.com",
                "ACTIVE"
        );

        CustomerResponse result = mapper.map(source);

        assertEquals(
                new CustomerResponse(
                        "CUS-10001",
                        "María López Torres",
                        "maria.lopez@example.com",
                        "ACTIVE"
                ),
                result
        );
    }

    void shouldMapCustomerEntityListToResponseList() {
        Mapper<CustomerEntity, CustomerResponse> mapper = new CustomerMapper();

        List<CustomerResponse> result = mapper.map(List.of(
                new CustomerEntity(
                        "CUS-10001",
                        "María López Torres",
                        "maria.lopez@example.com",
                        "ACTIVE"
                ),
                new CustomerEntity(
                        "CUS-10002",
                        "Juan Pérez García",
                        "juan.perez@example.com",
                        "ACTIVE"
                )
        ));

        assertEquals(
                List.of(
                        new CustomerResponse(
                                "CUS-10001",
                                "María López Torres",
                                "maria.lopez@example.com",
                                "ACTIVE"
                        ),
                        new CustomerResponse(
                                "CUS-10002",
                                "Juan Pérez García",
                                "juan.perez@example.com",
                                "ACTIVE"
                        )
                ),
                result
        );
    }

    private record CustomerEntity(
            String customerId,
            String fullName,
            String email,
            String status
    ) {}

    private record CustomerResponse(
            String customerId,
            String fullName,
            String email,
            String status
    ) {}

    private static final class CustomerMapper implements Mapper<CustomerEntity, CustomerResponse> {

        @Override
        public CustomerResponse map(CustomerEntity source) {
            return new CustomerResponse(
                    source.customerId,
                    source.fullName,
                    source.email,
                    source.status
            );
        }

        @Override
        public List<CustomerResponse> map(List<CustomerEntity> source) {
            return source.stream()
                    .map(this::map)
                    .toList();
        }
    }
}
