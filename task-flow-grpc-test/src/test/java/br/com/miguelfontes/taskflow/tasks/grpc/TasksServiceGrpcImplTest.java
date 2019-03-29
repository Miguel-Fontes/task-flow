package br.com.miguelfontes.taskflow.tasks.grpc;

import io.grpc.StatusRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
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

        var response = stub.create(request).getTask();

        assertAll(
                () -> Assertions.assertEquals(TASK_TITLE, response.getTitle()),
                () -> assertNotNull(response.getId()),
                () -> Assertions.assertEquals(USER_ID, response.getAuthor()),
                () -> assertNotNull(response.getCreatedAt()),
                () -> assertNotNull(response.getUpdatedAt()),
                () -> assertNotNull(response.getStatus())
        );
    }

    @Test
    @DisplayName("should create and retrieve a persisted task")
    void shouldCreateAndRetrieveAPersistedTask() {
        var createTaskRequest = buildCreateTaskRequest(USER_ID, TASK_TITLE);
        var createTaskResponse = stub.create(createTaskRequest).getTask();
        var searchTaskRequest = buildSearchTaskRequest(TASK_TITLE);

        var list = stub.search(searchTaskRequest);

        assertAll(
                () -> Assertions.assertFalse(list.getTasksList().isEmpty()),
                () -> Assertions.assertTrue(list.getTasksList().contains(createTaskResponse))
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

    @Test
    @DisplayName("shouldRetrieveAllTasks")
    void shouldRetrieveAllTasks() {
        stub.create(buildCreateTaskRequest(USER_ID, TASK_TITLE));

        final var tasks = stub.search(buildNoCriteriaSearchTasksRequest());

        assertFalse(tasks.getTasksList().isEmpty());
    }

    @Test
    @DisplayName("should return empty list when no task is found")
    void shouldReturnAEmptyListWhenNoTaskIsFound() {
        final var tasks = stub.search(buildSearchTaskRequest("random title to search for"));

        assertTrue(tasks.getTasksList().isEmpty());
    }

    private TasksServiceOuterClass.SearchTasksRequest buildNoCriteriaSearchTasksRequest() {
        return TasksServiceOuterClass.SearchTasksRequest.newBuilder().build();
    }

    @Test
    @DisplayName("should delete a task if it exists")
    void shouldDeleteATask() {
        var task = stub.create(buildCreateTaskRequest(USER_ID, TASK_TITLE)).getTask();

        stub.delete(buildDeleteTaskRequest(task));
        final var tasks = stub.search(buildNoCriteriaSearchTasksRequest());

        assertTrue(tasks.getTasksList().isEmpty());
    }

    private TasksServiceOuterClass.DeleteTaskRequest buildDeleteTaskRequest(TasksServiceOuterClass.Task task) {
        return TasksServiceOuterClass.DeleteTaskRequest.newBuilder().setUuid(task.getId()).build();
    }

    @Test
    @DisplayName("should update a task title and description")
    void shouldUpdateATask() {
        final String newTitle = "a new title";
        final String newDescription = "a new description";
        var task = stub.create(buildCreateTaskRequest(USER_ID, TASK_TITLE)).getTask();

        final var updatedTask = stub.update(
                buildDefaultTaskUpdateRequest(task)
                        .setTitle(newTitle)
                        .setDescription(newDescription)
                        .build())
                .getTask();

        assertAll(
                () -> assertEquals(newTitle, updatedTask.getTitle()),
                () -> assertEquals(newDescription, updatedTask.getDescription()),
                () -> assertEquals(task.getAuthor(), updatedTask.getAuthor()),
                () -> assertEquals(task.getId(), updatedTask.getId()),
                () -> assertEquals(task.getStatus(), updatedTask.getStatus()),
                () -> assertEquals(task.getCreatedAt(), updatedTask.getCreatedAt())
        );
    }

    private TasksServiceOuterClass.UpdateTaskRequest.Builder buildDefaultTaskUpdateRequest(TasksServiceOuterClass.Task task) {
        return TasksServiceOuterClass.UpdateTaskRequest.newBuilder()
                .setId(task.getId())
                .setTitle(task.getId())
                .setDescription(task.getDescription());
    }

    @Nested
    @DisplayName("conclude task")
    class ConcludeTask {

        @Test
        @DisplayName("should conclude a task")
        void shouldConcludeATask() {
            var request = buildCreateTaskRequest(USER_ID, TASK_TITLE);
            var task = stub.create(request).getTask();

            final var concludedTask = stub.conclude(buildConcludeTaskRequest(task.getId())).getTask();

            assertAll(
                    () -> assertEquals(task.getId(), concludedTask.getId()),
                    () -> assertNotEquals(task.getUpdatedAt(), concludedTask.getUpdatedAt()),
                    () -> assertEquals("DONE", concludedTask.getStatus())
            );
        }

        private TasksServiceOuterClass.ConcludeTaskRequest buildConcludeTaskRequest(String id) {
            return TasksServiceOuterClass.ConcludeTaskRequest.newBuilder()
                    .setId(id)
                    .build();
        }

        @Test
        @DisplayName("should throw exception when given task id is not found")
        void shouldThrowExceptionWhenGivenTaskIdIsNotFound(TestReporter reporter) {
            var uuid = UUID.randomUUID();
            final var taskNotFoundException =
                    assertThrows(StatusRuntimeException.class, () -> stub.conclude(buildConcludeTaskRequest(uuid.toString())));

            reporter.publishEntry("Error message: " + taskNotFoundException.getMessage());
            assertAll(
                    () -> assertTrue(taskNotFoundException.getMessage().contains(uuid.toString()))
            );
        }
    }

    @Nested
    @DisplayName("start task")
    class StartTask {

        @Test
        @DisplayName("should start a task")
        void shouldStartATask() {
            var task = stub.create(buildCreateTaskRequest(USER_ID, TASK_TITLE)).getTask();

            final var startedTask = stub.start(buildStartTaskRequest(task.getId())).getTask();

            assertAll(
                    () -> assertEquals("DOING", startedTask.getStatus()),
                    () -> assertNotEquals(task.getUpdatedAt(), startedTask.getUpdatedAt())
            );
        }

        private TasksServiceOuterClass.StartTaskRequest buildStartTaskRequest(String id) {
            return TasksServiceOuterClass.StartTaskRequest.newBuilder()
                    .setId(id)
                    .build();
        }

        @Test
        @DisplayName("should throw exception when id is not found")
        void shouldThrowExceptionWhenIdIsNotFound(TestReporter reporter) {
            var id = UUID.randomUUID().toString();

            final var taskNotFoundException =
                    assertThrows(StatusRuntimeException.class, () -> stub.start(buildStartTaskRequest(id)));

            reporter.publishEntry("Error message: " + taskNotFoundException.getMessage());
            assertTrue(taskNotFoundException.getMessage().contains(id));
        }
    }
}