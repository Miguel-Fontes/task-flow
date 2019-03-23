package br.com.miguelfontes.taskflow.persistence.mmdb;

import br.com.miguelfontes.taskflow.core.tasks.Task;
import br.com.miguelfontes.taskflow.ports.persistence.TaskRepository;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * A In Memory implementation of the {@link TaskRepository} specification, intended to be used
 * on development environments.
 *
 * @author Miguel Fontes
 */
@Repository
public class TaskRepositoryMMDB implements TaskRepository {

    private final Set<Task> tasks;

    private TaskRepositoryMMDB(Set<Task> tasks) {
        this.tasks = tasks;
    }

    public static TaskRepository instance() {
        return new TaskRepositoryMMDB(new HashSet<>());
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
        return List.copyOf(tasks);
    }

    @Override
    public void delete(UUID id) {
        tasks.removeIf(task -> id.equals(task.getId()));
    }

    @Override
    public List<Task> findByTitle(String title) {
        return tasks.stream()
                .filter(task -> title.equalsIgnoreCase(task.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Task> findById(UUID id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }
}
