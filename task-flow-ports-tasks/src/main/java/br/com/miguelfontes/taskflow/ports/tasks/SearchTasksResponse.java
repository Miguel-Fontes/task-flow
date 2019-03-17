package br.com.miguelfontes.taskflow.ports.tasks;

import java.util.List;

/**
 * Encapsulates the data returned by a {@link SearchTasks} operation.
 *
 * @author Miguel Fontes
 */
public class SearchTasksResponse {
    private final List<TaskDTO> tasks;

    private SearchTasksResponse(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }

    public static SearchTasksResponse of(List<TaskDTO> tasks) {
        return new SearchTasksResponse(tasks);
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }
}
