package br.com.miguelfontes.taskflow.persistence;

import br.com.miguelfontes.taskflow.core.tasks.Task;
import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StubTaskRepository implements TaskRepository {
    private final List<Task> tasks;

    private StubTaskRepository(List<Task> tasks) {
        this.tasks = tasks;
    }

    public static StubTaskRepository instance() {
        return new StubTaskRepository(new ArrayList<>());
    }

    @Override
    public Task save(Task task) {
        tasks.add(task);
        return task;
    }

    @Override
    public List<Task> findAll() {
        return tasks;
    }

    @Override
    public void delete(UUID id) {
        tasks.removeIf(task -> id.equals(task.getId()));
    }
}
