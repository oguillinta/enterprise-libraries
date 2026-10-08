package pe.com.galaxy.enterprise.java.libs.lib_application_core_gradle.command;

/**
 * Defines the contract for handling an application {@link Command}.
 *
 * <p>A command handler coordinates the execution of a state-changing use case
 * and returns the result associated with the handled command.</p>
 *
 * @param <C> the command type handled by this component
 * @param <R> the result type produced by the command
 * @since 0.0.1
 */
public interface CommandHandler<C extends Command<R>, R> {

    /**
     * Handles the specified command.
     *
     * @param command the command to execute
     * @return the result produced by the command execution
     */
    R handle(C command);
}