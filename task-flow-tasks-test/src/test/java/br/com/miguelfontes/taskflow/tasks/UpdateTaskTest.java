package br.com.miguelfontes.taskflow.tasks;

import br.com.miguelfontes.taskflow.core.tasks.Task;
import br.com.miguelfontes.taskflow.core.tasks.User;
import br.com.miguelfontes.taskflow.persistence.mmdb.TaskRepositoryMMDB;
import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;
import br.com.miguelfontes.taskflow.ports.tasks.TaskDTO;
import br.com.miguelfontes.taskflow.ports.tasks.TaskNotFoundException;
import br.com.miguelfontes.taskflow.ports.tasks.TasksAPI;
import br.com.miguelfontes.taskflow.ports.tasks.UpdateTaskRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("update task")
class UpdateTaskTest {
    private static final String TASK_TITLE = "my task title";
    private static final String USER_NAME = "a user name";
    private static final User USER = User.newInstance(USER_NAME);
    private static final Task TASK = Task.newInstance(USER, TASK_TITLE);

    private final TaskRepository repository = TaskRepositoryMMDB.instance();
    private final TasksAPI service = TasksService.instance(repository);

    @Test
    @DisplayName("should throw exception when task is not found")
    void shouldThrowExceptionWhenTaskIsNotFound() {
        UUID nonExistentTaskId = UUID.randomUUID();
        Task task = repository.save(TASK);
        var request = buildUpdateTaskRequestWithId(task, nonExistentTaskId);

        final var taskNotFoundException = assertThrows(TaskNotFoundException.class, () -> service.execute(request));

        assertTrue(taskNotFoundException.getMessage().contains(nonExistentTaskId.toString()));
    }

    private UpdateTaskRequest buildUpdateTaskRequestWithId(Task task, UUID id) {
        return UpdateTaskRequest.of(id, task.getTitle(), task.getDescription());
    }

    @Nested
    @DisplayName("when successful")
    class WhenSuccessful {
        final String newTitle = "a new title";
        final String newDescription = "a new description";
        final Task task = repository.save(TASK);
        final TaskDTO updatedTask = service.execute(UpdateTaskRequest.of(task.getId(), newTitle, newDescription)).getTask();

        @Test
        @DisplayName("should update a Task title")
        void shouldUpdateATaskTitle() {
            assertEquals(newTitle, updatedTask.getTitle());
        }

        @Test
        @DisplayName("should update a task description")
        void shouldUpdateATaskDescription() {
            assertEquals(newDescription, updatedTask.getDescription());
        }

        @Test
        @DisplayName("should update task updatedAt field")
        void shouldUpdateTaskUpdatedAtAttribute() {
            assertTrue(task.getUpdatedAt().isBefore(updatedTask.getUpdatedAt()),
                    String.format("The original task updated at [%s] is not before the updated one [%s]!", task.getUpdatedAt().toString(), updatedTask.getUpdatedAt().toString()));
        }

        @Test
        @DisplayName("should not change other attributes")
        void shouldNotChangeOtherAttributes() {
            assertAll(
                    () -> assertEquals(task.getStatus().toString(), updatedTask.getStatus()),
                    () -> assertEquals(task.getAuthor().getId(), updatedTask.getAuthor()),
                    () -> assertEquals(task.getCreatedAt(), updatedTask.getCreatedAt()),
                    () -> assertEquals(task.getId(), updatedTask.getId())
            );
        }
    }
}