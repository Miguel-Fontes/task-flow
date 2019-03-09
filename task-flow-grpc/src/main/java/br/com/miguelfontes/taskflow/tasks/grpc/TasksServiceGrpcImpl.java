package br.com.miguelfontes.taskflow.tasks.grpc;

import br.com.miguelfontes.taskflow.ports.tasks.CreateTask;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.TaskDTO;
import br.com.miguelfontes.taskflow.tasks.CreateTaskUseCase;
import io.grpc.stub.StreamObserver;

import java.util.UUID;


public class TasksServiceGrpcImpl extends TasksServiceGrpc.TasksServiceImplBase {

    private static final CreateTask createTask = CreateTaskUseCase.instance();

    @Override
    public void create(TasksServiceOuterClass.CreateTaskRequest request, StreamObserver<TasksServiceOuterClass.CreateTaskResponse> responseObserver) {
        super.create(request, responseObserver);

        System.out.println(request);

        CreateTaskRequest createTaskRequest = CreateTaskRequest.of(UUID.randomUUID(), request.getTitle());
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
