package org.grpcvsrest.raggr.repo;

import org.grpcvsrest.raggr.datasource.Content;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryContentRepo {
    private final Map<Integer, Content> contentMap = new ConcurrentHashMap<>();

    public Content find(int contentId) {
        return contentMap.get(contentId);
    }

    public void save(Content contentRecord) {
        contentMap.put(contentRecord.getId(), contentRecord);
    }
}
