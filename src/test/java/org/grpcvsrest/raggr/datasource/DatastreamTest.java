package org.grpcvsrest.raggr.datasource;

import org.grpcvsrest.raggr.MockitoTest;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willReturn;

public class DatastreamTest extends MockitoTest{

    private static final Content CONTENT_1 = new Content(1, "foo", 2);
    private static final Content CONTENT_2 = new Content(2, "bar", null);
    @Mock
    private Datasource datasource;

    @InjectMocks
    private Datastream datastream;

    @Test
    public void testNext() {
        // given
        datasourceHasTwoElements();

        // when
        Content result = datastream.next();
        // then
        assertThat(result).isEqualTo(CONTENT_1);

        // and when
        result = datastream.next();
        // then
        assertThat(result).isEqualTo(CONTENT_2);

        // and when
        result = datastream.next();
        // then
        assertThat(result).isNull();
    }

    private void datasourceHasTwoElements() {
        willReturn(CONTENT_1).given(datasource).fetch(1);
        willReturn(CONTENT_2).given(datasource).fetch(2);
    }

}