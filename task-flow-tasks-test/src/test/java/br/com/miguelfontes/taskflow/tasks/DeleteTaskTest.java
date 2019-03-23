package br.com.miguelfontes.taskflow.tasks;

import br.com.miguelfontes.taskflow.persistence.mmdb.TaskRepositoryMMDB;
import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.DeleteTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.SearchTasksRequest;
import br.com.miguelfontes.taskflow.ports.tasks.TasksAPI;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;

@DisplayName("delete task")
class DeleteTaskTest {
    private static final UUID USER_ID = UUID.randomUUID();
    private static final String TASK_TITLE = "A task title!";

    private final TaskRepository repository = TaskRepositoryMMDB.instance();
    private final TasksAPI service = TasksService.instance(repository);

    @Test
    @DisplayName("should delete a task")
    void shouldDeleteATask() {
        final var task = service
                .execute(CreateTaskRequest.of(USER_ID, TASK_TITLE))
                .getTask();

        service.execute(DeleteTaskRequest.of(task.getId()));
        final var foundTasks = service.execute(SearchTasksRequest.ALL);

        assertFalse(foundTasks.getTasks().contains(task));
    }
}
