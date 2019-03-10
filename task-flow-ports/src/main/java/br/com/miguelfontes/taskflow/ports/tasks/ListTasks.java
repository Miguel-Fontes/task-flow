package br.com.miguelfontes.taskflow.ports.tasks;

/**
 * Fetches all available {@link br.com.miguelfontes.taskflow.core.tasks.Task}
 *
 * @author Miguel Fontes
 */
public interface ListTasks {
    ListTasksResponse execute();
}
