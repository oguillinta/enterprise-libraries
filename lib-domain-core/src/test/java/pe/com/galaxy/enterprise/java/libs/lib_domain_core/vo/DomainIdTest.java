package pe.com.galaxy.enterprise.java.libs.lib_domain_core.vo;

import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_domain_core.exception.InvalidValueObjectException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link DomainId} base value object.
 *
 * <p>Verifies identifier creation, null validation, equality behavior,
 * and hash code consistency for domain identifiers backed by {@link UUID}.</p>
 *
 * @since 0.0.1
 */
public class DomainIdTest {

    @Test
    void shouldCreateDomainIdWithValidUuid() {
        UUID uuid = UUID.randomUUID();

        DomainId domainId = new TestDomainId(uuid);

        assertEquals(uuid, domainId.getValue());
    }

    @Test
    void shouldRejectNullUuid() {
        assertThrows(
                InvalidValueObjectException.class,
                () -> new TestDomainId(null)
        );
    }

    @Test
    void shouldBeEqualWhenIdsHaveSameValue() {
        UUID uuid = UUID.randomUUID();

        DomainId first = new TestDomainId(uuid);
        DomainId second = new TestDomainId(uuid);

        assertEquals(first, second);
        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void shouldNotBeEqualWhenIdsAreDifferent() {
        DomainId first = new TestDomainId(UUID.randomUUID());
        DomainId second = new TestDomainId(UUID.randomUUID());

        assertNotEquals(first, second);
    }

    private static final class TestDomainId extends DomainId {

        private TestDomainId(UUID value) {
            super(value);
        }
    }
}