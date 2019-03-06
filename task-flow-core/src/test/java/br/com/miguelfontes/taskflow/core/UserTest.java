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

class UserTest {
    private static final String userName = "a user name";
    private User user = User.newInstance(userName);

    @Nested
    @DisplayName("newInstance factory method")
    class newInstanceFactoryMethod {
        @Test
        @DisplayName("should create a new instance with a given name name")
        void shouldCreateANewInstanceWithAGivenName() {
            assertEquals(userName, user.getName());
        }

        @Test
        @DisplayName("should set a Users creation time")
        void shouldSetAUserCreationTime() {
            assertAll(
                    () -> assertNotNull(user.getCreatedAt()),
                    () -> assertNotNull(user.getUpdatedAt())
            );
        }
    }

    @Nested
    @DisplayName("createTask")
    class createTask {
        private String title = "A task title";
        Task task = Task.newInstance(user, title);

        @Test
        @DisplayName("should create a task with given title")
        void shouldCreateATaskWithGivenTitle() {
            assertEquals(title, task.getTitle());
        }

        @Test
        @DisplayName("should create task with the current user as author")
        void shouldCreateTaskWithTheCurrentUserAsAuthor() {
            assertEquals(user, task.getAuthor());
        }

        @Test
        @DisplayName("Should create a task on Inbox status")
        void shouldCreateATaskOnInboxStatus() {
            assertEquals(TaskStatus.INBOX, task.getStatus());
        }
    }

}