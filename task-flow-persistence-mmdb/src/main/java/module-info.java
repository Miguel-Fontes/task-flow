module taskflow.persistence.mmdb {
    exports br.com.miguelfontes.taskflow.persistence.mmdb;

    requires taskflow.ports.persistence;
    requires taskflow.core.tasks;

    requires spring.context;
}