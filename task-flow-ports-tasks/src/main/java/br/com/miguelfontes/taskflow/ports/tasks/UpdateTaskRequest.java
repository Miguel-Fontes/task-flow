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
    private final String status;
    private final UUID author;

    private UpdateTaskRequest(UUID id, String title, String status, UUID author) {
        this.id = id;
        this.title = title;
        this.status = status;
        this.author = author;
    }

    public static UpdateTaskRequest of(UUID id, String title, String status, UUID author) {
        return new UpdateTaskRequest(id, title, status, author);
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getStatus() {
        return status;
    }

    public UUID getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "UpdateTaskRequest{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", author=" + author +
                '}';
    }
}
