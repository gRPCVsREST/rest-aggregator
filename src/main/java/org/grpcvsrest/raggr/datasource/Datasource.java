package org.grpcvsrest.raggr.datasource;

import com.google.common.collect.ImmutableMap;
import org.springframework.web.client.RestTemplate;

public class Datasource {
    private final RestTemplate restTemplate;
    private final String url;

    public Datasource(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        this.url = url;
    }

    public Content fetch(int contentId) {
        return restTemplate.getForObject(
                url+"/content/{content_id}",
                Content.class,
                ImmutableMap.of("content_id", contentId));
    }
}
