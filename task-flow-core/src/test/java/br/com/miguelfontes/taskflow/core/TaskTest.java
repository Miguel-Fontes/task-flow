package br.com.miguelfontes.taskflow.core;

import br.com.miguelfontes.taskflow.core.tasks.Task;
import br.com.miguelfontes.taskflow.core.tasks.TaskStatus;
import br.com.miguelfontes.taskflow.core.tasks.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaskTest {

    @Nested
    @DisplayName("newInstance factory method")
    class newInstanceFactoryMethod {
        private final String title = "my task title";
        private final String userName = "a user name";
        private final User user = User.newInstance(userName);
        private final Task task = Task.newInstance(user, title);


        @Test
        @DisplayName("should create a new instance with a given title and user")
        void shouldCreateANewInstanceWithAGivenTitleAndUser() {
            assertAll(
                    () -> assertEquals(title, task.getTitle()),
                    () -> assertEquals(user, task.getAuthor())
            );
        }

        @Test
        @DisplayName("should set a task creation date time")
        void shouldSetATaskCreationDatetime() {
            assertAll(
                    () -> assertNotNull(task.getCreatedAt()),
                    () -> assertNotNull(task.getUpdatedAt())
            );
        }

        @Test
        @DisplayName("should create a task on INBOX status")
        void shouldCreateANewTaskWithInboxStatus() {
            assertEquals(TaskStatus.INBOX, task.getStatus());
        }

        @Test
        @DisplayName("should display a new id when a new Task is created")
        void shouldGenerateANewIdWhenANewTaskIsCreated() {
            assertNotNull(task.getId());
        }
    }


}