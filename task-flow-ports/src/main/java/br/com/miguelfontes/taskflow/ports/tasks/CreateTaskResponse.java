package br.com.miguelfontes.taskflow.ports.tasks;

import br.com.miguelfontes.taskflow.core.tasks.Task;

import java.io.Serializable;

/**
 * Encapsulates the data returned by a create {@link Task} operation.
 *
 * @author Miguel Fontes
 */
public final class CreateTaskResponse implements Serializable {
    private final TaskDTO task;

    private CreateTaskResponse(TaskDTO task) {
        this.task = task;
    }

    public static CreateTaskResponse from(Task task) {
        return new CreateTaskResponse(TaskDTO.from(task));
    }

    public TaskDTO getTask() {
        return task;
    }
}
