package br.com.miguelfontes.taskflow.tasks;

import br.com.miguelfontes.taskflow.core.tasks.Task;
import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;
import br.com.miguelfontes.taskflow.ports.tasks.SearchTasks;
import br.com.miguelfontes.taskflow.ports.tasks.SearchTasksRequest;
import br.com.miguelfontes.taskflow.ports.tasks.SearchTasksResponse;
import br.com.miguelfontes.taskflow.tasks.factories.TaskDTOFactory;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Implements the {@link SearchTasks} operation, searching for a collection of {@link br.com.miguelfontes.taskflow.core.tasks.Task}
 * with the given criteria.
 *
 * @author Miguel Fontes
 */
public class SearchTasksUseCase implements SearchTasks {

    private final TaskRepository repository;

    private SearchTasksUseCase(TaskRepository repository) {
        this.repository = repository;
    }

    public static SearchTasks instance(TaskRepository repository) {
        return new SearchTasksUseCase(repository);
    }

    @Override
    public SearchTasksResponse execute(SearchTasksRequest request) {
        return responseFrom(repository.findAll());
    }

    private SearchTasksResponse responseFrom(List<Task> tasks) {
        return SearchTasksResponse.of(tasks.stream()
                .map(TaskDTOFactory::from)
                .collect(toList()));
    }
}