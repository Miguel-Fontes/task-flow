package br.com.miguelfontes.taskflow.ports.tasks;

import java.util.UUID;

/**
 * Encapsulates the data required to conclude a Task.
 *
 * @author Miguel Fontes
 */
public class ConcludeTaskRequest {
    private final UUID id;

    private ConcludeTaskRequest(UUID id) {
        this.id = id;
    }

    public static ConcludeTaskRequest of(UUID id) {
        return new ConcludeTaskRequest(id);
    }

    public UUID getId() {
        return id;
    }

}
