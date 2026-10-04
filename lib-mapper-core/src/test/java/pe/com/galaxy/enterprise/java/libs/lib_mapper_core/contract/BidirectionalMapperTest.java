package pe.com.galaxy.enterprise.java.libs.lib_mapper_core.contract;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the {@link BidirectionalMapper} mapping contract.
 *
 * <p>Verifies mapping between domain and persistence representations
 * in both directions for individual values and collections.</p>
 *
 * @since 0.0.1
 */
public class BidirectionalMapperTest {

    @Test
    void shouldMapAccountDomainToEntity() {
        BidirectionalMapper<Account, AccountEntity> mapper = new AccountMapper();

        Account domain = new Account(
                "19100012345678",
                "CUS-10001",
                new BigDecimal("2575.50"),
                "PEN"
        );

        AccountEntity result = mapper.toExternal(domain);

        assertEquals(
                new AccountEntity(
                        "19100012345678",
                        "CUS-10001",
                        new BigDecimal("2575.50"),
                        "PEN"
                ),
                result
        );

    }

    @Test
    void shouldMapAccountEntityToDomain() {
        BidirectionalMapper<Account, AccountEntity> mapper = new AccountMapper();

        AccountEntity entity = new AccountEntity(
                "19100087654321",
                "CUS-10002",
                new BigDecimal("4850.75"),
                "PEN"
        );

        Account result = mapper.toDomain(entity);

        assertEquals(
                new Account(
                        "19100087654321",
                        "CUS-10002",
                        new BigDecimal("4850.75"),
                        "PEN"
                ),
                result
        );
    }

    @Test
    void shouldMapAccountDomainListToEntityList() {
        BidirectionalMapper<Account, AccountEntity> mapper = new AccountMapper();

        List<AccountEntity> result =
                mapper.toExternal(
                        List.of(
                                new Account(
                                        "19100012345678",
                                        "CUS-10001",
                                        new BigDecimal("2575.50"),
                                        "PEN"
                                ),
                                new Account(
                                        "19100087654321",
                                        "CUS-10002",
                                        new BigDecimal("4850.75"),
                                        "PEN"
                                )
                        )
                );

        assertEquals(
                List.of(
                        new AccountEntity(
                                "19100012345678",
                                "CUS-10001",
                                new BigDecimal("2575.50"),
                                "PEN"
                        ),
                        new AccountEntity(
                                "19100087654321",
                                "CUS-10002",
                                new BigDecimal("4850.75"),
                                "PEN"
                        )
                ),
                result
        );
    }

    @Test
    void shouldMapAccountEntityListToDomainList() {
        BidirectionalMapper<Account, AccountEntity> mapper =
                new AccountMapper();

        List<Account> result = mapper.toDomain(
                List.of(
                        new AccountEntity(
                                "19100012345678",
                                "CUS-10001",
                                new BigDecimal("2575.50"),
                                "PEN"
                        ),
                        new AccountEntity(
                                "19100087654321",
                                "CUS-10002",
                                new BigDecimal("4850.75"),
                                "PEN"
                        )
                )
        );

        assertEquals(
                List.of(
                        new Account(
                                "19100012345678",
                                "CUS-10001",
                                new BigDecimal("2575.50"),
                                "PEN"
                        ),
                        new Account(
                                "19100087654321",
                                "CUS-10002",
                                new BigDecimal("4850.75"),
                                "PEN"
                        )
                ),
                result
        );
    }

    private record Account(
            String accountNumber,
            String customerId,
            BigDecimal availableBalance,
            String currency
    ) {
    }

    private record AccountEntity(
            String accountNumber,
            String customerId,
            BigDecimal availableBalance,
            String currency
    ) {
    }

    private static final class AccountMapper implements BidirectionalMapper<Account, AccountEntity> {

        @Override
        public AccountEntity toExternal(Account domain) {
            return new AccountEntity(
                    domain.accountNumber(),
                    domain.customerId(),
                    domain.availableBalance(),
                    domain.currency()
            );
        }

        @Override
        public Account toDomain(AccountEntity external) {
            return new Account(
                    external.accountNumber(),
                    external.customerId(),
                    external.availableBalance(),
                    external.currency()
            );
        }

        @Override
        public List<AccountEntity> toExternal(List<Account> domain) {
            return domain.stream()
                    .map(this::toExternal)
                    .toList();
        }

        @Override
        public List<Account> toDomain(List<AccountEntity> external) {
            return external.stream()
                    .map(this::toDomain)
                    .toList();
        }
    }
}
