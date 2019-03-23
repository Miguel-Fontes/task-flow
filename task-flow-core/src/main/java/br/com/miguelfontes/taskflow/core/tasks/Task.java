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
    private final String description;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final TaskStatus status;
    private final User author;

    private Task(UUID id, String title, String description, LocalDateTime createdAt, LocalDateTime updatedAt, TaskStatus status, User author) {
        guardIsValidTitle(title);
        guardIsValidDescription(description);
        guardIsValidAuthor(author);

        this.id = id;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.status = status;
        this.author = author;
    }

    public static Task newInstance(User author, String title) {
        return new Task(UUID.randomUUID(), title, "", LocalDateTime.now(), LocalDateTime.now(), TaskStatus.INBOX, author);
    }

    private void guardIsValidTitle(String title) {
        if (Objects.isNull(title) || title.trim().length() < 1)
            throw new IllegalArgumentException("Given Task title is invalid (null or blank");
    }

    private void guardIsValidAuthor(User author) {
        if (Objects.isNull(author))
            throw new IllegalArgumentException("Given author is null!");
    }

    private void guardIsValidDescription(String description) {
        if (Objects.isNull(description))
            throw new IllegalArgumentException("Given description is null!");
    }

    /**
     * Edit's a Task title.
     *
     * @param title the intended title of the Task
     * @return a new instance of Task with the new title
     */
    public Task editTitle(String title) {
        return new Task(id, title, description, createdAt, LocalDateTime.now(), status, author);
    }

    /**
     * Edit's a Task description.
     *
     * @param description the intended description of the Task
     * @return a new instance of Task with the new description
     */
    public Task editDescription(String description) {
        return new Task(id, title, description, createdAt, LocalDateTime.now(), status, author);
    }

    /**
     * Marks a Task as Done.
     *
     * @return a new instance of Task, with it's status as Done
     */
    public Task conclude() {
        return new Task(id, title, description, createdAt, LocalDateTime.now(), TaskStatus.DONE, author);
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

    public String getDescription() {
        return description;
    }
}
