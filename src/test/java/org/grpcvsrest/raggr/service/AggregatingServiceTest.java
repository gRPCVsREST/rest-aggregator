package org.grpcvsrest.raggr.service;

import org.grpcvsrest.raggr.MockitoTest;
import org.grpcvsrest.raggr.datasource.Content;
import org.grpcvsrest.raggr.datasource.Datastream;
import org.grpcvsrest.raggr.repo.AggregatedContent;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

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
    private Datastream streamA;
    @Mock
    private Datastream streamB;

    private AggregatingService service;

    @Before
    public void setup() {
        service = new AggregatingService(streamA, streamB, "A", "B");
    }

    @Test
    public void testFetch_BothStreamsActive() {
        // given
        streamAActive();
        streamBActive();

        // when
        AggregatedContent resultA = service.fetch(1);
        AggregatedContent resultB = service.fetch(2);
        AggregatedContent resultC = service.fetch(3);

        // then
        assertThat(resultA).isEqualTo(new AggregatedContent(1, "A", CONTENT_STRING_A, CONTENT_ID_A));
        assertThat(resultB).isEqualTo(new AggregatedContent(2, "B", CONTENT_STRING_B, CONTENT_ID_B));
        assertThat(resultC).isNull();
    }

    @Test
    public void testFetch_BothStreamsActive_SkipOneElement() {
        // given
        streamAActive();
        streamBActive();

        // when
        AggregatedContent resultA = service.fetch(2);
        AggregatedContent resultB = service.fetch(3);

        // then
        assertThat(resultA).isEqualTo(new AggregatedContent(2, "B", CONTENT_STRING_B, CONTENT_ID_B));
        assertThat(resultB).isNull();
    }


    @Test
    public void testFetch_OnlyOneStreamActive() {
        // given
        streamADone();
        streamBActive();

        // when
        AggregatedContent resultA = service.fetch(1);
        AggregatedContent resultB = service.fetch(2);
        AggregatedContent resultC = service.fetch(3);

        // then
        assertThat(resultA).isEqualTo(new AggregatedContent(1, "B", CONTENT_STRING_B, CONTENT_ID_B));
        assertThat(resultB).isNull();
        assertThat(resultC).isNull();
    }

    private void streamADone() {
        doReturn(null).when(streamA).next();
    }


    private void streamAActive() {
        doReturn(CONTENT_A, null).when(streamA).next();

    }

    private void streamBActive() {
        doReturn(CONTENT_B, null).when(streamB).next();
    }
}