package org.grpcvsrest.raggr.repo;

import org.grpcvsrest.raggr.datasource.Content;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryContentRepo {
    private final Map<Integer, Content> contents = new ConcurrentHashMap<>();

    public Content find(int contentId) {
        return contents.get(contentId);
    }

    public synchronized Content save(Content contentRecord) {
        Content existingRecord = existingRecord(contentRecord);
        if (existingRecord != null) {
            return existingRecord;
        }

        contents.put(contentRecord.getId(), contentRecord);
        return contentRecord;
    }

    private Content existingRecord(Content candidate) {
        return contents.values().stream()
                .filter(i -> i.equals(candidate))
                .findFirst()
                .orElse(null);

    }

    public int size() {
        return contents.size();
    }
}
