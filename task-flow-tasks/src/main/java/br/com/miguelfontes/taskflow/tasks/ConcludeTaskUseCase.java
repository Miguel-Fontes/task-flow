package br.com.miguelfontes.taskflow.tasks;

import br.com.miguelfontes.taskflow.core.tasks.Task;
import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;
import br.com.miguelfontes.taskflow.ports.tasks.ConcludeTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.ConcludeTaskResponse;
import br.com.miguelfontes.taskflow.ports.tasks.TaskNotFoundException;
import br.com.miguelfontes.taskflow.tasks.factories.TaskDTOFactory;

/**
 * Concludes a Task
 *
 * @author Miguel Fontes
 */
public class ConcludeTaskUseCase {

    private final TaskRepository repository;

    private ConcludeTaskUseCase(TaskRepository repository) {
        this.repository = repository;
    }

    public static ConcludeTaskUseCase instance(TaskRepository repository) {
        return new ConcludeTaskUseCase(repository);
    }

    public ConcludeTaskResponse execute(ConcludeTaskRequest request) {
        return repository.findById(request.getId())
                .map(Task::conclude)
                .map(repository::save)
                .map(TaskDTOFactory::from)
                .map(ConcludeTaskResponse::of)
                .orElseThrow(() -> new TaskNotFoundException(request.getId()));
    }
}
