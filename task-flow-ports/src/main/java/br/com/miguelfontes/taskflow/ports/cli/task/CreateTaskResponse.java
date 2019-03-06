package br.com.miguelfontes.taskflow.ports.cli.task;

import br.com.miguelfontes.taskflow.core.Task;

import java.io.Serializable;

/**
 * Encapsulates the data returned by a create {@link Task} operation.
 *
 * @author Miguel Fontes
 */
public class CreateTaskResponse implements Serializable {
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
