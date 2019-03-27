package br.com.miguelfontes.taskflow.ports.tasks;

/**
 * Encapsulates the response data of a Start task response.
 *
 * @author Miguel Fontes
 */
public class StartTaskResponse {
    private final TaskDTO task;

    public StartTaskResponse(TaskDTO task) {
        this.task = task;
    }

    public static final StartTaskResponse of(TaskDTO task) {
        return new StartTaskResponse(task);
    }

    public TaskDTO getTask() {
        return task;
    }

    @Override
    public String toString() {
        return "StartTaskResponse{" +
                "task=" + task +
                '}';
    }
}
