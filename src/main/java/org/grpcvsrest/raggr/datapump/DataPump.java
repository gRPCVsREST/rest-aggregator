package org.grpcvsrest.raggr.datapump;

import org.apache.commons.lang3.StringUtils;
import org.grpcvsrest.raggr.datasource.Content;
import org.grpcvsrest.raggr.datasource.Datasource;
import org.grpcvsrest.raggr.repo.AggregatedContent;
import org.grpcvsrest.raggr.repo.InMemoryContentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class DataPump {

    private final InMemoryContentRepo repo;
    private final Datasource datasource;
    private final String contentType;

    public DataPump(InMemoryContentRepo repo, Datasource datasource, String contentType) {
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
