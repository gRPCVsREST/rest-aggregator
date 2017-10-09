package org.grpcvsrest.raggr.service;

import org.grpcvsrest.raggr.datasource.Content;
import org.grpcvsrest.raggr.datasource.Datasource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AggregatingService {
    private final Datasource sourceA;
    private final Datasource sourceB;
    private final String categoryA;
    private final IdMapper idMapper;

    @Autowired
    public AggregatingService(
            @Qualifier("datasourceA") Datasource sourceA,
            @Qualifier("datasourceB") Datasource sourceB,
            @Value("${content_type.a}") String categoryA,
            IdMapper idMapper) {
        this.sourceA = sourceA;
        this.sourceB = sourceB;
        this.categoryA = categoryA;
        this.idMapper = idMapper;
    }

    public AggregatedContent fetch(int id) {
        IdMapper.CategoryId categoryId = idMapper.getMap().get(id);
        if (categoryId == null) {
            return null;
        }

        Content originalContent;
        if (categoryId.getCategory().equals(categoryA)) {
            originalContent = sourceA.fetch(categoryId.getOriginalId());
        } else {
            originalContent = sourceB.fetch(categoryId.getOriginalId());
        }
        Integer nextId = id < idMapper.getMap().size() ?  id + 1: null;
        return new AggregatedContent(
                id,
                categoryId.getCategory(),
                originalContent.getContent(),
                categoryId.getOriginalId(),
                nextId);

    }
}
