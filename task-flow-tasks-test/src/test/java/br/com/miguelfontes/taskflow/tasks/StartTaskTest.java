package br.com.miguelfontes.taskflow.tasks;

import br.com.miguelfontes.taskflow.core.tasks.Task;
import br.com.miguelfontes.taskflow.core.tasks.User;
import br.com.miguelfontes.taskflow.persistence.mmdb.TaskRepositoryMMDB;
import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;
import br.com.miguelfontes.taskflow.ports.tasks.StartTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.TaskNotFoundException;
import br.com.miguelfontes.taskflow.ports.tasks.TasksAPI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("start task")
class StartTaskTest {

    private final TaskRepository repository = TaskRepositoryMMDB.instance();
    private final TasksAPI service = TasksService.instance(repository);

    @Nested
    @DisplayName("with valid arguments")
    class WithValidArguments {

        private final String TASK_TITLE = "my task title";
        private final String USER_NAME = "a user name";
        private final User USER = User.newInstance(USER_NAME);
        private final Task TASK = Task.newInstance(USER, TASK_TITLE);

        private final Task task = repository.save(TASK);

        @Test
        @DisplayName("should set a task status as in progress")
        void shouldSetATaskStatusAsInProgress() {
            var startedTask = service.execute(StartTaskRequest.of(task.getId()));

            assertEquals("DOING", startedTask.getTask().getStatus());
        }

        @Test
        @DisplayName("should update the date when the task was started")
        void shouldUpdateThenTheTaskWasStarted() {
            var startedTask = service.execute(StartTaskRequest.of(task.getId()));

            assertNotEquals(task.getUpdatedAt(), startedTask.getTask().getUpdatedAt());
        }
    }

    @Nested
    @DisplayName("with invalid arguments")
    class WithInvalidArguments {

        @Test
        @DisplayName("should throw exception when task is not found")
        void shouldThrowExceptionWhenTaskIsNotFound() {
            var id = UUID.randomUUID();

            final var taskNotFoundException =
                    assertThrows(TaskNotFoundException.class, () -> service.execute(StartTaskRequest.of(id)));

            assertTrue(taskNotFoundException.getMessage().contains(id.toString()));
        }
    }
}
