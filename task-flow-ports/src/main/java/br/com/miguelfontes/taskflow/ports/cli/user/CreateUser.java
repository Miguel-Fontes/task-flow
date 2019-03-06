package br.com.miguelfontes.taskflow.ports.cli.user;

import br.com.miguelfontes.taskflow.core.User;

/**
 * Creates a {@link User} on Task Flow
 *
 * @author Miguel Fontes
 */
public interface CreateUser {
    CreateUserResponse execute(CreateUserRequest request);
}
