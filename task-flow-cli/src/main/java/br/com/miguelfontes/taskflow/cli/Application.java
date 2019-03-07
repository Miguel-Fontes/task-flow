package br.com.miguelfontes.taskflow.cli;

import java.util.Arrays;
import java.util.List;

/**
 * The application entry point
 *
 * @author Miguel  Fontes
 */
public class Application {
    public static void main(String[] args) {
        Dispatcher.newInstance(getCommands(), args).dispatch();
    }

    private static List<Command> getCommands() {
        return Arrays.asList(AddTaskCommand.instance(), AddUserCommand.instance());
    }

}
