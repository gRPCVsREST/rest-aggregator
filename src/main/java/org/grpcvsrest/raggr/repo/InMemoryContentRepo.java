package org.grpcvsrest.raggr.repo;

import org.grpcvsrest.raggr.datasource.AggregatedContent;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryContentRepo {
    private final Map<Integer, AggregatedContent> contents = new ConcurrentHashMap<>();

    public AggregatedContent find(int contentId) {
        return contents.get(contentId);
    }

    public synchronized AggregatedContent save(AggregatedContent contentRecord) {
        if (contentRecord.getId() == null) {
            contentRecord = new AggregatedContent(
                    contents.size()+1,
                    contentRecord.getType(),
                    contentRecord.getContent());
        }
        contents.put(contentRecord.getId(), contentRecord);
        return contentRecord;
    }
}
