package org.grpcvsrest.raggr.service;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class IdMapperTest {

    @Test
    public void testMapper_A() {
        IdMapper mapper = new IdMapper("A", Arrays.asList(1, 2, 3, 4, 5), "B", Arrays.asList(1, 2));
        assertThat(mapper.getMap()).isEqualTo(
                ImmutableMap.<Integer, IdMapper.CategoryId>builder()
                        .put(1, new IdMapper.CategoryId("A", 1))
                        .put(2, new IdMapper.CategoryId("B", 1))
                        .put(3, new IdMapper.CategoryId("A", 2))
                        .put(4, new IdMapper.CategoryId("B", 2))
                        .put(5, new IdMapper.CategoryId("A", 3))
                        .put(6, new IdMapper.CategoryId("A", 4))
                        .put(7, new IdMapper.CategoryId("A", 5))
                        .build());
    }

    @Test
    public void testMapper_B() {
        IdMapper mapper = new IdMapper("A", Arrays.asList(1, 2), "B", Arrays.asList(1, 2, 3, 4, 5));
        assertThat(mapper.getMap()).isEqualTo(
                ImmutableMap.<Integer, IdMapper.CategoryId>builder()
                        .put(1, new IdMapper.CategoryId("A", 1))
                        .put(2, new IdMapper.CategoryId("B", 1))
                        .put(3, new IdMapper.CategoryId("A", 2))
                        .put(4, new IdMapper.CategoryId("B", 2))
                        .put(5, new IdMapper.CategoryId("B", 3))
                        .put(6, new IdMapper.CategoryId("B", 4))
                        .put(7, new IdMapper.CategoryId("B", 5))
                        .build());
    }

}