package br.com.miguelfontes.taskflow.tasks;

import br.com.miguelfontes.taskflow.core.tasks.Task;
import br.com.miguelfontes.taskflow.core.tasks.User;
import br.com.miguelfontes.taskflow.persistence.mmdb.TaskRepositoryMMDB;
import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;
import br.com.miguelfontes.taskflow.ports.tasks.SearchTasksRequest;
import br.com.miguelfontes.taskflow.ports.tasks.SearchTasksResponse;
import br.com.miguelfontes.taskflow.ports.tasks.TaskDTO;
import br.com.miguelfontes.taskflow.ports.tasks.TasksAPI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("search task")
class SearchTasksTest {
    private static final String USER_NAME = "User name";
    private static final String TASK_TITLE = "A task title";
    private final TaskRepository repository = TaskRepositoryMMDB.instance();
    private final TasksAPI service = TasksService.instance(repository);

    @Test
    @DisplayName("should return empty list when no task is found")
    void shouldReturnAEmptyListWhenNoTaskIsFound() {
        var response = service.execute(SearchTasksRequest.ALL);

        assertTrue(response.getTasks().isEmpty());
    }

    @Test
    @DisplayName("should return found tasks")
    void shouldReturnFoundTasks() {
        var author = User.newInstance(USER_NAME);
        var task = repository.save(Task.newInstance(author, TASK_TITLE));

        var foundTasks = service.execute(SearchTasksRequest.ALL);

        assertAll(
                () -> assertFalse(foundTasks.getTasks().isEmpty()),
                () -> assertFalse(filterFoundTasksBy(task, foundTasks).isEmpty())
        );
    }

    private List<UUID> filterFoundTasksBy(Task task, SearchTasksResponse foundTasks) {
        return foundTasks.getTasks()
                .stream()
                .map(TaskDTO::getId)
                .filter(uuid -> task.getId().equals(uuid))
                .collect(Collectors.toList());
    }

    @Nested
    @DisplayName("search by title")
    class SearchByTitle {

        @Test
        @DisplayName("should search a task by title")
        void shouldSearchATaskByTitle() {
            var author = User.newInstance(USER_NAME);
            var task = repository.save(Task.newInstance(author, TASK_TITLE));

            var foundTasks = service.execute(buildSearchByTitleRequest(TASK_TITLE));

            assertAll(
                    () -> assertFalse(foundTasks.getTasks().isEmpty()),
                    () -> assertFalse(filterFoundTasksBy(task, foundTasks).isEmpty())
            );
        }

        @Test
        @DisplayName("should return a empty list when no title is matched")
        void shouldReturnAEmptyListWhenNoTitleIsMatched() {
            var author = User.newInstance(USER_NAME);
            var task = repository.save(Task.newInstance(author, TASK_TITLE));

            var foundTasks = service.execute(buildSearchByTitleRequest("this title does not match any task"));

            assertAll(
                    () -> assertTrue(foundTasks.getTasks().isEmpty()),
                    () -> assertTrue(filterFoundTasksBy(task, foundTasks).isEmpty())
            );
        }

        private SearchTasksRequest buildSearchByTitleRequest(String title) {
            return SearchTasksRequest.builder()
                    .title(title)
                    .build();
        }
    }

    @Nested
    @DisplayName("search by id")
    class SearchById {

        @Test
        @DisplayName("should search a task by id")
        void shouldSearchATaskById() {
            var author = User.newInstance(USER_NAME);
            var task = repository.save(Task.newInstance(author, TASK_TITLE));

            var foundTasks = service.execute(buildSearchByIdRequest(task.getId()));

            assertAll(
                    () -> assertFalse(foundTasks.getTasks().isEmpty()),
                    () -> assertFalse(filterFoundTasksBy(task, foundTasks).isEmpty())
            );
        }

        @Test
        @DisplayName("should return a a empty list when no task is found")
        void shouldReturnAEmptyListWhenNoTaskIsFound() {
            var author = User.newInstance(USER_NAME);
            var task = repository.save(Task.newInstance(author, TASK_TITLE));

            var foundTasks = service.execute(buildSearchByIdRequest(UUID.randomUUID()));

            assertAll(
                    () -> assertTrue(foundTasks.getTasks().isEmpty()),
                    () -> assertTrue(filterFoundTasksBy(task, foundTasks).isEmpty())
            );
        }

        private SearchTasksRequest buildSearchByIdRequest(UUID id) {
            return SearchTasksRequest.builder()
                    .id(id)
                    .build();
        }
    }
}
