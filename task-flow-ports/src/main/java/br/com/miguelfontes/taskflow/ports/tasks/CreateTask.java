package br.com.miguelfontes.taskflow.ports.tasks;

import br.com.miguelfontes.taskflow.core.tasks.Task;

/**
 * Creates a new {@link Task}.
 *
 * @author Miguel Fontes
 */
public interface CreateTask {
    CreateTaskResponse execute(CreateTaskRequest request);
}
