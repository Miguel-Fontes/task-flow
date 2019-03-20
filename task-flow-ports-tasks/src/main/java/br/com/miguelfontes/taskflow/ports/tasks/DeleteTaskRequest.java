package br.com.miguelfontes.taskflow.ports.tasks;

/**
 * Encapsulates the data needed to request a Task deletion.
 *
 * @author Miguel Fontes
 */
public class DeleteTaskRequest {
    private final String id;

    private DeleteTaskRequest(String id) {
        this.id = id;
    }

    public static DeleteTaskRequest of(String id) {
        return new DeleteTaskRequest(id);
    }

    public String getId() {
        return id;
    }
}
