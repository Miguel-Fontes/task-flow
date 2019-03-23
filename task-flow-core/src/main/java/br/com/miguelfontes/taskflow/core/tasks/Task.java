package br.com.miguelfontes.taskflow.core.tasks;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Represents a task on the domain
 *
 * @author Miguel Fontes
 */
public final class Task {
    private final UUID id;
    private final String title;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final TaskStatus status;
    private final User author;

    private Task(UUID id, String title, LocalDateTime createdAt, LocalDateTime updatedAt, TaskStatus status, User author) {
        guardIsValidTitle(title);
        guardIsValidAuthor(author);

        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.author = author;
    }

    private void guardIsValidTitle(String title) {
        if (Objects.isNull(title) || title.trim().length() < 1)
            throw new IllegalArgumentException("Given Task title is invalid (null or blank");
    }

    private void guardIsValidAuthor(User author) {
        if (Objects.isNull(author))
            throw new IllegalArgumentException("Given author is null!");
    }

    public static Task newInstance(User author, String title) {
        return new Task(UUID.randomUUID(), title, LocalDateTime.now(), LocalDateTime.now(), TaskStatus.INBOX, author);
    }

    public Task withTitle(String title) {
        return new Task(id, title, createdAt, LocalDateTime.now(), status, author);
    }

    public Task withStatus(TaskStatus status) {
        return new Task(id, title, createdAt, LocalDateTime.now(), status, author);
    }

    public Task withAuthor(User author) {
        return new Task(id, title, createdAt, LocalDateTime.now(), status, author);
    }

    public UUID getId() {
        return id;
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
