package br.com.miguelfontes.taskflow.cli;

import com.beust.jcommander.JCommander;

import java.util.List;
import java.util.Optional;

/**
 * A parser of command line arguments
 *
 * @author Miguel Fontes
 */
final class Dispatcher {

    private final JCommander parser;

    private Dispatcher(JCommander parser) {
        this.parser = parser;
    }

    static Dispatcher newInstance(List<Command> commands, String[] arguments) {
        return new Dispatcher(parse(commands, arguments));
    }

    private static JCommander parse(List<Command> commands, String[] arguments) {
        JCommander parser = getCommandParser(commands);
        parser.parse(arguments);

        return parser;
    }

    private static JCommander getCommandParser(List<Command> commands) {
        JCommander.Builder builder = JCommander.newBuilder();
        commands.forEach(builder::addCommand);
        return builder.build();

    }

    String getParsedCommand() {
        return parser.getParsedCommand();
    }

    void dispatch() {
        getCommand(parser.getParsedCommand())
                .ifPresent(Command::execute);
    }

    private Optional<Command> getCommand(String parsedCommand) {
        JCommander jCommander = parser.getCommands().get(parsedCommand);
        return Optional.ofNullable((Command) jCommander.getObjects().get(0));
    }
}
