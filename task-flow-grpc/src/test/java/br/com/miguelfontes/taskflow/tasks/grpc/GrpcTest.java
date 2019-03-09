package br.com.miguelfontes.taskflow.tasks.grpc;

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
        server = serverBuilder.addService(new TasksServiceGrpcImpl()).build().start();
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
