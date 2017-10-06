package org.grpcvsrest.raggr.datasource;

import com.google.common.collect.ImmutableMap;
import org.grpcvsrest.raggr.repo.InMemoryContentRepo;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_XML;

public class Datasource {
    private final RestTemplate restTemplate;
    private final String urlPrefix;
    private final InMemoryContentRepo repo = new InMemoryContentRepo();

    public Datasource(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        urlPrefix = url+"/content/";
    }

    public Content fetch(int contentId) {
        Content cached = repo.find(contentId);
        if (cached != null) {
            return cached;
        }

        ResponseEntity<Content> contentEntity = null;
        try {
            contentEntity = restTemplate.getForEntity(
                    urlPrefix+"{content_id}",
                    Content.class,
                    ImmutableMap.of("content_id", contentId));
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().value() == 404) {
                return null;
            }
            throw e;
        }

        repo.save(contentEntity.getBody());
        return contentEntity.getBody();
    }

}
