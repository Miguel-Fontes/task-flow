package br.com.miguelfontes.taskflow.tasks;

import br.com.miguelfontes.taskflow.core.tasks.Task;
import br.com.miguelfontes.taskflow.core.tasks.User;
import br.com.miguelfontes.taskflow.persistence.StubTaskRepository;
import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;
import br.com.miguelfontes.taskflow.ports.tasks.SearchTasksRequest;
import br.com.miguelfontes.taskflow.ports.tasks.SearchTasksResponse;
import br.com.miguelfontes.taskflow.ports.tasks.TaskDTO;
import br.com.miguelfontes.taskflow.ports.tasks.TasksAPI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("search task")
class SearchTasksTest {

    private final TaskRepository repository = StubTaskRepository.instance();
    private final TasksAPI service = TasksService.instance(repository);

    private final SearchTasksRequest NO_CRITERIA_SEARCH_REQUEST = SearchTasksRequest.builder().build();
    private final String USER_NAME = "User name";
    private final String TASK_TITLE = "A task title";

    @Test
    @DisplayName("should return empty list when no task is found")
    void shouldReturnAEmptyListWhenNoTaskIsFound() {
        var response = service.execute(NO_CRITERIA_SEARCH_REQUEST);

        assertTrue(response.getTasks().isEmpty());
    }

    @Test
    @DisplayName("should return found tasks")
    void shouldReturnFoundTasks() {
        var author = User.newInstance(USER_NAME);
        var task = repository.save(Task.newInstance(author, TASK_TITLE));

        var foundTasks = service.execute(NO_CRITERIA_SEARCH_REQUEST);

        assertAll(
                () -> assertFalse(foundTasks.getTasks().isEmpty()),
                () -> assertFalse(filterFoundTasksBy(task, foundTasks).isEmpty())
        );
    }

    private List<UUID> filterFoundTasksBy(Task task, SearchTasksResponse foundTasks) {
        return foundTasks.getTasks().stream().map(TaskDTO::getId).filter(uuid -> task.getId().equals(uuid)).collect(Collectors.toList());
    }
}