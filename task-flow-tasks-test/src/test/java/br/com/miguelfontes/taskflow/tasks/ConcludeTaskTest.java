package br.com.miguelfontes.taskflow.tasks;

import br.com.miguelfontes.taskflow.core.tasks.TaskStatus;
import br.com.miguelfontes.taskflow.persistence.mmdb.TaskRepositoryMMDB;
import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;
import br.com.miguelfontes.taskflow.ports.tasks.ConcludeTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.TaskNotFoundException;
import br.com.miguelfontes.taskflow.ports.tasks.TasksAPI;
import org.junit.jupiter.api.DisplayName;
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

    @Test
    @DisplayName("should conclude a Task")
    void shouldConcludeATask() {
        final var task = service.execute(CreateTaskRequest.of(USER_ID, TASK_TITLE)).getTask();

        final var concludedTask = service.execute(ConcludeTaskRequest.of(task.getId())).getTask();

        assertAll(
                () -> assertNotEquals(task.getUpdatedAt(), concludedTask.getUpdatedAt()),
                () -> assertEquals(TaskStatus.DONE.toString(), concludedTask.getStatus())
        );
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
