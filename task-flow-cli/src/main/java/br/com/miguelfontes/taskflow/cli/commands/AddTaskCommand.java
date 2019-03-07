package br.com.miguelfontes.taskflow.cli.commands;

import br.com.miguelfontes.taskflow.cli.Command;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * Defines the arguments of a Add Task command
 *
 * @author Miguel Fontes
 */
@Parameters(commandNames = "add-task", commandDescription = "Adds a new task")
public final class AddTaskCommand implements Command {

    private AddTaskCommand() {
    }

    public static AddTaskCommand instance() {
        return new AddTaskCommand();
    }

    @Parameter(description = "The Task's title ")
    private String title;

    public void execute() {

        System.out.println(String.format("You've entered the title [%s]", title));
    }
}
