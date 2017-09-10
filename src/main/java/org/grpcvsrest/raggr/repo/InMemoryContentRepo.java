package org.grpcvsrest.raggr.repo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
                    contentRecord.getContent(),
                    contentRecord.getOriginalId());
        }
        contents.put(contentRecord.getId(), contentRecord);
        return contentRecord;
    }
}
