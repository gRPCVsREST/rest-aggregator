package org.grpcvsrest.raggr.repo;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryAggregatedContentRepoTest {

    private static final int CONTENT_ID = 1;
    private static final String CONTENT_TYPE = "Pokemon";
    private static final String CONTENT_STRING = "foobar";
    private static final String CONTENT_STRING_2 = "foobar2";
    private static final int ORIGINAL_ID = 42;
    private static final int ORIGINAL_ID_2 = 43;
    private static final AggregatedContent NEW_CONTENT_RECORD = new AggregatedContent(
            null,
            CONTENT_TYPE,
            CONTENT_STRING,
            ORIGINAL_ID);
    private static final AggregatedContent NEW_CONTENT_RECORD_2 = new AggregatedContent(
            null,
            CONTENT_TYPE,
            CONTENT_STRING_2,
            ORIGINAL_ID_2);
    private static final AggregatedContent SAVED_CONTENT_RECORD = new AggregatedContent(
            CONTENT_ID,
            CONTENT_TYPE,
            CONTENT_STRING,
            ORIGINAL_ID);


    private InMemoryAggregatedContentRepo repo;

    @Before
    public void resetRepo() {
        repo = new InMemoryAggregatedContentRepo();
    }

    @Test
    public void testSize() {
        // given empty repo
        // when
        int size = repo.size();
        // then
        assertThat(size).isEqualTo(0);

        // and given
        repo.save(NEW_CONTENT_RECORD);
        // when
        size = repo.size();
        // then
        assertThat(size).isEqualTo(1);


        // and given
        repo.save(NEW_CONTENT_RECORD);
        // when
        size = repo.size();
        // then no additional records are saved
        assertThat(size).isEqualTo(1);

        // and given
        repo.save(NEW_CONTENT_RECORD_2);
        // when
        size = repo.size();
        // then one extra record is saved
        assertThat(size).isEqualTo(2);
    }

    @Test
    public void testSaveNew() {
        // when
        AggregatedContent result = repo.find(CONTENT_ID);
        // then
        assertThat(result).isNull();

        // and when
        result = repo.save(NEW_CONTENT_RECORD);
        // then
        assertThat(result).isEqualTo(SAVED_CONTENT_RECORD);

        // and when
        result = repo.find(CONTENT_ID);
        // then
        assertThat(result).isEqualTo(SAVED_CONTENT_RECORD);
    }

    @Test
    public void testSaveExisting() {
        // given
        repo.save(NEW_CONTENT_RECORD);

        // and when
        AggregatedContent result = repo.save(SAVED_CONTENT_RECORD);
        // then
        assertThat(result).isEqualTo(SAVED_CONTENT_RECORD);

        // and when
        result = repo.save(NEW_CONTENT_RECORD);
        // then
        assertThat(result).isEqualTo(SAVED_CONTENT_RECORD);

        // and when
        result = repo.find(CONTENT_ID);
        // then
        assertThat(result).isEqualTo(SAVED_CONTENT_RECORD);
    }

}