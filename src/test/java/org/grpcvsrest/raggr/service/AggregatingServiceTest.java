package org.grpcvsrest.raggr.service;

import com.google.common.collect.ImmutableMap;
import org.grpcvsrest.raggr.MockitoTest;
import org.grpcvsrest.raggr.datasource.Content;
import org.grpcvsrest.raggr.datasource.Datasource;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class AggregatingServiceTest extends MockitoTest{

    private static final int CONTENT_ID_A = 1;
    private static final int CONTENT_ID_B = 1;
    private static final String CONTENT_STRING_A = "foobar";
    private static final String CONTENT_STRING_B = "foobar2";
    private static final Content CONTENT_A = new Content(
            CONTENT_ID_A,
            CONTENT_STRING_A,
            null);
    private static final Content CONTENT_B = new Content(
            CONTENT_ID_B,
            CONTENT_STRING_B,
            null);

    @Mock
    private Datasource sourceA;
    @Mock
    private Datasource sourceB;
    @Mock
    private IdMapper idMapper;

    private AggregatingService service;

    @Before
    public void setup() {
        service = new AggregatingService(sourceA, sourceB, "A", idMapper);
    }

    @Test
    public void testFetch() {
        // given
        twoItems();
        // when
        AggregatedContent resultA = service.fetch(1);
        AggregatedContent resultB = service.fetch(2);
        AggregatedContent resultC = service.fetch(3);

        // then
        assertThat(resultA).isEqualTo(new AggregatedContent(1, "A", CONTENT_STRING_A, CONTENT_ID_A, 2));
        assertThat(resultB).isEqualTo(new AggregatedContent(2, "B", CONTENT_STRING_B, CONTENT_ID_B, null));
        assertThat(resultC).isNull();
    }

    private void twoItems() {
        when(idMapper.getMap()).thenReturn(ImmutableMap.of(
                1, new IdMapper.CategoryId("A", CONTENT_ID_A),
                2, new IdMapper.CategoryId("B", CONTENT_ID_B)
        ));

        when(sourceA.fetch(CONTENT_ID_A)).thenReturn(CONTENT_A);
        when(sourceB.fetch(CONTENT_ID_B)).thenReturn(CONTENT_B);
    }



}