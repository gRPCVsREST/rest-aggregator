package org.grpcvsrest.raggr.service;

import org.grpcvsrest.raggr.datasource.Content;
import org.grpcvsrest.raggr.datasource.Datastream;
import org.grpcvsrest.raggr.repo.AggregatedContent;
import org.grpcvsrest.raggr.repo.InMemoryAggregatedContentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AggregatingService {
    private final Datastream streamA;
    private final Datastream streamB;
    private final String categoryA;
    private final String categoryB;
    private final InMemoryAggregatedContentRepo repo = new InMemoryAggregatedContentRepo();

    @Autowired
    public AggregatingService(
            @Qualifier("datastreamA") Datastream streamA,
            @Qualifier("datastreamB") Datastream streamB,
            @Value("${content_type.a}") String categoryA,
            @Value("${content_type.b}") String categoryB) {
        this.streamA = streamA;
        this.streamB = streamB;
        this.categoryA = categoryA;
        this.categoryB = categoryB;
    }

    public AggregatedContent fetch(int id) {
        AggregatedContent cached = repo.find(id);
        if (cached != null) {
            return cached;
        }

        AggregatedContent saved = null;
        Content resultA = streamA.next();
        Content resultB = streamB.next();

        while ( saved == null &&  (resultA != null || resultB != null)) {
            if (resultA != null) {
                repo.save(new AggregatedContent(null, categoryA, resultA.getContent(), resultA.getId()));
            }
            if (resultB != null) {
                repo.save(new AggregatedContent(null, categoryB, resultB.getContent(), resultB.getId()));
            }
            saved = repo.find(id);

            resultA = streamA.next();
            resultB = streamB.next();

        }
        return saved;

    }
}
