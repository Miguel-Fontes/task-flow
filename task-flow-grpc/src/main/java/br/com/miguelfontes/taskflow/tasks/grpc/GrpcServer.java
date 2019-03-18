package br.com.miguelfontes.taskflow.tasks.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@SuppressWarnings("WeakerAccess")
public class GrpcServer {

    private static Logger logger = LoggerFactory.getLogger(GrpcServer.class);
    private final TasksServiceGrpcImpl taskService;
    private Server server;

    @Autowired
    private GrpcServer(TasksServiceGrpcImpl taskService) {
        this.taskService = taskService;
    }

    void start(Integer port) throws IOException, InterruptedException {
        server = ServerBuilder.forPort(port)
                .addService(taskService)
                .build();

        server.start();
        logger.info("Server started on port [{}]!", port);

        server.awaitTermination();
    }

    void stop() {
        server.shutdownNow();
    }

}
