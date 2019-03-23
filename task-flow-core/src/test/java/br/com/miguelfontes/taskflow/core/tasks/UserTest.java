package br.com.miguelfontes.taskflow.core.tasks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("user")
class UserTest {
    private static final String userName = "a user name";
    private final User user = User.newInstance(userName);

    @Nested
    @DisplayName("newInstance factory method")
    class newInstanceFactoryMethod {

        @Nested
        @DisplayName("valid arguments")
        class validArguments {
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

            @Test
            @DisplayName("should generate a id")
            void shouldGenerateAId() {
                assertNotNull(user.getId());
            }
        }

        @Nested
        @DisplayName("invalid aguments")
        class invalidArguments {
            @Test
            @DisplayName("should throw exception when name is null")
            void shouldThrowExceptionWhenNameIsNull() {
                assertThrows(IllegalArgumentException.class, () -> User.newInstance(null));
            }

            @ParameterizedTest
            @ValueSource(strings = {"a", "ab", "a  ", "  a"})
            @DisplayName("should throw exception when name length is below 3")
            void shouldThrowExceptionWhenNameLengthIsBelow3(String name) {
                assertThrows(IllegalArgumentException.class, () -> User.newInstance(name));
            }

            @ParameterizedTest
            @ValueSource(strings = {"", " ", "   ", "    "})
            @DisplayName("should throw exception when name is blank or spaces")
            void shouldThrowExceptionForBlankName(String name) {
                assertThrows(IllegalArgumentException.class, () -> User.newInstance(name));
            }

        }

    }

    @Nested
    @DisplayName("createTask")
    class createTask {
        private static final String TASK_TITLE = "A task title";
        private final Task task = user.createTask(TASK_TITLE);

        @Test
        @DisplayName("should create a task with given title")
        void shouldCreateATaskWithGivenTitle() {
            assertEquals(TASK_TITLE, task.getTitle());
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