package br.com.miguelfontes.taskflow.tasks;

import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;
import br.com.miguelfontes.taskflow.ports.tasks.TaskNotFoundException;
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
                .map(task -> task.editTitle(request.getTitle()))
                .map(task -> task.editDescription(request.getDescription()))
                .map(repository::save)
                .map(TaskDTOFactory::from)
                .map(UpdateTaskResponse::of)
                .orElseThrow(() -> new TaskNotFoundException(request.getId()));
    }

}
