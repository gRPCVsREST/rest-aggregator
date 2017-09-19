package org.grpcvsrest.raggr.rest;

import org.grpcvsrest.raggr.repo.AggregatedContent;
import org.grpcvsrest.raggr.repo.InMemoryContentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContentController {

    private final InMemoryContentRepo repo;

    @Autowired
    public ContentController(InMemoryContentRepo repo) {
        this.repo = repo;
    }

    @PostMapping("/content/{id}")
    public AggregatedContent content(@PathVariable("id") int id) {
        AggregatedContent aggregatedContent = repo.find(id);
        if (aggregatedContent == null) {
            throw new IllegalArgumentException("Content not found");
        }
        return aggregatedContent;
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFound() {

    }
}
