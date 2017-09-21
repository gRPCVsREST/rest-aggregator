package org.grpcvsrest.raggr.datasource;

import com.google.common.collect.ImmutableMap;
import org.grpcvsrest.raggr.repo.InMemoryContentRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Datasource {
    private final RestTemplate restTemplate;
    private final String urlPrefix;
    private final InMemoryContentRepo repo = new InMemoryContentRepo();

    private volatile Integer lastContentId;

    public Datasource(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        urlPrefix = url+"/content/";
    }

    public Content fetch(int contentId) {
        Content cached = repo.find(contentId);
        if (cached != null) {
            return cached;
        }

        ResponseEntity<Content> contentEntity = restTemplate.getForEntity(
                urlPrefix + "{content_id}",
                Content.class,
                ImmutableMap.of("content_id", contentId));

        if (contentEntity.getStatusCode().value() == 404) {
            return null;
        }

        repo.save(contentEntity.getBody());
        return contentEntity.getBody();
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }
}
