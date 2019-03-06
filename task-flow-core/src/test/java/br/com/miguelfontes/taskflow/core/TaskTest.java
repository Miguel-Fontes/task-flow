package br.com.miguelfontes.taskflow.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Nested
    @DisplayName("newInstance factory method")
    class newInstanceFactoryMethod {
        private final String title = "my task title";
        private final User user = new User();
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
            assertNotNull(task.getCreatedAt());
        }

        @Test
        @DisplayName("should create a task on INBOX status")
        void shouldCreateANewTaskWithInboxStatus() {
            assertEquals(TaskStatus.INBOX, task.getStatus());
        }
    }


}