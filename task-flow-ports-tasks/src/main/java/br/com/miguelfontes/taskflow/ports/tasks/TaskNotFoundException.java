package br.com.miguelfontes.taskflow.ports.tasks;

import java.util.UUID;

/**
 * Represents that a Task was not found with the given criteria.
 *
 * @author Miguel Fontes
 */
public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(UUID id) {
        super(String.format("Task with id [%s] was not found!", id));
    }
}
