package br.com.miguelfontes.taskflow.core.tasks;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * A user of the application
 *
 * @author Miguel Fontes
 */
public final class User {
    private final UUID id;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private User(UUID id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static User newInstance(String name) {
        guardIsValidName(name);
        return new User(UUID.randomUUID(), name, LocalDateTime.now(), LocalDateTime.now());
    }

    private static void guardIsValidName(String name) {
        if (Objects.isNull(name) || name.trim().length() < 3)
            throw new IllegalArgumentException("Given name [%s] is not valid (null or length below 5)!");
    }

    public Task createTask(String title) {
        return Task.newInstance(this, title);
    }

    public UUID getId() {
        return id;
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
