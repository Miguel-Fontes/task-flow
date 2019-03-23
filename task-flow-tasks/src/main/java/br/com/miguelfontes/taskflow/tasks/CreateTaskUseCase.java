package br.com.miguelfontes.taskflow.tasks;

import br.com.miguelfontes.taskflow.core.tasks.User;
import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTaskResponse;
import br.com.miguelfontes.taskflow.tasks.factories.TaskDTOFactory;

import java.util.Optional;

/**
 * Implements the CreateTask port, defining the {@link br.com.miguelfontes.taskflow.core.tasks.Task} creation
 * operation
 *
 * @author Miguel Fontes
 */
final class CreateTaskUseCase {

    private final TaskRepository repository;

    private CreateTaskUseCase(TaskRepository repository) {
        this.repository = repository;
    }

    public static CreateTaskUseCase instance(TaskRepository repository) {
        return new CreateTaskUseCase(repository);
    }

    CreateTaskResponse execute(CreateTaskRequest request) {
        return Optional.of(getUserById(request))
                .map(user -> user.createTask(request.getTitle()))
                .map(repository::save)
                .map(TaskDTOFactory::from)
                .map(CreateTaskResponse::of)
                .orElseThrow(IllegalArgumentException::new);
    }

    private User getUserById(CreateTaskRequest request) {
        var user = User.newInstance(request.getUserId().toString());
        return User.of(request.getUserId(), user.getName(), user.getCreatedAt(), user.getUpdatedAt()); // This is a temporary logic, while there is no persistence
    }

}
