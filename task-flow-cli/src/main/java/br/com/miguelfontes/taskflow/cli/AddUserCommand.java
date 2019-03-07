package br.com.miguelfontes.taskflow.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * Defines the arguments of a Add User Command.
 *
 * @author Miguel Fontes
 */
@Parameters(commandNames = "add-user", commandDescription = "Adds a new user")
class AddUserCommand implements Command {

    private AddUserCommand() {
    }

    static AddUserCommand instance() {
        return new AddUserCommand();
    }

    @Parameter(description = "The user's name")
    private String name;

    public void execute() {

    }
}
