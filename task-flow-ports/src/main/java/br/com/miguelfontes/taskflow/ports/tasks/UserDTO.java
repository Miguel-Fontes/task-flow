package br.com.miguelfontes.taskflow.ports.tasks;

import br.com.miguelfontes.taskflow.core.tasks.User;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Contains the data of a {@link User} on a Serializable fashion
 *
 * @author Miguel Fontes
 */
public final class UserDTO {
    private final UUID id;
    private final String name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private UserDTO(UUID id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    static UserDTO from(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getCreatedAt(), user.getUpdatedAt());
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
