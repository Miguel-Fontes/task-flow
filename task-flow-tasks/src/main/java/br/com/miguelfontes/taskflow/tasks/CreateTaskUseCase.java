package br.com.miguelfontes.taskflow.tasks;

import br.com.miguelfontes.taskflow.core.tasks.User;
import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTask;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTaskResponse;

import java.util.Optional;

/**
 * Implements the {@link CreateTask} port, defining the {@link br.com.miguelfontes.taskflow.core.tasks.Task} creation
 * operation
 *
 * @author Miguel Fontes
 */
public final class CreateTaskUseCase implements CreateTask {

    private final TaskRepository repository;

    private CreateTaskUseCase(TaskRepository repository) {
        this.repository = repository;
    }

    public static CreateTaskUseCase instance(TaskRepository repository) {
        return new CreateTaskUseCase(repository);
    }

    @Override
    public CreateTaskResponse execute(CreateTaskRequest request) {
        return Optional.of(User.newInstance(request.getUserId().toString()))
                .map(user -> User.of(request.getUserId(), user.getName(), user.getCreatedAt(), user.getUpdatedAt())) // This is a temporary logic, while there is no persistence
                .map(user -> user.createTask(request.getTitle()))
                .map(repository::save)
                .map(CreateTaskResponse::from)
                .orElseThrow(IllegalArgumentException::new);
    }
}
