package org.grpcvsrest.raggr.service;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

public class IdMapperTest {

    @Test
    public void testMapper_A() {
        IdMapper mapper = new IdMapper("A", asList(1, 2, 3, 4, 5), "B", asList(1, 2));
        assertThat(mapper.getMap().values()).containsOnly(
                        new IdMapper.CategoryId("A", 1),
                        new IdMapper.CategoryId("B", 1),
                        new IdMapper.CategoryId("A", 2),
                        new IdMapper.CategoryId("B", 2),
                        new IdMapper.CategoryId("A", 3),
                        new IdMapper.CategoryId("A", 4),
                        new IdMapper.CategoryId("A", 5)
        );

        assertThat(mapper.getMap().keySet()).containsOnly(1,2,3,4,5,6,7);
    }

}