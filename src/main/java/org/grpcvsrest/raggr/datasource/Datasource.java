package org.grpcvsrest.raggr.datasource;

import com.google.common.collect.ImmutableMap;
import org.springframework.web.client.RestTemplate;

public class Datasource {
    private final RestTemplate restTemplate;
    private final String url;
    private final String urlPrefix;

    public Datasource(RestTemplate restTemplate, String url) {
        this.restTemplate = restTemplate;
        this.url = url;
        urlPrefix = url+"/content/";
    }

    public Content fetch(int contentId) {
        return restTemplate.getForObject(
                urlPrefix + "{content_id}",
                Content.class,
                ImmutableMap.of("content_id", contentId));
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }
}
