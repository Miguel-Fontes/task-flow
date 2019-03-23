package br.com.miguelfontes.taskflow.core;

import br.com.miguelfontes.taskflow.core.tasks.Task;
import br.com.miguelfontes.taskflow.core.tasks.TaskStatus;
import br.com.miguelfontes.taskflow.core.tasks.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("task test")
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

        @Nested
        @DisplayName("mutations creates new instances")
        class mutations {
            private final String title = "my task title";
            private final String userName = "a user name";
            private final User user = User.newInstance(userName);
            private final Task task = Task.newInstance(user, title);

            @Test
            @DisplayName("with a new title")
            void withANewTitle() {
                String newTitle = "a new title";
                Task updatedTask = task.withTitle(newTitle);

                assertAll(
                        () -> assertEquals(task.getId(), updatedTask.getId()),
                        () -> assertEquals(task.getAuthor(), updatedTask.getAuthor()),
                        () -> assertEquals(task.getStatus(), updatedTask.getStatus()),
                        () -> assertEquals(task.getCreatedAt(), updatedTask.getCreatedAt()),
                        () -> assertNotEquals(task.getTitle(), updatedTask.getTitle()),
                        () -> assertNotEquals(task.getUpdatedAt(), updatedTask.getUpdatedAt())
                );

            }

            @Test
            @DisplayName("with a new status")
            void withANewStatus() {
                TaskStatus status = TaskStatus.TODO;
                Task updatedTask = task.withStatus(status);

                assertAll(
                        () -> assertEquals(task.getId(), updatedTask.getId()),
                        () -> assertEquals(task.getTitle(), updatedTask.getTitle()),
                        () -> assertEquals(task.getAuthor(), updatedTask.getAuthor()),
                        () -> assertEquals(task.getCreatedAt(), updatedTask.getCreatedAt()),
                        () -> assertNotEquals(task.getStatus(), updatedTask.getStatus()),
                        () -> assertNotEquals(task.getUpdatedAt(), updatedTask.getUpdatedAt())
                );
            }

            @Test
            @DisplayName("with a new author")
            void withANewAuthor() {
                User newAuthor = User.newInstance("new author");
                Task updatedTask = task.withAuthor(newAuthor);

                assertAll(
                        () -> assertEquals(task.getId(), updatedTask.getId()),
                        () -> assertEquals(task.getTitle(), updatedTask.getTitle()),
                        () -> assertEquals(task.getStatus(), updatedTask.getStatus()),
                        () -> assertEquals(task.getCreatedAt(), updatedTask.getCreatedAt()),
                        () -> assertNotEquals(task.getAuthor(), updatedTask.getAuthor()),
                        () -> assertNotEquals(task.getUpdatedAt(), updatedTask.getUpdatedAt())
                );
            }
        }

    }
}
