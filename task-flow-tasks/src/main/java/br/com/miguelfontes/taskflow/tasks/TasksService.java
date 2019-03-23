package br.com.miguelfontes.taskflow.tasks;

import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTaskResponse;
import br.com.miguelfontes.taskflow.ports.tasks.DeleteTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.SearchTasksRequest;
import br.com.miguelfontes.taskflow.ports.tasks.SearchTasksResponse;
import br.com.miguelfontes.taskflow.ports.tasks.TasksAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implements the TasksAPI services.
 *
 * @author Miguel Fontes
 */
@Service
public class TasksService implements TasksAPI {

    private final TaskRepository repository;

    @Autowired
    private TasksService(TaskRepository repository) {
        this.repository = repository;
    }

    public static TasksAPI instance(TaskRepository repository) {
        return new TasksService(repository);
    }

    @Override
    public CreateTaskResponse execute(CreateTaskRequest request) {
        return CreateTaskUseCase.instance(repository).execute(request);
    }

    @Override
    public SearchTasksResponse execute(SearchTasksRequest request) {
        return SearchTasksUseCase.instance(repository).execute(request);
    }

    @Override
    public void execute(DeleteTaskRequest request) {
        repository.delete(request.getId());
    }
}
