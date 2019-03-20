package br.com.miguelfontes.taskflow.ports.persistence;

import br.com.miguelfontes.taskflow.core.tasks.Task;

import java.util.List;
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

}
