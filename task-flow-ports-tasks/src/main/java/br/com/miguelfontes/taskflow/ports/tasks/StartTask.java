package br.com.miguelfontes.taskflow.ports.tasks;

/**
 * Selects a task for execution, updating it's status to In Progress
 *
 * @author Miguel Fontes
 */
public interface StartTask {
    StartTaskResponse execute(StartTaskRequest request);
}
