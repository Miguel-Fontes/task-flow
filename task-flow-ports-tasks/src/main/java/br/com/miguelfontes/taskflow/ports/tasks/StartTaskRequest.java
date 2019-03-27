package br.com.miguelfontes.taskflow.ports.tasks;

import java.util.UUID;

/**
 * Encapsulates the data request to start a Task execution.
 *
 * @author Miguel Fontes
 */
public class StartTaskRequest {
    private final UUID taskId;

    private StartTaskRequest(UUID taskId) {
        this.taskId = taskId;
    }

    public static StartTaskRequest of(UUID taskId) {
        return new StartTaskRequest(taskId);
    }

    public UUID getTaskId() {
        return taskId;
    }

    @Override
    public String toString() {
        return "StartTaskRequest{" +
                "taskId=" + taskId +
                '}';
    }
}
