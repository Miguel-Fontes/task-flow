module taskflow.core.tasks {
    exports br.com.miguelfontes.taskflow.core.tasks to
            taskflow.tasks,
            taskflow.ports.persistence,
            taskflow.persistence.mmdb;
}