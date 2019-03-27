package br.com.miguelfontes.taskflow.tasks;

import br.com.miguelfontes.taskflow.core.tasks.Task;
import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;
import br.com.miguelfontes.taskflow.ports.tasks.StartTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.StartTaskResponse;
import br.com.miguelfontes.taskflow.ports.tasks.TaskNotFoundException;
import br.com.miguelfontes.taskflow.tasks.factories.TaskDTOFactory;

/**
 * Sets a given Task as doing
 *
 * @author Miguel Fontes
 */
public class StartTaskUseCase {

    private final TaskRepository repository;

    private StartTaskUseCase(TaskRepository repository) {
        this.repository = repository;
    }

    public static StartTaskUseCase instance(TaskRepository repository) {
        return new StartTaskUseCase(repository);
    }

    StartTaskResponse execute(StartTaskRequest request) {
        return repository.findById(request.getTaskId())
                .map(Task::start)
                .map(TaskDTOFactory::from)
                .map(StartTaskResponse::of)
                .orElseThrow(() -> new TaskNotFoundException(request.getTaskId()));
    }

}
