package br.com.miguelfontes.taskflow.core.tasks;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a task on the domain
 *
 * @author Miguel Fontes
 */
public final class Task {
    private final String title;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final TaskStatus status;
    private final User author;

    private Task(String title, LocalDateTime createdAt, LocalDateTime updatedAt, TaskStatus status, User author) {
        guardIsValidTitle(title);

        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.author = author;
    }

    private void guardIsValidTitle(String title) {
        if (Objects.isNull(title) || title.trim().length() < 1)
            throw new IllegalArgumentException("Given Task title [%s] is invalid (null or length below 1)");
    }

    public static Task newInstance(User author, String title) {
        return new Task(title, LocalDateTime.now(), LocalDateTime.now(), TaskStatus.INBOX, author);
    }

    public String getTitle() {
        return title;
    }

    public User getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public TaskStatus getStatus() {
        return status;
    }
}
