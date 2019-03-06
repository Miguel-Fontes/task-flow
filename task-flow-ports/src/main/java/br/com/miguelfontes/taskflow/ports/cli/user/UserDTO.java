package br.com.miguelfontes.taskflow.ports.cli.user;

import br.com.miguelfontes.taskflow.core.User;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Contains the data of a {@link User} on a Serializable fashion
 *
 * @author Miguel Fontes
 */
public class UserDTO {
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

    public static UserDTO from(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getCreatedAt(), user.getUpdatedAt());
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
