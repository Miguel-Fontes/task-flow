package br.com.miguelfontes.taskflow.ports.tasks;

/**
 * Encapsulates the data returned by a User creation request
 *
 * @author Miguel Fontes
 */
public final class CreateUserResponse {
    private final UserDTO user;

    private CreateUserResponse(UserDTO user) {
        this.user = user;
    }

    public static CreateUserResponse of(UserDTO user) {
        return new CreateUserResponse(user);
    }

    public UserDTO getUser() {
        return user;
    }
}
