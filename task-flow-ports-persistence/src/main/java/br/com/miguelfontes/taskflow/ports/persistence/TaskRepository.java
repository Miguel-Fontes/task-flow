package br.com.miguelfontes.taskflow.ports.persistence;

import br.com.miguelfontes.taskflow.core.tasks.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Provides data access operations for {@link Task} entities
 *
 * @author Miguel Fontes
 */
public interface TaskRepository {

    /**
     * Persist a {@link Task} entity
     *
     * @param task the Task to be persisted
     * @return a instance of Task with updated identifiers, if applicable
     */
    Task save(Task task);

    /**
     * Retrieves all  persisted {@link Task} entities
     *
     * @return a list containing all Task entities
     */
    List<Task> findAll();

    /**
     * Deletes a {@link Task} by it's id, if it exists
     *
     * @param id a Task uuid
     */
    void delete(UUID id);

    /**
     * Search Tasks by it's title.
     *
     * @param title the title to be searched for
     * @return a list of Tasks with titles that matches the one given as parameter
     */
    List<Task> findByTitle(String title);

    /**
     * Retrieves a Task by it's id
     *
     * @param id the id it to be searched
     * @return the found task, or Empty if none is found.
     */
    Optional<Task> findById(UUID id);
}
