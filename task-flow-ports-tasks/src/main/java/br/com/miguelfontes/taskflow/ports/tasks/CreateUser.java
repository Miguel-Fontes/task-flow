package br.com.miguelfontes.taskflow.ports.tasks;


/**
 * Creates a User on Task Flow
 *
 * @author Miguel Fontes
 */
public interface CreateUser {
    CreateUserResponse execute(CreateUserRequest request);
}
