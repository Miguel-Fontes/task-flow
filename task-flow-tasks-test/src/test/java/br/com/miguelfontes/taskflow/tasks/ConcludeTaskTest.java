package br.com.miguelfontes.taskflow.tasks;

import br.com.miguelfontes.taskflow.core.tasks.TaskStatus;
import br.com.miguelfontes.taskflow.persistence.mmdb.TaskRepositoryMMDB;
import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;
import br.com.miguelfontes.taskflow.ports.tasks.ConcludeTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.TaskDTO;
import br.com.miguelfontes.taskflow.ports.tasks.TaskNotFoundException;
import br.com.miguelfontes.taskflow.ports.tasks.TasksAPI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("conclude task")
class ConcludeTaskTest {
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String TASK_TITLE = "A task title!";

    private final TaskRepository repository = TaskRepositoryMMDB.instance();
    private final TasksAPI service = TasksService.instance(repository);

    @Nested
    @DisplayName("when successful")
    class WhenSuccessful {
        TaskDTO task = service.execute(CreateTaskRequest.of(USER_ID, TASK_TITLE)).getTask();
        TaskDTO concludedTask = service.execute(ConcludeTaskRequest.of(task.getId())).getTask();

        @Test
        @DisplayName("should conclude a Task")
        void shouldConcludeATask() {
            assertEquals(TaskStatus.DONE.toString(), concludedTask.getStatus());
        }

        @Test
        @DisplayName("should update the updated at task attribute")
        void shouldUpdateTheUpdatedAtTaskAttribute() {
            assertTrue(task.getUpdatedAt().isBefore(concludedTask.getUpdatedAt()),
                    String.format("The original task updated at [%s] is not before the updated one [%s]!", task.getUpdatedAt().toString(), concludedTask.getUpdatedAt().toString()));
        }

        @Test
        @DisplayName("should not change other attributes")
        void shouldNotChangeOtherAttributes() {
            assertAll(
                    () -> assertEquals(task.getTitle(), concludedTask.getTitle()),
                    () -> assertEquals(task.getDescription(), concludedTask.getDescription()),
                    () -> assertEquals(task.getCreatedAt(), concludedTask.getCreatedAt()),
                    () -> assertEquals(task.getAuthor(), concludedTask.getAuthor())
            );

        }
    }

    @Test
    @DisplayName("should throw exception when no tasks are found")
    void shouldThrowExceptionWhenNoTasksAreFound(TestReporter reporter) {
        final var uuid = UUID.randomUUID();

        final var taskNotFoundException = assertThrows(TaskNotFoundException.class, () -> service.execute(ConcludeTaskRequest.of(uuid)));

        reporter.publishEntry("Error message: " + taskNotFoundException.getMessage());
        assertTrue(taskNotFoundException.getMessage().contains(uuid.toString()));
    }
}
