package pe.com.galaxy.enterprise.java.libs.lib_application_core.command;

/**
 * Represents an application command that requests a state-changing operation.
 *
 * <p>A command models an intention to execute an application use case that may
 * modify system state. The generic type parameter represents the result produced
 * by the command handler.</p>
 *
 * <p>Concrete commands should contain only the data required to execute the
 * corresponding use case and should not contain application orchestration logic.</p>
 *
 * @param <R> the result type produced when the command is handled
 * @since 0.0.1
 */
public interface Command<R> {
}