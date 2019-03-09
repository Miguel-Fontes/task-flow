package br.com.miguelfontes.taskflow.tasks.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * Manages a GRPC server, exposing the Tasks API for external clients
 *
 * @author Miguel Fontes
 */
public class Application {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(8080)
                .addService(new TasksServiceGrpcImpl())
                .build();

        server.start();

        System.out.println("Server started!");

        server.awaitTermination();
    }
}
