package org.grpcvsrest.raggr.datapump;

import org.grpcvsrest.raggr.MockitoTest;
import org.grpcvsrest.raggr.datasource.Content;
import org.grpcvsrest.raggr.datasource.Datasource;
import org.grpcvsrest.raggr.repo.AggregatedContent;
import org.grpcvsrest.raggr.repo.InMemoryAggregatedContentRepo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.verify;

public class DataPumpTest extends MockitoTest{

    private static final String POKEMON = "Pokemon";
    @Mock
    private InMemoryAggregatedContentRepo repo;
    @Mock
    private Datasource datasource;

    private DataPump dataPump;

    @Before
    public void setup() {
        dataPump = new DataPump(repo, datasource, POKEMON);
    }

    @Test
    public void testFetchAndSave() {
        // given
        willReturn(new Content(1, "one", 2)).given(datasource).fetch(1);
        willReturn(new Content(2, "two", 3)).given(datasource).fetch(2);
        willReturn(new Content(3, "three", null)).given(datasource).fetch(3);

        // when
        dataPump.fetchAndSave();

        // then
        verify(repo).save(new AggregatedContent(null, POKEMON, "one", 1));
        verify(repo).save(new AggregatedContent(null, POKEMON, "two", 2));
        verify(repo).save(new AggregatedContent(null, POKEMON, "three", 3));


    }
}
