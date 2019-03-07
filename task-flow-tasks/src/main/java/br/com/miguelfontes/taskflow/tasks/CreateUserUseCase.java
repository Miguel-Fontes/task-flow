package br.com.miguelfontes.taskflow.tasks;

import br.com.miguelfontes.taskflow.core.tasks.User;
import br.com.miguelfontes.taskflow.ports.tasks.CreateUser;
import br.com.miguelfontes.taskflow.ports.tasks.CreateUserRequest;
import br.com.miguelfontes.taskflow.ports.tasks.CreateUserResponse;

/**
 * Implements the {@link CreateUser} port , defining the {@link br.com.miguelfontes.taskflow.core.tasks.User} creation
 * operation.
 *
 * @author Miguel Fontes
 */
public final class CreateUserUseCase implements CreateUser {

    private CreateUserUseCase() {
    }

    public static CreateUserUseCase instance() {
        return new CreateUserUseCase();
    }

    @Override
    public CreateUserResponse execute(CreateUserRequest request) {
        return CreateUserResponse.of(User.newInstance(request.getName()));
    }
}
