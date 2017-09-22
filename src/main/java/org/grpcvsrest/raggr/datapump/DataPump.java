package org.grpcvsrest.raggr.datapump;

import org.grpcvsrest.raggr.datasource.Content;
import org.grpcvsrest.raggr.datasource.Datasource;
import org.grpcvsrest.raggr.repo.AggregatedContent;
import org.grpcvsrest.raggr.repo.InMemoryAggregatedContentRepo;

public class DataPump {

    private final InMemoryAggregatedContentRepo repo;
    private final Datasource datasource;
    private final String contentType;

    public DataPump(InMemoryAggregatedContentRepo repo, Datasource datasource, String contentType) {
        this.repo = repo;
        this.datasource = datasource;
        this.contentType = contentType;
    }

    public void fetchAndSave() {
        Integer nextId = 1;
        while(nextId != null) {
            Content content = datasource.fetch(nextId);
            repo.save(new AggregatedContent(null, contentType, content.getContent(), content.getId()));

            nextId = content.getNextId();
        }
    }
}
