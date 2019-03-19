module taskflow.tasks {
    exports br.com.miguelfontes.taskflow.tasks;
    requires taskflow.ports.tasks;
    requires taskflow.ports.persistence;
    requires taskflow.core.tasks;

    requires spring.core;
    requires spring.context;
    requires spring.beans;
}