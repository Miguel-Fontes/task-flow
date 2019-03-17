package br.com.miguelfontes.taskflow.tasks.grpc;

import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("task service grpc")
class TasksServiceGrpcImplTest extends GrpcTest {

    private static final String USER_ID = UUID.randomUUID().toString();
    private static final String TASK_TITLE = "a task title";

    @Test
    @DisplayName("should create a new task with a given title")
    void shouldCreateANewTask() {
        var request = buildCreateTaskRequest(USER_ID, TASK_TITLE);

        var response = stub.create(request);

        assertAll(
                () -> assertEquals(TASK_TITLE, response.getTitle()),
                () -> assertNotNull(response.getId()),
                () -> assertEquals(USER_ID, response.getAuthor()),
                () -> assertNotNull(response.getCreatedAt()),
                () -> assertNotNull(response.getUpdatedAt()),
                () -> assertNotNull(response.getStatus())
        );
    }

    @Test
    @DisplayName("should create and retrieve a persisted task")
    void shouldCreateAndRetrieveAPersistedTask() {
        var createTaskRequest = buildCreateTaskRequest(USER_ID, TASK_TITLE);
        var createTaskResponse = stub.create(createTaskRequest);
        var searchTaskRequest = buildSearchTaskRequest("");


        var list = stub.list(searchTaskRequest);

        assertAll(
                () -> assertFalse(list.getTasksList().isEmpty()),
                () -> assertTrue(list.getTasksList().contains(createTaskResponse))
        );
    }

    private TasksServiceOuterClass.SearchTasksRequest buildSearchTaskRequest(String title) {
        return TasksServiceOuterClass.SearchTasksRequest.newBuilder()
                .setTitle(title)
                .build();

    }

    @Test
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @DisplayName("should throw exception when user id is not a valid UUID")
    void shouldThrowExceptionWhenUserIdIsNotAValidUUID() {
        var request = buildCreateTaskRequest("123", TASK_TITLE);

        assertThrows(StatusRuntimeException.class, () -> stub.create(request));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "  ", "   ", "    "})
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @DisplayName("should throw exception when task title is empty strings or blank")
    void shouldThrowExceptionWhenTaskTitleSizeIsEmptyStringOrSpaces(String title) {
        var request = buildCreateTaskRequest(USER_ID, title);

        assertThrows(StatusRuntimeException.class, () -> stub.create(request));
    }

    private TasksServiceOuterClass.CreateTaskRequest buildCreateTaskRequest(String userId, String taskTitle) {
        return TasksServiceOuterClass.CreateTaskRequest.newBuilder()
                .setUserId(userId)
                .setTitle(taskTitle)
                .build();
    }
}