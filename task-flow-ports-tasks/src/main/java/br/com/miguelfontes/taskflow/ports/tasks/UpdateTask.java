package br.com.miguelfontes.taskflow.ports.tasks;

/**
 * Defines a Task update operation.
 *
 * @author Miguel Fontes
 */
interface UpdateTask {
    UpdateTaskResponse execute(UpdateTaskRequest request);
}
