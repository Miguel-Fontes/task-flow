package br.com.miguelfontes.taskflow.ports.tasks;

import br.com.miguelfontes.taskflow.core.tasks.Task;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Contains the data of a {@link Task} on a Serializable fashion.
 *
 * @author Miguel Fontes
 */
public class TaskDTO {
    private final String title;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final String status;
    private final UUID author;

    private TaskDTO(String title, LocalDateTime createdAt, LocalDateTime updatedAt, String status, UUID author) {
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.author = author;
    }

    public static TaskDTO from(Task task) {
        return new TaskDTO(task.getTitle(), task.getCreatedAt(), task.getUpdatedAt(), task.getStatus().toString(), task.getAuthor().getId());
    }

    public String getTitle() {
        return title;
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
}