package br.com.miguelfontes.taskflow.ports.tasks;

import br.com.miguelfontes.taskflow.core.tasks.Task;

/**
 * Encapsulates the data returned by a create {@link Task} operation.
 *
 * @author Miguel Fontes
 */
public final class CreateTaskResponse {
    private final TaskDTO task;

    private CreateTaskResponse(TaskDTO task) {
        this.task = task;
    }

    public static CreateTaskResponse of(TaskDTO task) {
        return new CreateTaskResponse(task);
    }

    public TaskDTO getTask() {
        return task;
    }
}
