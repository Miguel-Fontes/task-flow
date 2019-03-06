package br.com.miguelfontes.taskflow.core;

import java.time.LocalDateTime;

/**
 * Represents a task on the domain
 *
 * @author Miguel Fontes
 */
public class Task {
    private final String title;
    private final LocalDateTime createdAt;
    private final TaskStatus status;
    private final User author;

    private Task(String title, LocalDateTime createdAt, TaskStatus status, User author) {
        this.title = title;
        this.createdAt = createdAt;
        this.status = status;
        this.author = author;
    }

    public static Task newInstance(User author, String title) {
        return new Task(title, LocalDateTime.now(), TaskStatus.INBOX, author);
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

    public TaskStatus getStatus() {
        return status;
    }
}
