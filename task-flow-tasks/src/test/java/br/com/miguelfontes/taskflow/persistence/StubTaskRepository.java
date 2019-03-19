package br.com.miguelfontes.taskflow.persistence;

import br.com.miguelfontes.taskflow.core.tasks.Task;
import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;

import java.util.ArrayList;
import java.util.List;

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
}
