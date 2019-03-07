package br.com.miguelfontes.taskflow.cli.commands;

import br.com.miguelfontes.taskflow.cli.Command;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTask;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTaskResponse;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import java.util.UUID;

/**
 * Defines the arguments of a Add Task command
 *
 * @author Miguel Fontes
 */
@Parameters(commandNames = "add-task", commandDescription = "Adds a new task")
public final class AddTaskCommand implements Command {

    private final CreateTask createTask;

    private AddTaskCommand(CreateTask createTask) {
        this.createTask = createTask;
    }

    public static AddTaskCommand instance(CreateTask createTask) {
        return new AddTaskCommand(createTask);
    }

    @Parameter(description = "The Task's title ")
    private String title;

    public void execute() {
        System.out.println(String.format("You've entered the title [%s]...", title));
        System.out.println("Creating new task...");

        CreateTaskRequest request = CreateTaskRequest.of(UUID.randomUUID(), title);
        CreateTaskResponse response = createTask.execute(request);

        System.out.println(String.format("Created task: [%s]", response.getTask().toString()));
    }
}
