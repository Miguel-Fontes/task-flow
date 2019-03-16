package br.com.miguelfontes.taskflow.tasks.grpc;

import br.com.miguelfontes.taskflow.persistence.mmdb.TaskRepositoryMMDB;
import br.com.miguelfontes.taskflow.tasks.CreateTaskUseCase;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

/**
 * Manages a GRPC server, exposing the Tasks API for external clients
 *
 * @author Miguel Fontes
 */
public class Application {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws IOException, InterruptedException {
        var port = getPort(args);
        Server server = ServerBuilder.forPort(port)
                .addService(TasksServiceGrpcImpl.instance(CreateTaskUseCase.instance(TaskRepositoryMMDB.instance())))
                .build();

        server.start();

        logger.info("Server started on port [{}]!", port);

        server.awaitTermination();
    }

    private static Integer getPort(String[] args) {
        return safeGet(0, args)
                .map(Integer::valueOf)
                .orElse(8080);
    }

    private static <T> Optional<T> safeGet(int i, T[] args) {
        return args.length > i
                ? java.util.Optional.ofNullable(args[i])
                : Optional.empty();
    }
}

