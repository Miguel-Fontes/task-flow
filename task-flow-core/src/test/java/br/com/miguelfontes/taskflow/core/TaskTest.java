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
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskTest {

    @Nested
    @DisplayName("newInstance factory method")
    class newInstanceFactoryMethod {

        @Nested
        @DisplayName("valid arguments")
        class validArguments {


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

        @Nested
        @DisplayName("invalid arguments")
        class invalidArguments {
            private final String title = "my task title";
            private final String userName = "a user name";
            private final User user = User.newInstance(userName);

            @Test
            @DisplayName("should throw exception when title size is a empty String or spaces")
            void shouldThrowExceptionWhenTitleSizeIsEmptyStringOrSpaces() {
                assertThrows(IllegalArgumentException.class, () -> Task.newInstance(user, ""));
                assertThrows(IllegalArgumentException.class, () -> Task.newInstance(user, " "));
                assertThrows(IllegalArgumentException.class, () -> Task.newInstance(user, "  "));
            }

            @Test
            @DisplayName("should throw exception when title is null")
            void shouldThrowExceptionWhenTitleIsNull() {
                assertThrows(IllegalArgumentException.class, () -> Task.newInstance(user, null));
            }

            @Test
            @DisplayName("should throw exception when user is null")
            void shouldThrowExceptionWhenUserIsNull() {
                assertThrows(IllegalArgumentException.class, () -> Task.newInstance(null, title));
            }
        }
    }
}
