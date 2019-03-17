package br.com.miguelfontes.taskflow.ports.tasks;

/**
 * Fetches {@link br.com.miguelfontes.taskflow.core.tasks.Task} based on given search parameters
 *
 * @author Miguel Fontes
 */
public interface SearchTasks {
    SearchTasksResponse execute(SearchTasksRequest request);
}
