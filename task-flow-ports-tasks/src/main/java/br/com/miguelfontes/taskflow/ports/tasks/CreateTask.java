package br.com.miguelfontes.taskflow.ports.tasks;

/**
 * Creates a new Task
 *
 * @author Miguel Fontes
 */
public interface CreateTask {
    CreateTaskResponse execute(CreateTaskRequest request);
}
