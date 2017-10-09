package org.grpcvsrest.raggr.service;

import com.google.common.base.MoreObjects;

public class AggregatedContent {
    private final Integer id;
    private final String type;
    private final String content;
    private final Integer originalId;
    private final Integer nextId;

    public AggregatedContent(Integer id, String type, String content, Integer originalId, Integer nextId) {
        this.id = id;
        this.type = type;
        this.content = content;
        this.originalId = originalId;
        this.nextId = nextId;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public Integer getOriginalId() {
        return originalId;
    }

    public Integer getNextId() {
        return nextId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AggregatedContent that = (AggregatedContent) o;
        return com.google.common.base.Objects.equal(id, that.id) &&
                com.google.common.base.Objects.equal(type, that.type) &&
                com.google.common.base.Objects.equal(content, that.content) &&
                com.google.common.base.Objects.equal(originalId, that.originalId) &&
                com.google.common.base.Objects.equal(nextId, that.nextId);
    }

    @Override
    public int hashCode() {
        return com.google.common.base.Objects.hashCode(id, type, content, originalId, nextId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("type", type)
                .add("content", content)
                .add("originalId", originalId)
                .add("nextId", nextId)
                .toString();
    }
}
