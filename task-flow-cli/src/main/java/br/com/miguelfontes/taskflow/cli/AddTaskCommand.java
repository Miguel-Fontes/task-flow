package br.com.miguelfontes.taskflow.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * Defines the arguments of a Add Task command
 *
 * @author Miguel Fontes
 */
@Parameters(commandNames = "add-task", commandDescription = "Adds a new task")
class AddTaskCommand implements Command {

    private AddTaskCommand() {
    }

    static AddTaskCommand instance() {
        return new AddTaskCommand();
    }

    @Parameter(description = "The Task's title ")
    private String title;

    public void execute() {

        System.out.println(String.format("You've entered the title [%s]", title));
    }
}
