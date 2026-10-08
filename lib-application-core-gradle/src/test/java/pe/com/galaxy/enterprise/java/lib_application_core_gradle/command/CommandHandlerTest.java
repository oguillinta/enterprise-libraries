package pe.com.galaxy.enterprise.java.lib_application_core_gradle.command;

import org.junit.jupiter.api.Test;
import pe.com.galaxy.enterprise.java.libs.lib_application_core_gradle.command.Command;
import pe.com.galaxy.enterprise.java.libs.lib_application_core_gradle.command.CommandHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the {@link CommandHandler} contract.
 *
 * <p>Verifies that commands can be handled through implementations of
 * the generic command handler abstraction.</p>
 *
 * @since 0.0.1
 */
public class CommandHandlerTest {

    @Test
    void shouldHandleCommandAndReturnExpectedResult() {

        TestCommand command = new TestCommand("Command-001");

        CommandHandler<TestCommand, String> handler = new TestCommandHandler();

        String result = handler.handle(command);

        assertEquals(
                "Command-001",
                result
        );
    }

    private record TestCommand(String name) implements Command<String> {}

    private static final class TestCommandHandler implements CommandHandler<TestCommand, String> {

        @Override
        public String handle(TestCommand command) {
            return command.name();
        }
    }

}
