package br.com.miguelfontes.tasks;

import br.com.miguelfontes.taskflow.core.tasks.TaskStatus;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTask;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.TaskDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("create task use case")
class CreateTaskUseCaseTest {

    private static final UUID USER_ID = UUID.randomUUID();
    private static final String TASK_TITLE = "A task title!";

    private final CreateTask useCase = CreateTaskUseCase.instance();

    @Test
    @DisplayName("should create a new task")
    void shouldCreateANewTask() {
        CreateTaskRequest request = CreateTaskRequest.of(USER_ID, TASK_TITLE);

        TaskDTO task = useCase.execute(request).getTask();

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

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(request));
    }

    @Test
    @DisplayName("should throw exception when title length is below 1")
    void shouldThrowExceptionWhenTitleLengthIsBelowOne() {
        CreateTaskRequest request = CreateTaskRequest.of(USER_ID, "");

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(request));
    }

    @ParameterizedTest
    @DisplayName("should not consider leading and trailing spaces on title length validation")
    @ValueSource(strings = {" ", "  ", "   ", "    "})
    void shouldNotConsiderLeadingAndTrailingSpacesOnTitleLengthValidation(String title) {
         CreateTaskRequest request = CreateTaskRequest.of(USER_ID, title);

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(request));
    }
}