package org.grpcvsrest.raggr.datasource;

import com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.quality.Strictness;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class DatasourceTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule().strictness(Strictness.STRICT_STUBS);

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

    private void resourceExists() {
        when(restTemplate.getForObject(
                FAKE_URL+"/content/{content_id}",
                Content.class,
                ImmutableMap.of("content_id", CONTENT_ID)
        )).thenReturn(EXPECTED_CONTENT);
    }
}
