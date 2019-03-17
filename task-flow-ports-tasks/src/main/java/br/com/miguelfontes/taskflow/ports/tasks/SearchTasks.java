package br.com.miguelfontes.taskflow.ports.tasks;

/**
 * Fetches a Task based on given search parameters
 *
 * @author Miguel Fontes
 */
public interface SearchTasks {
    SearchTasksResponse execute(SearchTasksRequest request);
}
