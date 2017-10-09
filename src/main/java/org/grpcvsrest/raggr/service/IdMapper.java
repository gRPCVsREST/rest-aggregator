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
import java.util.stream.Stream;

import static com.google.common.primitives.Ints.max;

public class IdMapper {

    private final static Logger LOG = LoggerFactory.getLogger(Application.class);

    private final Map<Integer, CategoryId> map;

    public IdMapper(String categoryA, List<Integer> idsA, String categoryB, List<Integer> idsB) {
        Map<Integer, CategoryId> interim = new HashMap<>();
        int j=1;
        for (int i = 0; i < max(idsA.size(), idsB.size()); i++) {
            j = putIfPresent(categoryA, idsA, interim, j, i);
            j = putIfPresent(categoryB, idsB, interim, j, i);

        }
        map = Collections.unmodifiableMap(interim);

        LOG.info("Initialized ID mapping {}", map);
    }

    private int putIfPresent(String category, List<Integer> ids, Map<Integer, CategoryId> interim, int j, int i) {
        if (i < ids.size()) {
            interim.put(j, new CategoryId(category, ids.get(i)));
            j++;
        }
        return j;
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
