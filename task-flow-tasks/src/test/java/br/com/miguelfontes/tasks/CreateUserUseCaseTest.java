package br.com.miguelfontes.tasks;

import br.com.miguelfontes.taskflow.ports.tasks.CreateUser;
import br.com.miguelfontes.taskflow.ports.tasks.CreateUserRequest;
import br.com.miguelfontes.taskflow.ports.tasks.UserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("create user use case")
class CreateUserUseCaseTest {

    private CreateUser useCase = CreateUserUseCase.instance();
    private static final String USER_NAME = "a user's name";

    @Test
    @DisplayName("should create a new user")
    void shouldCreateANewUser() {
        CreateUserRequest request = CreateUserRequest.of(USER_NAME);

        UserDTO user = useCase.execute(request).getUser();

        assertAll(
                () -> assertNotNull(user.getId()),
                () -> assertEquals(USER_NAME, user.getName()),
                () -> assertNotNull(user.getCreatedAt()),
                () -> assertNotNull(user.getUpdatedAt())
        );
    }

    @Test
    @DisplayName("should throw exception when name is null")
    void shouldThrowAExceptionWhenNameIsNull() {
        CreateUserRequest request = CreateUserRequest.of(null);

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(request));
    }

    @ParameterizedTest
    @DisplayName("should throw exception when name is below three characters")
    @ValueSource(strings = {"a", "ab", "b", "bc"})
    void shouldThrowExceptionWhenNameIsBelowTreeCharacters(String name) {
        CreateUserRequest request = CreateUserRequest.of(name);

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(request));
    }

    @ParameterizedTest
    @DisplayName("should not consider leading or trailing spaces when validation name length")
    @ValueSource(strings = {"   ", "    ", "a  ", "b  ", "  a", "  b"})
    void shouldNotConsiderLeadingOrTrailingSpacesOnNameLength(String name) {
        CreateUserRequest request = CreateUserRequest.of(name);

        assertThrows(IllegalArgumentException.class, () -> useCase.execute(request));
    }
}