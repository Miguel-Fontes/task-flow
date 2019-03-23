package br.com.miguelfontes.taskflow.tasks;

import br.com.miguelfontes.taskflow.core.tasks.Task;
import br.com.miguelfontes.taskflow.core.tasks.User;
import br.com.miguelfontes.taskflow.persistence.mmdb.TaskRepositoryMMDB;
import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;
import br.com.miguelfontes.taskflow.ports.tasks.TaskDTO;
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
    @DisplayName("should update a Task title")
    void shouldUpdateATaskTitle() {
        final String newTitle = "a new title";
        Task task = repository.save(TASK);
        var request = buildUpdateTaskTitleRequest(task, newTitle);

        TaskDTO response = service.execute(request).getTask();

        assertAll(
                () -> assertEquals(task.getId(), response.getId()),
                () -> assertEquals(task.getStatus().toString(), response.getStatus()),
                () -> assertEquals(task.getCreatedAt(), response.getCreatedAt()),
                () -> assertNotEquals(task.getTitle(), response.getTitle()),
                () -> assertEquals(newTitle, response.getTitle())
        );
    }

    private UpdateTaskRequest buildUpdateTaskTitleRequest(Task task, String title) {
        return UpdateTaskRequest.of(task.getId(), title, task.getDescription(), task.getStatus().toString(), task.getAuthor().getId());
    }

    @Test
    @DisplayName("should update a task description")
    void shouldUpdateATaskDescription() {
        final String newDescription = "a new description";
        Task task = repository.save(TASK);
        var request = buildUpdateTaskDescriptionRequest(task, newDescription);

        TaskDTO response = service.execute(request).getTask();

        assertAll(
                () -> assertEquals(task.getId(), response.getId()),
                () -> assertEquals(task.getTitle(), response.getTitle()),
                () -> assertEquals(task.getStatus().toString(), response.getStatus()),
                () -> assertEquals(task.getCreatedAt(), response.getCreatedAt()),
                () -> assertNotEquals(task.getDescription(), response.getDescription()),
                () -> assertEquals(newDescription, response.getDescription())
        );
    }

    private UpdateTaskRequest buildUpdateTaskDescriptionRequest(Task task, String newDescription) {
        return UpdateTaskRequest.of(task.getId(), task.getTitle(), newDescription, task.getStatus().toString(), task.getAuthor().getId());
    }

    @Nested
    @DisplayName("task creation on update")
    class TaskCreationOnUpdate {

        @Test
        @DisplayName("should create a new task if none is found with a given id")
        void shouldCreateANewTaskIfNoneIsFoundWithGivenId() {
            UUID newId = UUID.randomUUID();
            Task task = repository.save(TASK);
            var request = buildUpdateTaskRequestWithId(task, newId);

            TaskDTO newTask = service.execute(request).getTask();

            assertAll(
                    () -> assertEquals(task.getTitle(), newTask.getTitle()),
                    () -> assertEquals(task.getStatus().toString(), newTask.getStatus()),
                    () -> assertEquals(task.getDescription(), newTask.getDescription()),
                    () -> assertNotEquals(task.getId(), newTask.getId()),
                    () -> assertNotEquals(task.getCreatedAt(), newTask.getCreatedAt())
            );
        }

        @Test
        @DisplayName("should generate a new id for the task")
        void shouldGenerateANewIdForTheTask() {
            UUID newId = UUID.randomUUID();
            Task task = repository.save(TASK);
            var request = buildUpdateTaskRequestWithId(task, newId);

            TaskDTO newTask = service.execute(request).getTask();

            assertNotEquals(newId, newTask.getId());
        }

        private UpdateTaskRequest buildUpdateTaskRequestWithId(Task task, UUID id) {
            return UpdateTaskRequest.of(id, task.getTitle(), task.getDescription(), task.getStatus().toString(), task.getAuthor().getId());
        }
    }


}