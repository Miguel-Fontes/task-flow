package br.com.miguelfontes.taskflow.ports.cli.task;

/**
 * Creates a new {@link br.com.miguelfontes.taskflow.core.Task}.
 *
 * @author Miguel Fontes
 */
public interface CreateTask {
    CreateTaskResponse execute(CreateTaskRequest request);
}
