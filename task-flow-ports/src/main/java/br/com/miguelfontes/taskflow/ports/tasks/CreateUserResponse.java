package br.com.miguelfontes.taskflow.ports.tasks;

import br.com.miguelfontes.taskflow.core.tasks.User;

/**
 * Encapsulates the data returned by a {@link User} creation request
 *
 * @author Miguel Fontes
 */
public class CreateUserResponse {
    private final UserDTO user;

    private CreateUserResponse(UserDTO user) {
        this.user = user;
    }

    public static CreateUserResponse of(User user) {
        return new CreateUserResponse(UserDTO.from(user));
    }

    public UserDTO getUser() {
        return user;
    }
}