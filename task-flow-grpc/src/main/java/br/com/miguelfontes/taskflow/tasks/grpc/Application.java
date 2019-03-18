package br.com.miguelfontes.taskflow.tasks.grpc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;
import java.util.Optional;

/**
 * Manages a GRPC server, exposing the Tasks API for external clients
 *
 * @author Miguel Fontes
 */
public class Application {

    private static final String BASE_PACKAGE = "br.com.miguelfontes.taskflow";

    public static void main(String[] args) throws IOException, InterruptedException {
        var applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.scan(BASE_PACKAGE);
        applicationContext.refresh();
        var server = applicationContext.getBean(GrpcServer.class);
        var port = getPort(args);
        server.start(port);
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

