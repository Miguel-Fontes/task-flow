package br.com.miguelfontes.taskflow.ports.tasks;

/**
 * Creates a new Task
 *
 * @author Miguel Fontes
 */
interface CreateTask {
    CreateTaskResponse execute(CreateTaskRequest request);
}
