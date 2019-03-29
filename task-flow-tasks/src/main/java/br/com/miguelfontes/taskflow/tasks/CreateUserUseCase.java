package br.com.miguelfontes.taskflow.tasks;

import br.com.miguelfontes.taskflow.core.tasks.User;
import br.com.miguelfontes.taskflow.ports.tasks.CreateUserRequest;
import br.com.miguelfontes.taskflow.ports.tasks.CreateUserResponse;
import br.com.miguelfontes.taskflow.ports.tasks.UserDTO;
import org.springframework.stereotype.Service;

/**
 * Executes the Create User use case.
 *
 * @author Miguel Fontes
 */
@Service
public final class CreateUserUseCase {

    private CreateUserUseCase() {
    }

    public static CreateUserUseCase instance() {
        return new CreateUserUseCase();
    }

    public CreateUserResponse execute(CreateUserRequest request) {
        var user = User.newInstance(request.getName());
        return CreateUserResponse.of(toUserDTO(user));
    }

    private UserDTO toUserDTO(User user) {
        return UserDTO.of(user.getId(), user.getName(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
