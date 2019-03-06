package br.com.miguelfontes.taskflow.core;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Nested
    @DisplayName("newInstance factory method")
    class newInstanceFactoryMethod {
        private static final String userName = "a user name";
        private User user = User.newInstance(userName);

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

}