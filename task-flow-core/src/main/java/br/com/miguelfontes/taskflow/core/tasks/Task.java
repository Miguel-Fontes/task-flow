package br.com.miguelfontes.taskflow.core.tasks;

import java.time.LocalDateTime;

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
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.author = author;
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
