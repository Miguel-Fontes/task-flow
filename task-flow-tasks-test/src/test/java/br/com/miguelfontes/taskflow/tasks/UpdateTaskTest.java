package br.com.miguelfontes.taskflow.tasks;

import br.com.miguelfontes.taskflow.core.tasks.Task;
import br.com.miguelfontes.taskflow.core.tasks.User;
import br.com.miguelfontes.taskflow.persistence.mmdb.TaskRepositoryMMDB;
import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;
import br.com.miguelfontes.taskflow.ports.tasks.TaskDTO;
import br.com.miguelfontes.taskflow.ports.tasks.TasksAPI;
import br.com.miguelfontes.taskflow.ports.tasks.UpdateTaskRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
                () -> assertNotEquals(task.getTitle(), response.getTitle())
        );
    }

    private UpdateTaskRequest buildUpdateTaskTitleRequest(Task task, String title) {
        return UpdateTaskRequest.of(task.getId(), title, task.getStatus().toString(), task.getAuthor().getId());
    }
}