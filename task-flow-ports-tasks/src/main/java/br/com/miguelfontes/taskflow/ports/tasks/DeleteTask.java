package br.com.miguelfontes.taskflow.ports.tasks;

/**
 * Deletes a Task based on it's id
 *
 * @author Miguel Fontes
 */
interface DeleteTask {
    void execute(DeleteTaskRequest request);
}
