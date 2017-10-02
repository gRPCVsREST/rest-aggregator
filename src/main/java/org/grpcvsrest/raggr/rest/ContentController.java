package org.grpcvsrest.raggr.rest;

import org.grpcvsrest.raggr.repo.AggregatedContent;
import org.grpcvsrest.raggr.service.AggregatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContentController {

    private final AggregatingService aggregatingService;

    @Autowired
    public ContentController(AggregatingService aggregatingService) {
        this.aggregatingService = aggregatingService;
    }


    @GetMapping(value = "/content/{id}", consumes = "application/xml", produces = "application/xml")
    public ResponseEntity<AggregatedContentResponse> contentXml(@PathVariable("id") int id) {
        return content(id);
    }
    @GetMapping(value = "/content/{id}", produces = "application/json")
    public ResponseEntity<AggregatedContentResponse> contentJson(@PathVariable("id") int id) {
        return content(id);
    }

    private ResponseEntity<AggregatedContentResponse> content(@PathVariable("id") int id) {
        AggregatedContent aggregatedContent = aggregatingService.fetch(id);
        if (aggregatedContent == null) {
            throw new IllegalArgumentException("Content not found");
        }

        return ResponseEntity.ok()
                .body(new AggregatedContentResponse(
                        aggregatedContent.getId(),
                        aggregatedContent.getType(),
                        aggregatedContent.getContent(),
                        "/content/"+(aggregatedContent.getId()+1)));
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFound() {

    }
}
