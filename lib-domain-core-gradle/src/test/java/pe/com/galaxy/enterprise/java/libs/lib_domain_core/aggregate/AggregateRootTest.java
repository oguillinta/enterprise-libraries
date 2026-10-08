package pe.com.galaxy.enterprise.java.libs.lib_domain_core.aggregate;

import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_domain_core.event.DomainEvent;
import pe.com.galaxy.enterprise.java.libs.lib_domain_core.vo.DomainId;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link AggregateRoot} base class.
 *
 * <p>Verifies domain event registration, retrieval, ordering,
 * and removal after events are pulled from an aggregate.</p>
 *
 * @since 0.0.1
 */
public class AggregateRootTest {

    @Test
    void shouldRegisterAndPullDomainEvent() {
        TestAggregateRoot aggregate = new TestAggregateRoot();

        DomainEvent event = new TestDomainEvent(
                "TEST_EVENT",
                UUID.randomUUID(),
                Instant.now()
        );

        aggregate.registerEvent(event);

        List<DomainEvent> events = aggregate.pullDomainEvents();

        assertEquals(1, events.size());
        assertSame(event, events.getFirst());
    }

    @Test
    void shouldClearEventsAfterPullingThem() {
        TestAggregateRoot aggregate = new TestAggregateRoot();

        aggregate.registerEvent(
                new TestDomainEvent(
                        "TEST_EVENT",
                        UUID.randomUUID(),
                        Instant.now()
                )
        );

        aggregate.pullDomainEvents();

        assertTrue(aggregate.pullDomainEvents().isEmpty());
    }

    private static final class TestAggregateRoot
            extends AggregateRoot<TestDomainId> {

        void registerEvent(DomainEvent event) {
            addEvent(event);
        }
    }

    private static final class TestDomainId extends DomainId {

        private TestDomainId(UUID value) {
            super(value);
        }
    }

    private record TestDomainEvent(
            String eventType,
            UUID eventId,
            Instant occurredAt
    ) implements DomainEvent {
    }
}