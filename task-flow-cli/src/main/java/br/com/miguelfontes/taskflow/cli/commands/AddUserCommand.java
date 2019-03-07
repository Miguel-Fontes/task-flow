package br.com.miguelfontes.taskflow.cli.commands;

import br.com.miguelfontes.taskflow.cli.Command;
import br.com.miguelfontes.taskflow.ports.tasks.CreateUser;
import br.com.miguelfontes.taskflow.ports.tasks.CreateUserRequest;
import br.com.miguelfontes.taskflow.ports.tasks.CreateUserResponse;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

/**
 * Defines the arguments of a Add User Command.
 *
 * @author Miguel Fontes
 */
@Parameters(commandNames = "add-user", commandDescription = "Adds a new user")
public final class AddUserCommand implements Command {

    private final CreateUser createUser;

    private AddUserCommand(CreateUser createUser) {
        this.createUser = createUser;
    }

    public static AddUserCommand instance(CreateUser createUser) {
        return new AddUserCommand(createUser);
    }

    @Parameter(description = "The user's name")
    private String name;

    public void execute() {
        System.out.println(String.format("You've entered the name [%s].", name));
        System.out.println("Creating new User");

        CreateUserRequest request = CreateUserRequest.of(name);
        CreateUserResponse response = createUser.execute(request);

        System.out.println(String.format("Created user: [%s]", response.getUser().toString()));

    }
}
