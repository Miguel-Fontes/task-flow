package br.com.miguelfontes.taskflow.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Command parser")
class DispatcherTest {
    private static final String TEST_COMMAND_1 = "test-command-1";
    private static final String TEST_COMMAND_2 = "test-command-2";
    private static final String TEST_ARGUMENT_1 = "test-argument-1";
    private static final String TEST_ARGUMENT_2 = "test-argument-2";

    @ParameterizedTest
    @DisplayName("should parse a command")
    @MethodSource
    void shouldParseACommand(String commandName, String argument) {
        String[] arguments = {commandName, argument};

        Dispatcher dispatcher = Dispatcher.newInstance(buildTestCommandsList(), arguments);

        assertEquals(commandName, dispatcher.getParsedCommand());
    }

    private List<Command> buildTestCommandsList() {
        return asList(new TestCommand2(), new TestCommand1());
    }

    private static Stream<Arguments> shouldParseACommand() {
        return Stream.of(
                Arguments.of(TEST_COMMAND_1, TEST_ARGUMENT_1),
                Arguments.of(TEST_COMMAND_2, TEST_ARGUMENT_2)
        );
    }

    @Parameters(commandNames = TEST_COMMAND_1)
    private class TestCommand1 implements Command {
        @Parameter
        private String someString;

        private Boolean hasBeenCalled = false;

        Boolean hasBeenCalled() {
            return hasBeenCalled;
        }

        public void execute() {
            hasBeenCalled = true;
        }
    }

    @Parameters(commandNames = TEST_COMMAND_2)
    private class TestCommand2 implements Command {
        @Parameter
        private String someString;

        public void execute() {
        }

    }

    @Test
    @DisplayName("should execute a command")
    void shouldExecuteACommand() {
        String[] arguments = {TEST_COMMAND_1, TEST_ARGUMENT_1};
        TestCommand1 command = new TestCommand1();

        Dispatcher dispatcher = Dispatcher.newInstance(singletonList(command), arguments);

        dispatcher.dispatch();
        assertTrue(command.hasBeenCalled());
    }

    @Test
    @DisplayName("should set command's attributes")
    void shouldSetCommandAttributes() {
        String[] arguments = {TEST_COMMAND_2, TEST_ARGUMENT_2};
        TestCommand2 command = new TestCommand2();

        Dispatcher.newInstance(singletonList(command), arguments);

        assertEquals(TEST_ARGUMENT_2, command.someString);
    }
}