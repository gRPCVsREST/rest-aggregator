package org.grpcvsrest.raggr.rest;

import org.grpcvsrest.raggr.datapump.DataPump;
import org.grpcvsrest.raggr.repo.InMemoryContentRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@RunWith(SpringRunner.class)
public class InternalControllerTest {
    @MockBean(name = "datapumpA")
    private DataPump dataPumpA;

    @MockBean(name = "datapumpB")
    private DataPump dataPumpB;

    @MockBean
    private InMemoryContentRepo repo;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testPumpAll() throws Exception {
        // when
        mockMvc.perform(post("/pump")
        ) // then
                .andExpect(status().is(200));

        verify(dataPumpA).fetchAndSave();
        verify(dataPumpB).fetchAndSave();
    }

}
