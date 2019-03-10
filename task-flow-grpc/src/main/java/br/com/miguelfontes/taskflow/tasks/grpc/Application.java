package br.com.miguelfontes.taskflow.tasks.grpc;

import br.com.miguelfontes.taskflow.ports.tasks.CreateTask;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Optional;

/**
 * Manages a GRPC server, exposing the Tasks API for external clients
 *
 * @author Miguel Fontes
 */
public class Application {

    public static void main(String[] args) throws IOException, InterruptedException {
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();
        CreateTask createTask = container.select(CreateTask.class).get();

        var port = getPort(args);

        Server server = ServerBuilder.forPort(port)
                .addService(TasksServiceGrpcImpl.instance(createTask))
                .build();

        server.start();

        System.out.println(String.format("Server started on port [%s]!", port));

        server.awaitTermination();
        container.shutdown();
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
