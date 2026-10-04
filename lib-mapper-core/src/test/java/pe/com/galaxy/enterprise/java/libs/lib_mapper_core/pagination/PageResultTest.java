package pe.com.galaxy.enterprise.java.libs.lib_mapper_core.pagination;

import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_mapper_core.contract.Mapper;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link PageResult} pagination model.
 *
 * <p>Verifies page content and pagination metadata representation.</p>
 *
 * @since 0.0.1
 */
public class PageResultTest {

    @Test
    void shouldCreatePageResultWithExpectedMetadata() {
        List<AccountSummary> content = List.of(
                new AccountSummary(
                        "19100012345678",
                        new BigDecimal("2575.50"),
                        "PEN"
                ),
                new AccountSummary(
                        "19100087654321",
                        new BigDecimal("4850.75"),
                        "PEN"
                )
        );


        PageResult<AccountSummary> result =
                new PageResult<>(
                        content,
                        0,
                        20,
                        42,
                        3,
                        true,
                        false
                );

        assertEquals(
                content,
                result.content()
        );

        assertEquals(0, result.page());
        assertEquals(20, result.size());
        assertEquals(42, result.totalElements());
        assertEquals(3, result.totalPages());
        assertTrue(result.first());
        assertFalse(result.last());
    }

    private record AccountSummary(
            String accountNumber,
            BigDecimal availableBalance,
            String currency
    ) {
    }

}
