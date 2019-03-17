package br.com.miguelfontes.taskflow.tasks.grpc;

import br.com.miguelfontes.taskflow.ports.tasks.*;
import io.grpc.stub.StreamObserver;

import java.util.UUID;

import static java.util.stream.Collectors.toList;


public class TasksServiceGrpcImpl extends TasksServiceGrpc.TasksServiceImplBase {

    private final CreateTask createTask;

    private final SearchTasks searchTasks;

    private TasksServiceGrpcImpl(CreateTask createTask, SearchTasks searchTasks) {
        this.createTask = createTask;
        this.searchTasks = searchTasks;
    }

    static TasksServiceGrpc.TasksServiceImplBase instance(CreateTask createTask, SearchTasks searchTasks) {
        return new TasksServiceGrpcImpl(createTask, searchTasks);
    }

    @Override
    public void create(TasksServiceOuterClass.CreateTaskRequest request, StreamObserver<TasksServiceOuterClass.CreateTaskResponse> responseObserver) {
        CreateTaskRequest createTaskRequest = CreateTaskRequest.of(UUID.fromString(request.getUserId()), request.getTitle());
        TaskDTO task = createTask.execute(createTaskRequest).getTask();

        var response = buildCreateTaskResponse(task);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private TasksServiceOuterClass.CreateTaskResponse buildCreateTaskResponse(TaskDTO task) {
        return TasksServiceOuterClass.CreateTaskResponse.newBuilder()
                .setAuthor(task.getAuthor().toString())
                .setId(task.getId().toString())
                .setTitle(task.getTitle())
                .setCreatedAt(task.getCreatedAt().toString())
                .setUpdatedAt(task.getUpdatedAt().toString())
                .setStatus(task.getStatus())
                .build();
    }

    @Override
    public void list(TasksServiceOuterClass.SearchTasksRequest request, StreamObserver<TasksServiceOuterClass.SearchTasksResponse> responseObserver) {
        var searchTasksRequest = SearchTasksRequest.builder()
                .title(request.getTitle())
                .build();

        var foundTasks = searchTasks.execute(searchTasksRequest)
                .getTasks()
                .stream()
                .map(this::buildCreateTaskResponse)
                .collect(toList());

        var response = TasksServiceOuterClass.SearchTasksResponse.newBuilder()
                .addAllTasks(foundTasks)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
