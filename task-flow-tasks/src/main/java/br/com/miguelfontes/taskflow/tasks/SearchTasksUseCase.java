package br.com.miguelfontes.taskflow.tasks;

import br.com.miguelfontes.taskflow.core.tasks.Task;
import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;
import br.com.miguelfontes.taskflow.ports.tasks.SearchTasksRequest;
import br.com.miguelfontes.taskflow.ports.tasks.SearchTasksResponse;
import br.com.miguelfontes.taskflow.tasks.factories.TaskDTOFactory;

import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * Implements the SearchTasks operation, searching for a collection of {@link br.com.miguelfontes.taskflow.core.tasks.Task}
 * with the given criteria.
 *
 * @author Miguel Fontes
 */
class SearchTasksUseCase {

    private final TaskRepository repository;

    private SearchTasksUseCase(TaskRepository repository) {
        this.repository = repository;
    }

    public static SearchTasksUseCase instance(TaskRepository repository) {
        return new SearchTasksUseCase(repository);
    }

    @SuppressWarnings("unused")
    SearchTasksResponse execute(SearchTasksRequest request) {
        return responseFrom(repository.findAll());
    }

    private SearchTasksResponse responseFrom(List<Task> tasks) {
        return SearchTasksResponse.of(tasks.stream()
                .map(TaskDTOFactory::from)
                .collect(toList()));
    }
}