package org.grpcvsrest.raggr.datasource;

import com.google.common.collect.ImmutableMap;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class Datasource {
    private final RestTemplate restTemplate;
    private final String urlPrefix;

    public Datasource(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        urlPrefix = url+"/content/";
    }

    @SuppressWarnings("unchecked")
    public List<Integer> ids() {
        return restTemplate.getForObject(
                urlPrefix+"ids",
                List.class);
    }

    public Content fetch(int contentId) {
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

        return contentEntity.getBody();
    }

}
