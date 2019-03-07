package br.com.miguelfontes.tasks;

import br.com.miguelfontes.taskflow.ports.tasks.CreateTask;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTaskResponse;

/**
 * Implements the {@link CreateTask} port, defining the {@link br.com.miguelfontes.taskflow.core.tasks.Task} creation
 * operation
 *
 * @author  Miguel Fontes
 */
public final class CreateTaskUseCase implements CreateTask {
    @Override
    public CreateTaskResponse execute(CreateTaskRequest request) {
        return null;
    }
}
