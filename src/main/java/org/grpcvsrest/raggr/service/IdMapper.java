package org.grpcvsrest.raggr.service;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.grpcvsrest.raggr.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.google.common.primitives.Ints.max;

public class IdMapper {

    private final static Logger LOG = LoggerFactory.getLogger(Application.class);

    private final Map<Integer, CategoryId> map;

    public IdMapper(String categoryA, List<Integer> idsA, String categoryB, List<Integer> idsB) {
        List<CategoryId> interimCategories =
                Stream.concat(
                        idsA.stream().map(i -> new CategoryId(categoryA, i)),
                        idsB.stream().map(i -> new CategoryId(categoryB, i))
                ).collect(Collectors.toList());

        Collections.shuffle(interimCategories);

        Map<Integer, CategoryId> interim = IntStream.range(0, interimCategories.size())
                .boxed()
                .collect(Collectors.toMap(i -> i+1, interimCategories::get));

        map = Collections.unmodifiableMap(interim);

        LOG.info("Initialized ID mapping {}", map);
    }

    public Map<Integer, CategoryId> getMap() {
        return map;
    }

    public static class CategoryId {
        private final String category;
        private final Integer originalId;

        public CategoryId(String category, Integer originalId) {
            this.category = category;
            this.originalId = originalId;
        }

        public String getCategory() {
            return category;
        }

        public Integer getOriginalId() {
            return originalId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CategoryId that = (CategoryId) o;
            return Objects.equal(category, that.category) &&
                    Objects.equal(originalId, that.originalId);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(category, originalId);
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(this)
                    .add("category", category)
                    .add("originalId", originalId)
                    .toString();
        }
    }
}
