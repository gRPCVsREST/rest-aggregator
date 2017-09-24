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

    @GetMapping("/content/{id}")
    public ResponseEntity<AggregatedContentResponse> content(@PathVariable("id") int id) {
        AggregatedContent aggregatedContent = aggregatingService.fetch(id);
        if (aggregatedContent == null) {
            throw new IllegalArgumentException("Content not found");
        }

        return ResponseEntity.ok()
                .body(new AggregatedContentResponse(
                        aggregatedContent.getId(),
                        aggregatedContent.getType(),
                        aggregatedContent.getContent()));
    }


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFound() {

    }
}
