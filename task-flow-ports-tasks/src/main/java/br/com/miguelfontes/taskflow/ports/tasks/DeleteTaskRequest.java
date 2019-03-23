package br.com.miguelfontes.taskflow.ports.tasks;

import java.util.UUID;

/**
 * Encapsulates the data needed to request a Task deletion.
 *
 * @author Miguel Fontes
 */
public class DeleteTaskRequest {
    private final UUID id;

    private DeleteTaskRequest(UUID id) {
        this.id = id;
    }

    public static DeleteTaskRequest of(UUID id) {
        return new DeleteTaskRequest(id);
    }

    public UUID getId() {
        return id;
    }
}
