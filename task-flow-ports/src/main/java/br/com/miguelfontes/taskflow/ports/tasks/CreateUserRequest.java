package br.com.miguelfontes.taskflow.ports.tasks;

/**
 * Encapsulates the date involved on a user creation request
 *
 * @author Miguel Fontes
 */
public class CreateUserRequest {
    private final String name;

    private CreateUserRequest(String name) {
        this.name = name;
    }

    public static CreateUserRequest of(String name) {
        return new CreateUserRequest(name);
    }

    public String getName() {
        return name;
    }
}
