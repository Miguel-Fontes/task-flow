package br.com.miguelfontes.taskflow.ports.tasks;

import java.util.Objects;
import java.util.Optional;

/**
 * Encapsulates the request data of a {@link SearchTasks} operation.
 *
 * @author Miguel Fontes
 */
public class SearchTasksRequest {
    private final String title;

    public static final SearchTasksRequest ALL = SearchTasksRequest.builder().build();

    private SearchTasksRequest(String title) {
        this.title = title;
    }

    public static SearchTasksRequestBuilder builder() {
        return new SearchTasksRequestBuilder();
    }

    public Optional<String> getTitle() {
        return Objects.isNull(title) || isBlankString(title)
                ? Optional.empty()
                : Optional.of(title);
    }

    private boolean isBlankString(String title) {
        return "".equalsIgnoreCase(title.trim());
    }

    /**
     * A search request has variable criteria that could be anything between no criteria and all criteria.
     * This Builder aims to make the instantiation of the request easier, encapsulating the usage of nulls
     * inside the builder context.
     *
     * @author Miguel Fontes
     */
    public static class SearchTasksRequestBuilder {
        private String title = null;

        public SearchTasksRequestBuilder title(String title) {
            this.title = title;
            return this;
        }

        public SearchTasksRequest build() {
            return new SearchTasksRequest(title);
        }

    }
}
