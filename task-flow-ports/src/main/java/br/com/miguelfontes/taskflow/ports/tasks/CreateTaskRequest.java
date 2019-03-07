package br.com.miguelfontes.taskflow.ports.tasks;

import br.com.miguelfontes.taskflow.core.tasks.Task;

import java.util.UUID;

/**
 * Encapsulates the data involved on a {@link Task} creation request.
 *
 * @author Miguel Fontes
 */
public final class CreateTaskRequest {
    private final UUID userId;
    private final String title;

    private CreateTaskRequest(UUID userId, String title) {
        this.userId = userId;
        this.title = title;
    }

    public static CreateTaskRequest of(UUID userId, String title) {
        return new CreateTaskRequest(userId, title);
    }

    public UUID getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }
}
