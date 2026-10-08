package pe.com.galaxy.enterprise.java.libs.lib_domain_core_gradle.event;

import java.time.Instant;
import java.util.UUID;

/**
 * Represents an event produced by the domain.
 *
 * <p>A domain event describes something that occurred within the domain
 * and is relevant to other parts of the application or system.</p>
 *
 * @since 0.0.1
 */
public interface DomainEvent {
    /**
     * Returns the type of the domain event.
     *
     * @return the event type
     */
    String eventType();

    /**
     * Returns the unique identifier of the domain event.
     *
     * @return the event identifier
     */
    UUID eventId();

    /**
     * Returns the instant at which the domain event occurred.
     *
     * @return the event occurrence timestamp
     */
    Instant occurredAt();
}
