package br.com.miguelfontes.taskflow.ports.tasks;

/**
 * The external API of the Tasks module.
 *
 * @author Miguel Fontes
 */
public interface TasksAPI extends CreateTask, SearchTasks, DeleteTask, UpdateTask, ConcludeTask {
}
