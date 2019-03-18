package br.com.miguelfontes.taskflow.persistence.mmdb;

import br.com.miguelfontes.taskflow.core.tasks.Task;
import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.unmodifiableList;

/**
 * A In Memory implementation of the {@link TaskRepository} specification, intended to be used
 * on development environments.
 *
 * @author Miguel Fontes
 */
@Service
public class TaskRepositoryMMDB implements TaskRepository {

    private final List<Task> tasks;

    private TaskRepositoryMMDB(List<Task> tasks) {
        this.tasks = tasks;
    }

    public static TaskRepository instance() {
        return new TaskRepositoryMMDB(new ArrayList<>());
    }

    @Override
    public Task save(Task task) {
        return Optional.ofNullable(task)
                .map(this::persist)
                .orElseThrow(() -> new IllegalArgumentException("Given Task is null!"));
    }

    private Task persist(Task task) {
        tasks.add(task);
        return task;
    }

    @Override
    public List<Task> findAll() {
        return unmodifiableList(tasks);
    }
}
