package br.com.miguelfontes.taskflow.tasks.grpc;

import br.com.miguelfontes.taskflow.ports.tasks.CreateTask;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTaskRequest;
import br.com.miguelfontes.taskflow.ports.tasks.CreateTaskResponse;
import br.com.miguelfontes.taskflow.ports.tasks.SearchTasks;
import br.com.miguelfontes.taskflow.ports.tasks.SearchTasksRequest;
import br.com.miguelfontes.taskflow.ports.tasks.SearchTasksResponse;
import br.com.miguelfontes.taskflow.ports.tasks.TaskDTO;
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

    private final CreateTask createTask;

    private final SearchTasks searchTasks;

    @Autowired
    public TasksServiceGrpcImpl(CreateTask createTask, SearchTasks searchTasks) {
        this.createTask = createTask;
        this.searchTasks = searchTasks;
    }

    static TasksServiceGrpc.TasksServiceImplBase instance(CreateTask createTask, SearchTasks searchTasks) {
        return new TasksServiceGrpcImpl(createTask, searchTasks);
    }

    @Override
    public void create(TasksServiceOuterClass.CreateTaskRequest request, StreamObserver<TasksServiceOuterClass.CreateTaskResponse> responseObserver) {
        var response = Optional.of(request)
                .map(this::buildCreateTaskRequest)
                .map(createTask::execute)
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
                .map(searchTasks::execute)
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

}
