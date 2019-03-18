package br.com.miguelfontes.taskflow.tasks;

import br.com.miguelfontes.taskflow.core.tasks.User;
import br.com.miguelfontes.taskflow.ports.tasks.CreateUser;
import br.com.miguelfontes.taskflow.ports.tasks.CreateUserRequest;
import br.com.miguelfontes.taskflow.ports.tasks.CreateUserResponse;
import br.com.miguelfontes.taskflow.ports.tasks.UserDTO;
import org.springframework.stereotype.Service;

/**
 * Implements the {@link CreateUser} port, defining the {@link br.com.miguelfontes.taskflow.core.tasks.User} creation
 * operation.
 *
 * @author Miguel Fontes
 */
@Service
public final class CreateUserUseCase implements CreateUser {

    private CreateUserUseCase() {
    }

    public static CreateUserUseCase instance() {
        return new CreateUserUseCase();
    }

    @Override
    public CreateUserResponse execute(CreateUserRequest request) {
        var user = User.newInstance(request.getName());
        return CreateUserResponse.of(toUserDTO(user));
    }

    private UserDTO toUserDTO(User user) {
        return UserDTO.of(user.getId(), user.getName(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
