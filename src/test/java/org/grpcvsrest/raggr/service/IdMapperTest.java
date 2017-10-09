package org.grpcvsrest.raggr.service;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class IdMapperTest {

    @Test
    public void testMapper() {
        IdMapper mapper = new IdMapper("A", Arrays.asList(1, 2, 3), "B", Arrays.asList(1, 2));
        assertThat(mapper.getMap()).isEqualTo(
                ImmutableMap.<Integer, IdMapper.CategoryId>builder()
                        .put(1, new IdMapper.CategoryId("A", 1))
                        .put(2, new IdMapper.CategoryId("B", 1))
                        .put(3, new IdMapper.CategoryId("A", 2))
                        .put(4, new IdMapper.CategoryId("B", 2))
                        .put(5, new IdMapper.CategoryId("A", 3))
                        .build());
    }

}