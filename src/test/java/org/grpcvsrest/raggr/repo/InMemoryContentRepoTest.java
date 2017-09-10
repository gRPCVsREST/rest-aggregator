package org.grpcvsrest.raggr.repo;

import org.grpcvsrest.raggr.datasource.Content;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryContentRepoTest {

    private static final int CONTENT_ID = 42;
    private static final String CONTENT_STRING = "foobar";
    private static final Content CONTENT_RECORD = new Content(CONTENT_ID, CONTENT_STRING, null);


    private InMemoryContentRepo repo;

    @Before
    public void resetRepo() {
        repo = new InMemoryContentRepo();
    }

    @Test
    public void testSaveAndFind() {
        // when
        Content result = repo.find(CONTENT_ID);
        // then
        assertThat(result).isNull();

        // and when
        repo.save(CONTENT_RECORD);
        result = repo.find(CONTENT_ID);
        // then
        assertThat(result).isEqualTo(CONTENT_RECORD);
    }
}
