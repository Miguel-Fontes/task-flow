package br.com.miguelfontes.taskflow.tasks.grpc;

import br.com.miguelfontes.taskflow.ports.tasks.CreateTask;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.TaskDTO;
import br.com.miguelfontes.taskflow.tasks.CreateTaskUseCase;
import io.grpc.stub.StreamObserver;

import java.util.UUID;


public class TasksServiceGrpcImpl extends TasksServiceGrpc.TasksServiceImplBase {

    private static final CreateTask createTask = CreateTaskUseCase.instance();

    private TasksServiceGrpcImpl() {
    }

    public static TasksServiceGrpc.TasksServiceImplBase instance() {
        return new TasksServiceGrpcImpl();
    }

    @Override
    public void create(TasksServiceOuterClass.CreateTaskRequest request, StreamObserver<TasksServiceOuterClass.CreateTaskResponse> responseObserver) {
        CreateTaskRequest createTaskRequest = CreateTaskRequest.of(UUID.fromString(request.getUserId()), request.getTitle());
        TaskDTO task = createTask.execute(createTaskRequest).getTask();

        TasksServiceOuterClass.CreateTaskResponse response = TasksServiceOuterClass.CreateTaskResponse.newBuilder()
                .setAuthor(task.getAuthor().toString())
                .setId(task.getId().toString())
                .setTitle(task.getTitle())
                .setCreatedAt(task.getCreatedAt().toString())
                .setUpdatedAt(task.getUpdatedAt().toString())
                .setStatus(task.getStatus())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
