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

    public static CreateTaskResponse from(Task task) {
        return new CreateTaskResponse(TaskDTO.from(task));
    }

    public static CreateTaskResponse of(TaskDTO taskDTO) {
        return new CreateTaskResponse(taskDTO);
    }

    public TaskDTO getTask() {
        return task;
    }
}
