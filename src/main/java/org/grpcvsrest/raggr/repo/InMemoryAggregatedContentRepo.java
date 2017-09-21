package org.grpcvsrest.raggr.repo;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class InMemoryAggregatedContentRepo {
    private final Map<Integer, AggregatedContent> contents = new ConcurrentHashMap<>();

    public AggregatedContent find(int contentId) {
        return contents.get(contentId);
    }

    public synchronized AggregatedContent save(AggregatedContent contentRecord) {
        AggregatedContent existingRecord = existingRecord(contentRecord);
        if (existingRecord != null) {
            return existingRecord;
        }
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

    private AggregatedContent existingRecord(AggregatedContent record) {
        return contents.values().stream()
                .filter(i ->
                        Objects.equals(     i.getContent(),     record.getContent())
                        && Objects.equals(  i.getOriginalId(),  record.getOriginalId())
                        && Objects.equals(  i.getType(),        record.getType())
                ).findFirst().orElse(null);

    }

    public int size() {
        return contents.size();
    }
}
