package org.grpcvsrest.raggr.datasource;

import com.google.common.collect.ImmutableMap;
import org.grpcvsrest.raggr.MockitoTest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class DatasourceTest extends MockitoTest{

    private static final String FAKE_URL = "fakeUrl";
    private static final int CONTENT_ID = 42;
    private static final String CONTENT = "foobar";
    private static final Content EXPECTED_CONTENT = new Content(CONTENT_ID, CONTENT, null);

    @Mock
    private RestTemplate restTemplate;

    private Datasource datasource;

    @Before
    public void setup() {
        datasource = new Datasource(restTemplate, FAKE_URL);
    }

    @Test
    public void testFetch() {
        // given
        resourceExists();

        // when
        Content result = datasource.fetch(CONTENT_ID);

        // then
        assertThat(result).isEqualTo(EXPECTED_CONTENT);
    }

    @Test
    public void testFetch404() {
        // given
        resourceMissing();

        // when
        Content result = datasource.fetch(CONTENT_ID);

        // then
        assertThat(result).isNull();
    }


    private void resourceExists() {
        when(restTemplate.getForEntity(
                FAKE_URL+"/content/{content_id}",
                Content.class,
                ImmutableMap.of("content_id", CONTENT_ID)
        )).thenReturn(new ResponseEntity<>(EXPECTED_CONTENT, HttpStatus.OK));
    }

    private void resourceMissing() {
        when(restTemplate.getForEntity(
                FAKE_URL+"/content/{content_id}",
                Content.class,
                ImmutableMap.of("content_id", CONTENT_ID)
        )).thenReturn(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
