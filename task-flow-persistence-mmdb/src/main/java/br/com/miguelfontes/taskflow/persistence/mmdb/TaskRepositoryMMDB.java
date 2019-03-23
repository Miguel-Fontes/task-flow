package br.com.miguelfontes.taskflow.persistence.mmdb;

import br.com.miguelfontes.taskflow.core.tasks.Task;
import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toUnmodifiableList;

/**
 * A In Memory implementation of the {@link TaskRepository} specification, intended to be used
 * on development environments.
 *
 * @author Miguel Fontes
 */
@Repository
public class TaskRepositoryMMDB implements TaskRepository {

    private final Map<UUID, Task> tasks;

    private TaskRepositoryMMDB(Map<UUID, Task> tasks) {
        this.tasks = tasks;
    }

    public static TaskRepository instance() {
        return new TaskRepositoryMMDB(new HashMap<>());
    }

    @Override
    public Task save(Task task) {
        return Optional.ofNullable(task)
                .map(this::persist)
                .orElseThrow(() -> new IllegalArgumentException("Given Task is null!"));
    }

    private Task persist(Task task) {
        tasks.put(task.getId(), task);
        return task;
    }

    @Override
    public List<Task> findAll() {
        return tasks.values().stream()
                .collect(toUnmodifiableList());
    }

    @Override
    public void delete(UUID id) {
        tasks.remove(id);
    }

    @Override
    public List<Task> findByTitle(String title) {
        return tasks.values().stream()
                .filter(task -> title.equalsIgnoreCase(task.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return Optional.ofNullable(tasks.get(id));
    }
}
