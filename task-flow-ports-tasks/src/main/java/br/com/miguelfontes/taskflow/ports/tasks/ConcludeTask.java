package br.com.miguelfontes.taskflow.ports.tasks;

/**
 * Set a Task on DONE status
 *
 * @author Miguel Fontes
 */
interface ConcludeTask {

    /**
     * Defines the status of a given Task as Done.
     *
     * @param request the resolution data
     * @return a response containing the task new state
     */
    ConcludeTaskResponse execute(ConcludeTaskRequest request);
}
