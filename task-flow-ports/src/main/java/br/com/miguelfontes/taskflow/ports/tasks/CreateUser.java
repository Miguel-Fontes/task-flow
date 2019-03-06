package br.com.miguelfontes.taskflow.ports.tasks;

import br.com.miguelfontes.taskflow.core.tasks.User;

/**
 * Creates a {@link User} on Task Flow
 *
 * @author Miguel Fontes
 */
public interface CreateUser {
    CreateUserResponse execute(CreateUserRequest request);
}
