package br.com.miguelfontes.taskflow.tasks.factories;

import br.com.miguelfontes.taskflow.core.tasks.Task;
import br.com.miguelfontes.taskflow.ports.tasks.TaskDTO;

/**
 * Produces TaskDTO instances
 *
 * @author Miguel Fontes
 */
public class TaskDTOFactory {

    private TaskDTOFactory() {
    }

    /**
     * Produces as TaskDTO instance from a {@link Task} instance.
     *
     * @param task a task instance
     * @return a TaskDTO instance, containing data of the given Task instance
     */
    public static TaskDTO from(Task task) {
        return TaskDTO.of(task.getId(), task.getTitle(), task.getCreatedAt(), task.getUpdatedAt(), task.getStatus().toString(), task.getAuthor().getId());
    }
}
