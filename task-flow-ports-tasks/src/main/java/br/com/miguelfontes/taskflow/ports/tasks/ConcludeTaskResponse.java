package br.com.miguelfontes.taskflow.ports.tasks;

/**
 * Encapsulates the data returned from a conclude Task operation.
 *
 * @author Miguel Fontes
 */
public class ConcludeTaskResponse {
    private final TaskDTO task;

    private ConcludeTaskResponse(TaskDTO task) {
        this.task = task;
    }

    public static ConcludeTaskResponse of(TaskDTO task) {
        return new ConcludeTaskResponse(task);
    }

    public TaskDTO getTask() {
        return task;
    }
}
