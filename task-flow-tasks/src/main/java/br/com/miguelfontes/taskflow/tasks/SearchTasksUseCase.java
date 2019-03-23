package br.com.miguelfontes.taskflow.tasks;

import br.com.miguelfontes.taskflow.core.tasks.Task;
import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;
import br.com.miguelfontes.taskflow.ports.tasks.SearchTasksRequest;
import br.com.miguelfontes.taskflow.ports.tasks.SearchTasksResponse;
import br.com.miguelfontes.taskflow.tasks.factories.TaskDTOFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
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

    SearchTasksResponse execute(SearchTasksRequest request) {
        return responseFrom(findById(request)
                .or(() -> findByTitle(request))
                .or(() -> findAll(request))
                .orElse(emptyList()));
    }

    private Optional<List<Task>> findById(SearchTasksRequest request) {
        return request.getId()
                .map(repository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Collections::singletonList);
    }

    private Optional<List<Task>> findByTitle(SearchTasksRequest request) {
        return request.getTitle()
                .map(repository::findByTitle);
    }

    private Optional<List<Task>> findAll(SearchTasksRequest request) {
        return hasNoSearchCriteria(request)
                ? Optional.of(repository.findAll())
                : Optional.empty();
    }

    private boolean hasNoSearchCriteria(SearchTasksRequest request) {
        return request.getTitle().isEmpty() && request.getId().isEmpty();
    }

    private SearchTasksResponse responseFrom(List<Task> tasks) {
        return SearchTasksResponse.of(tasks.stream()
                .map(TaskDTOFactory::from)
                .collect(toList()));
    }
}