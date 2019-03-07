package br.com.miguelfontes.taskflow.cli.commands;

import br.com.miguelfontes.taskflow.cli.Command;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * Defines the arguments of a Add User Command.
 *
 * @author Miguel Fontes
 */
@Parameters(commandNames = "add-user", commandDescription = "Adds a new user")
public class AddUserCommand implements Command {

    private AddUserCommand() {
    }

    public static AddUserCommand instance() {
        return new AddUserCommand();
    }

    @Parameter(description = "The user's name")
    private String name;

    public void execute() {

    }
}
