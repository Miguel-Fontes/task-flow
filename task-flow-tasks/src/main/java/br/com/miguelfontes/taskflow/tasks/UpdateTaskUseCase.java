package br.com.miguelfontes.taskflow.tasks;

import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.UpdateTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.UpdateTaskResponse;
import br.com.miguelfontes.taskflow.tasks.factories.TaskDTOFactory;

/**
 * Updates a given Task.
 *
 * @author Miguel Fontes
 */
class UpdateTaskUseCase {
    private final TaskRepository repository;

    private UpdateTaskUseCase(TaskRepository repository) {
        this.repository = repository;
    }

    public static UpdateTaskUseCase instance(TaskRepository repository) {
        return new UpdateTaskUseCase(repository);
    }

    UpdateTaskResponse execute(UpdateTaskRequest request) {
        return repository.findById(request.getId())
                .map(task -> task.withTitle(request.getTitle()))
                .map(repository::save)
                .map(TaskDTOFactory::from)
                .map(UpdateTaskResponse::of)
                .orElseGet(() -> createTask(request));
    }

    private UpdateTaskResponse createTask(UpdateTaskRequest request) {
        var createTaskRequest = CreateTaskRequest.of(request.getAuthor(), request.getTitle());
        var response = CreateTaskUseCase.instance(repository).execute(createTaskRequest);

        return UpdateTaskResponse.of(response.getTask());
    }
}
