package br.com.miguelfontes.taskflow.tasks;

import br.com.miguelfontes.taskflow.core.tasks.TaskStatus;
import br.com.miguelfontes.taskflow.persistence.mmdb.TaskRepositoryMMDB;
import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.TaskDTO;
import br.com.miguelfontes.taskflow.ports.tasks.TasksAPI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("create task")
class CreateTaskTest {

    private static final UUID USER_ID = UUID.randomUUID();
    private static final String TASK_TITLE = "A task title!";

    private final TaskRepository repository = TaskRepositoryMMDB.instance();
    private final TasksAPI service = TasksService.instance(repository);

    @Test
    @DisplayName("should create a new task")
    void shouldCreateANewTask() {
        CreateTaskRequest request = CreateTaskRequest.of(USER_ID, TASK_TITLE);

        TaskDTO task = service.execute(request).getTask();

        assertAll(
                () -> assertEquals(TASK_TITLE, task.getTitle()),
                () -> assertNotNull(task.getCreatedAt()),
                () -> assertNotNull(task.getUpdatedAt()),
                () -> assertEquals(TaskStatus.INBOX.toString(), task.getStatus()),
                () -> assertEquals(USER_ID, task.getAuthor())
        );
    }

    @Test
    @DisplayName("should throw exception if argument is null")
    void shouldThrowExceptionIfTitleIsNull() {
        CreateTaskRequest request = CreateTaskRequest.of(USER_ID, null);

        assertThrows(IllegalArgumentException.class, () -> service.execute(request));
    }

    @Test
    @DisplayName("should throw exception when title length is below 1")
    void shouldThrowExceptionWhenTitleLengthIsBelowOne() {
        CreateTaskRequest request = CreateTaskRequest.of(USER_ID, "");

        assertThrows(IllegalArgumentException.class, () -> service.execute(request));
    }

    @ParameterizedTest
    @DisplayName("should not consider leading and trailing spaces on title length validation")
    @ValueSource(strings = {" ", "  ", "   ", "    "})
    void shouldNotConsiderLeadingAndTrailingSpacesOnTitleLengthValidation(String title) {
        CreateTaskRequest request = CreateTaskRequest.of(USER_ID, title);

        assertThrows(IllegalArgumentException.class, () -> service.execute(request));
    }
}