package br.com.miguelfontes.taskflow.ports.tasks;


import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Contains the data of a Task on a Serializable fashion.
 *
 * @author Miguel Fontes
 */
public final class TaskDTO {
    private final UUID id;
    private final String title;
    private final String description;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String status;
    private final UUID author;

    private TaskDTO(UUID id, String title, String description, LocalDateTime createdAt, LocalDateTime updatedAt, String status, UUID author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.author = author;
    }

    public static TaskDTO of(UUID id, String title, String description, LocalDateTime createdAt, LocalDateTime updatedAt, String status, UUID author) {
        return new TaskDTO(id, title, description, createdAt, updatedAt, status, author);
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public String getStatus() {
        return status;
    }

    public UUID getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "TaskDTO{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", status='" + status + '\'' +
                ", author=" + author +
                '}';
    }
}
