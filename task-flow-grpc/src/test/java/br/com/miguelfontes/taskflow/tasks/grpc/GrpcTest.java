package br.com.miguelfontes.taskflow.tasks.grpc;

import br.com.miguelfontes.taskflow.persistence.mmdb.TaskRepositoryMMDB;
import br.com.miguelfontes.taskflow.tasks.CreateTaskUseCase;
import br.com.miguelfontes.taskflow.tasks.SearchTasksUseCase;
import io.grpc.ManagedChannel;
import io.grpc.Server;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

class GrpcTest {
    static TasksServiceGrpc.TasksServiceBlockingStub stub;
    private static Server server;
    private static ManagedChannel channel;
    private static String serverName = InProcessServerBuilder.generateName();
    private static InProcessServerBuilder serverBuilder = InProcessServerBuilder
            .forName(serverName).directExecutor();
    private static InProcessChannelBuilder channelBuilder = InProcessChannelBuilder
            .forName(serverName).directExecutor();

    @BeforeAll
    static void setup() throws IOException {
        var repository = TaskRepositoryMMDB.instance();
        var createTask = CreateTaskUseCase.instance(repository);
        var searchTask = SearchTasksUseCase.instance(repository);

        server = serverBuilder.addService(TasksServiceGrpcImpl.instance(createTask, searchTask)).build().start();
        channel = channelBuilder.build();
        stub = TasksServiceGrpc.newBlockingStub(channel);
    }

    @AfterAll
    static void tearDown() throws InterruptedException {
        channel.shutdown();
        server.shutdown();

        try {
            assert channel.awaitTermination(5, TimeUnit.SECONDS) : "channel cannot be gracefully shutdown";
            assert server.awaitTermination(5, TimeUnit.SECONDS) : "server cannot be gracefully shutdown";
        } finally {
            channel.shutdownNow();
            server.shutdownNow();
        }
    }


}
