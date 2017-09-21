package org.grpcvsrest.raggr.repo;

import org.grpcvsrest.raggr.datasource.Content;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryContentRepoTest {

    private static final int CONTENT_ID = 42;
    private static final int CONTENT_ID_2 = 43;
    private static final String CONTENT_STRING = "foobar";
    private static final String CONTENT_STRING_2 = "foobar2";
    private static final Content NEW_CONTENT_RECORD = new Content(
            CONTENT_ID,
            CONTENT_STRING,
            CONTENT_ID_2);
    private static final Content NEW_CONTENT_RECORD_2 = new Content(
            CONTENT_ID_2,
            CONTENT_STRING_2,
            null);


    private InMemoryContentRepo repo;

    @Before
    public void resetRepo() {
        repo = new InMemoryContentRepo();
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
    public void testSave() {
        // when
        Content result = repo.find(CONTENT_ID);
        // then
        assertThat(result).isNull();

        // and when
        result = repo.save(NEW_CONTENT_RECORD);
        // then
        assertThat(result).isEqualTo(NEW_CONTENT_RECORD);

        // and when
        result = repo.save(NEW_CONTENT_RECORD);
        // then
        assertThat(result).isEqualTo(NEW_CONTENT_RECORD);
    }

}
