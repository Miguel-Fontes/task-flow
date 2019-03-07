package br.com.miguelfontes.taskflow.cli;

import br.com.miguelfontes.taskflow.cli.commands.AddTaskCommand;
import br.com.miguelfontes.taskflow.cli.commands.AddUserCommand;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTask;
import br.com.miguelfontes.taskflow.ports.tasks.CreateUser;
import br.com.miguelfontes.taskflow.tasks.CreateTaskUseCase;
import br.com.miguelfontes.taskflow.tasks.CreateUserUseCase;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * The application entry point
 *
 * @author Miguel  Fontes
 */
public final class Application {
    public static void main(String[] args) {
        Dispatcher.newInstance(getCommands(), args).dispatch();
    }

    private static List<Command> getCommands() {
        final CreateTask createTask = CreateTaskUseCase.instance();
        final CreateUser createUser = CreateUserUseCase.instance();


        return asList(AddTaskCommand.instance(createTask), AddUserCommand.instance(createUser));
    }

}
