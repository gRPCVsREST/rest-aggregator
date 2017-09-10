package org.grpcvsrest.raggr.datasource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Objects;

public class Content {
    private final int id;
    private final String content;
    private final String next;

    @JsonCreator
    public Content(
            @JsonProperty("id") int id,
            @JsonProperty("content") String content,
            @JsonProperty("next") String next) {
        this.id = id;
        this.content = content;
        this.next = next;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getNext() {
        return next;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Content)) return false;
        Content content1 = (Content) o;
        return id == content1.id &&
                Objects.equals(content, content1.content) &&
                Objects.equals(next, content1.next);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, next);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("content", content)
                .add("next", next)
                .toString();
    }
}
