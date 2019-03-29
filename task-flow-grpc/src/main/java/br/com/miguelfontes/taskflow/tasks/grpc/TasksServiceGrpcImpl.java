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
import java.util.UUID;
import java.util.function.Function;
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
        var response = buildCreateTaskRequest()
                .andThen(api::execute)
                .andThen(CreateTaskResponse::getTask)
                .andThen(this::toOuterTask)
                .andThen(this::buildTaskResponse)
                .apply(request);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private Function<TasksServiceOuterClass.CreateTaskRequest, CreateTaskRequest> buildCreateTaskRequest() {
        return request -> CreateTaskRequest.of(UUID.fromString(request.getUserId()), request.getTitle());
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
        var response = buildUpdateTaskRequest()
                .andThen(api::execute)
                .andThen(UpdateTaskResponse::getTask)
                .andThen(this::toOuterTask)
                .andThen(this::buildUpdateTaskResponse)
                .apply(request);

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }


    private Function<TasksServiceOuterClass.UpdateTaskRequest, UpdateTaskRequest> buildUpdateTaskRequest() {
        return request -> UpdateTaskRequest.of(
                UUID.fromString(request.getId()),
                request.getTitle(),
                request.getDescription()
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
            final var response = buildConcludeTaskRequest()
                    .andThen(api::execute)
                    .andThen(ConcludeTaskResponse::getTask)
                    .andThen(this::toOuterTask)
                    .andThen(this::buildConcludeTaskResponse)
                    .apply(request);

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (TaskNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .withCause(e)
                    .asRuntimeException());
        }
    }

    private Function<TasksServiceOuterClass.ConcludeTaskRequest, ConcludeTaskRequest> buildConcludeTaskRequest() {
        return request -> ConcludeTaskRequest.of(UUID.fromString(request.getId()));
    }

    private TasksServiceOuterClass.ConcludeTaskResponse buildConcludeTaskResponse(TasksServiceOuterClass.Task task) {
        return TasksServiceOuterClass.ConcludeTaskResponse.newBuilder()
                .setTask(task)
                .build();
    }

    @Override
    public void start(TasksServiceOuterClass.StartTaskRequest request, StreamObserver<TasksServiceOuterClass.StartTaskResponse> responseObserver) {
        try {
            var response = buildStartTaskRequest()
                    .andThen(api::execute)
                    .andThen(StartTaskResponse::getTask)
                    .andThen(this::toOuterTask)
                    .andThen(this::buildStartTaskResponse)
                    .apply(request);

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (TaskNotFoundException e) {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription(e.getMessage())
                    .withCause(e)
                    .asRuntimeException());
        }
    }

    private Function<TasksServiceOuterClass.StartTaskRequest, StartTaskRequest> buildStartTaskRequest() {
        return request -> StartTaskRequest.of(UUID.fromString(request.getId()));
    }

    private TasksServiceOuterClass.StartTaskResponse buildStartTaskResponse(TasksServiceOuterClass.Task task) {
        return TasksServiceOuterClass.StartTaskResponse.newBuilder()
                .setTask(task)
                .build();
    }
}
