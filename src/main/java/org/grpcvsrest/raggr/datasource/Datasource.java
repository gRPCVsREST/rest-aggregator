package org.grpcvsrest.raggr.datasource;

import com.google.common.collect.ImmutableMap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class Datasource {
    private final RestTemplate restTemplate;
    private final String urlPrefix;
    private volatile Integer lastContentId;

    public Datasource(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        urlPrefix = url+"/content/";
    }

    public Content fetch(int contentId) {
        ResponseEntity<Content> contentEntity = restTemplate.getForEntity(
                urlPrefix + "{content_id}",
                Content.class,
                ImmutableMap.of("content_id", contentId));

        if (contentEntity.getStatusCode().value() == 404) {
            return null;
        }

        return contentEntity.getBody();
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }
}
