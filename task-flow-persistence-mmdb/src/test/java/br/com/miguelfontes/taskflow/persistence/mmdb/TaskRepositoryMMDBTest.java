package br.com.miguelfontes.taskflow.persistence.mmdb;

import br.com.miguelfontes.taskflow.core.tasks.Task;
import br.com.miguelfontes.taskflow.core.tasks.User;
import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("task repository mmdb")
class TaskRepositoryMMDBTest {

    private static final String TASK_TITLE = "a task title";
    private static final String USER_NAME = "a user name";
    private static final User USER = User.newInstance(USER_NAME);
    private static final Task TASK = Task.newInstance(USER, TASK_TITLE);

    private final TaskRepository repository = TaskRepositoryMMDB.instance();

    @Test
    @DisplayName("should persist a task")
    void shouldSaveATask() {
        var task = repository.save(TASK);

        assertEquals(TASK, task);
    }

    @Test
    @DisplayName("should retrieve all persisted tasks")
    void shouldRetrieveAllPersistedTasks() {
        var task = repository.save(TASK);
        var tasks = repository.findAll();

        assertTrue(tasks.contains(task));
    }

    @Test
    @DisplayName("should return empty list when there is no persisted task")
    void shouldReturnEmptyListWhenThereIsNoPersistedTask() {
        List<Task> tasks = repository.findAll();

        assertTrue(tasks.isEmpty());
    }

    @Test
    @DisplayName("should throw exception if task is null")
    void shouldThrowExceptionIfTaskIsNull() {
        assertThrows(IllegalArgumentException.class, () -> repository.save(null));
    }

    @Test
    @DisplayName("should delete a task")
    void shouldDeleteATask() {
        var task = repository.save(TASK);

        repository.delete(task.getId());
        var tasks = repository.findAll();

        assertFalse(tasks.contains(task));
    }
}