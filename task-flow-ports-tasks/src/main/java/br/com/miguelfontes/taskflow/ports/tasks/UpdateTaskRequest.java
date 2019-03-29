package br.com.miguelfontes.taskflow.ports.tasks;

import java.util.UUID;

/**
 * Encapsulates the data involved on a Task updates operation.
 *
 * @author Miguel Fontes
 */
public class UpdateTaskRequest {
    private final UUID id;
    private final String title;
    private final String description;

    private UpdateTaskRequest(UUID id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public static UpdateTaskRequest of(UUID id, String title, String description) {
        return new UpdateTaskRequest(id, title, description);
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "UpdateTaskRequest{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
