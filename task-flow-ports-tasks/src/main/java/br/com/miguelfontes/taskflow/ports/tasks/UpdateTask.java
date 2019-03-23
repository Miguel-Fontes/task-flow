package br.com.miguelfontes.taskflow.ports.tasks;

/**
 * Defines a Task update operation.
 *
 * @author Miguel Fontes
 */
public interface UpdateTask {
    UpdateTaskResponse execute(UpdateTaskRequest request);
}
