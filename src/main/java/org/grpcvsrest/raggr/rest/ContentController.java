package org.grpcvsrest.raggr.rest;

import org.grpcvsrest.raggr.repo.AggregatedContent;
import org.grpcvsrest.raggr.repo.InMemoryContentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContentController {

    private final InMemoryContentRepo repo;

    @Autowired
    public ContentController(InMemoryContentRepo repo) {
        this.repo = repo;
    }

    @PostMapping("/content/{id}")
    public AggregatedContent content(@PathVariable("id") int id) {
        return repo.find(id);
    }
}
