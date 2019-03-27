package br.com.miguelfontes.taskflow.tasks.grpc;

import br.com.miguelfontes.taskflow.ports.tasks.ConcludeTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.ConcludeTaskResponse;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTaskResponse;
import br.com.miguelfontes.taskflow.ports.tasks.DeleteTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.SearchTasksRequest;
import br.com.miguelfontes.taskflow.ports.tasks.SearchTasksResponse;
import br.com.miguelfontes.taskflow.ports.tasks.StartTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.StartTaskResponse;
import br.com.miguelfontes.taskflow.ports.tasks.TaskDTO;
import br.com.miguelfontes.taskflow.ports.tasks.TaskNotFoundException;
import br.com.miguelfontes.taskflow.ports.tasks.TasksAPI;
import br.com.miguelfontes.taskflow.ports.tasks.UpdateTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.UpdateTaskResponse;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;


/**
 * Implements the gRPC Tasks service, exposing the core Task Domain features the external clients
 *
 * @author Miguel Fontes
 */
@Service
public class TasksServiceGrpcImpl extends TasksServiceGrpc.TasksServiceImplBase {
    private final TasksAPI api;

    @Autowired
    public TasksServiceGrpcImpl(TasksAPI api) {
        this.api = api;
    }

    static TasksServiceGrpc.TasksServiceImplBase instance(TasksAPI api) {
        return new TasksServiceGrpcImpl(api);
    }

    @Override
    public void create(TasksServiceOuterClass.CreateTaskRequest request, StreamObserver<TasksServiceOuterClass.CreateTaskResponse> responseObserver) {
        var response = Optional.of(request)
                .map(this::buildCreateTaskRequest)
                .map(api::execute)
                .map(CreateTaskResponse::getTask)
                .map(this::toOuterTask)
                .map(this::buildTaskResponse)
                .orElseThrow();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private CreateTaskRequest buildCreateTaskRequest(TasksServiceOuterClass.CreateTaskRequest req) {
        return CreateTaskRequest.of(UUID.fromString(req.getUserId()), req.getTitle());
    }

    private TasksServiceOuterClass.Task toOuterTask(TaskDTO task) {
        return TasksServiceOuterClass.Task.newBuilder()
                .setAuthor(task.getAuthor().toString())
                .setId(task.getId().toString())
                .setTitle(task.getTitle())
                .setDescription(task.getDescription())
                .setCreatedAt(task.getCreatedAt().toString())
                .setUpdatedAt(task.getUpdatedAt().toString())
                .setStatus(task.getStatus())
                .build();
    }

    private TasksServiceOuterClass.CreateTaskResponse buildTaskResponse(TasksServiceOuterClass.Task grpcTask) {
        return TasksServiceOuterClass.CreateTaskResponse.newBuilder()
                .setTask(grpcTask)
                .build();
    }

    @Override
    public void search(TasksServiceOuterClass.SearchTasksRequest request, StreamObserver<TasksServiceOuterClass.SearchTasksResponse> responseObserver) {
        var response = Stream.of(request)
                .map(this::buildSearchTaskRequest)
                .map(api::execute)
                .map(SearchTasksResponse::getTasks)
                .flatMap(Collection::stream)
                .map(this::toOuterTask)
                .collect(Collectors.collectingAndThen(
                        toList(),
                        this::buildSearchTaskResponse
                ));

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private SearchTasksRequest buildSearchTaskRequest(TasksServiceOuterClass.SearchTasksRequest request) {
        return SearchTasksRequest.builder()
                .title(request.getTitle())
                .build();
    }

    private TasksServiceOuterClass.SearchTasksResponse buildSearchTaskResponse(List<TasksServiceOuterClass.Task> foundTasks) {
        return TasksServiceOuterClass.SearchTasksResponse.newBuilder()
                .addAllTasks(foundTasks)
                .build();
    }

    @Override
    public void delete(TasksServiceOuterClass.DeleteTaskRequest request, StreamObserver<TasksServiceOuterClass.DeleteTaskResponse> responseObserver) {
        Stream.of(request)
                .map(TasksServiceOuterClass.DeleteTaskRequest::getUuid)
                .map(UUID::fromString)
                .map(DeleteTaskRequest::of)
                .forEach(api::execute);

        responseObserver.onNext(TasksServiceOuterClass.DeleteTaskResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void update(TasksServiceOuterClass.UpdateTaskRequest request, StreamObserver<TasksServiceOuterClass.UpdateTaskResponse> responseObserver) {
        var response = Optional.of(request)
                .map(this::buildUpdateTaskRequest)
                .map(api::execute)
                .map(UpdateTaskResponse::getTask)
                .map(this::toOuterTask)
                .map(this::buildUpdateTaskResponse)
                .orElse(TasksServiceOuterClass.UpdateTaskResponse.newBuilder().build());

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


    private UpdateTaskRequest buildUpdateTaskRequest(TasksServiceOuterClass.UpdateTaskRequest request) {
        return UpdateTaskRequest.of(
                UUID.fromString(request.getId()),
                request.getTitle(),
                request.getDescription(),
                request.getStatus(),
                UUID.fromString(request.getAuthor())
        );
    }

    private TasksServiceOuterClass.UpdateTaskResponse buildUpdateTaskResponse(TasksServiceOuterClass.Task task) {
        return TasksServiceOuterClass.UpdateTaskResponse.newBuilder()
                .setTask(task)
                .build();
    }

    @Override
    public void conclude(TasksServiceOuterClass.ConcludeTaskRequest request, StreamObserver<TasksServiceOuterClass.ConcludeTaskResponse> responseObserver) {
        try {
            var response = Optional.of(request)
                    .map(TasksServiceOuterClass.ConcludeTaskRequest::getId)
                    .map(UUID::fromString)
                    .map(ConcludeTaskRequest::of)
                    .map(api::execute)
                    .map(ConcludeTaskResponse::getTask)
                    .map(this::toOuterTask)
                    .map(this::buildConcludeTaskResponse)
                    .orElseThrow();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (TaskNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .withCause(e)
                    .asRuntimeException());
        }
    }

    private TasksServiceOuterClass.ConcludeTaskResponse buildConcludeTaskResponse(TasksServiceOuterClass.Task task) {
        return TasksServiceOuterClass.ConcludeTaskResponse.newBuilder()
                .setTask(task)
                .build();
    }

    @Override
    public void start(TasksServiceOuterClass.StartTaskRequest request, StreamObserver<TasksServiceOuterClass.StartTaskResponse> responseObserver) {
        try {
            var response = Optional.of(request)
                    .map(TasksServiceOuterClass.StartTaskRequest::getId)
                    .map(UUID::fromString)
                    .map(StartTaskRequest::of)
                    .map(api::execute)
                    .map(StartTaskResponse::getTask)
                    .map(this::toOuterTask)
                    .map(this::buildStartTaskResponse)
                    .orElseThrow();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (TaskNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .withCause(e)
                    .asRuntimeException());
        }
    }

    private TasksServiceOuterClass.StartTaskResponse buildStartTaskResponse(TasksServiceOuterClass.Task task) {
        return TasksServiceOuterClass.StartTaskResponse.newBuilder()
                .setTask(task)
                .build();
    }
}
