package br.com.miguelfontes.taskflow.cli;

import java.util.List;

import static java.util.Arrays.asList;

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
        return asList(AddTaskCommand.instance(), AddUserCommand.instance());
    }

}
