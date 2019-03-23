package br.com.miguelfontes.taskflow.ports.tasks;

/**
 * Encapsulates the data returned by a Update task response
 *
 * @author Miguel Fontes
 */
public class UpdateTaskResponse {

    private final TaskDTO task;

    private UpdateTaskResponse(TaskDTO task) {
        this.task = task;
    }

    public static UpdateTaskResponse of(TaskDTO task) {
        return new UpdateTaskResponse(task);
    }

    public TaskDTO getTask() {
        return task;
    }

}
