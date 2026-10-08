package pe.com.galaxy.enterprise.java.libs.lib_domain_core_gradle.aggregate;

import pe.com.galaxy.enterprise.java.libs.lib_domain_core_gradle.vo.DomainId;
import pe.com.galaxy.enterprise.java.libs.lib_domain_core_gradle.event.DomainEvent;

import java.util.ArrayList;
import java.util.List;
/**
 * Base class for aggregate roots in the domain model.
 *
 * <p>Provides support for registering and retrieving domain events
 * produced by the aggregate.</p>
 *
 * @param <ID> the identifier type of the aggregate
 * @since 0.0.1
 */
public abstract class AggregateRoot<ID extends DomainId> {

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    /**
     * Registers a domain event produced by the aggregate.
     *
     * @param event the domain event to register
     * @throws NullPointerException if {@code event} is {@code null}
     */
    protected void addEvent(DomainEvent event) {
        domainEvents.add(event);
    }

    /**
     * Returns the currently registered domain events and clears them
     * from the aggregate.
     *
     * @return an immutable list containing the registered domain events
     */
    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> events = List.copyOf(domainEvents);
        domainEvents.clear();
        return events;
    }
}
