package br.com.miguelfontes.taskflow.core;

import java.time.LocalDateTime;

/**
 * A user of the application
 *
 * @author Miguel Fontes
 */
public class User {

    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private User(String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static User newInstance(String name) {
        return new User(name, LocalDateTime.now(), LocalDateTime.now());
    }

    public Task createTask(String title) {
        return Task.newInstance(this, title);
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
