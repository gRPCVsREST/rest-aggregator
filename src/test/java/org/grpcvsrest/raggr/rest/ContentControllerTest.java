package org.grpcvsrest.raggr.rest;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.grpcvsrest.raggr.repo.AggregatedContent;
import org.grpcvsrest.raggr.service.AggregatingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@RunWith(SpringRunner.class)
public class ContentControllerTest {
    private static final int CONTENT_ID = 1;
    private static final String POKEMON = "Pokemon";
    private static final String PIKACHU = "Pikachu";
    private static final int ORIGINAL_ID = 42;

    @MockBean
    private AggregatingService aggregatingService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testExistingContent() throws Exception {
        // given
        recordExists();

        // when
        mockMvc.perform(
                get("/content/1")
        ) // then
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJsonContent()));

    }


    @Test
    public void testExistingContentJson() throws Exception {
        // given
        recordExists();

        // when
        mockMvc.perform(
                get("/content/1")
                .contentType("application/json")
                .accept("application/json")
        ) // then
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJsonContent()));

    }

    @Test
    public void testExistingContent_XML() throws Exception {
        // given
        recordExists();

        // when
        mockMvc.perform(
                get("/content/1")
                .contentType("application/xml")
                .accept("application/xml")
        ) // then
                .andExpect(status().is(200))
                .andExpect(content().xml(expectedXmlContent()));

    }

    @Test
    public void test404() throws Exception {
        // given
        recordMissing();

        // when
        mockMvc.perform(
                get("/content/1")
                .contentType("application/json")
                .accept("application/json")

        ) // then
                .andExpect(status().is(404));

    }

    private String expectedJsonContent() throws IOException {
        return Resources.toString(
                Resources.getResource("aggregated_content.json"),
                Charsets.UTF_8);
    }

    private String expectedXmlContent() throws IOException {
        return Resources.toString(
                Resources.getResource("aggregated_content.xml"),
                Charsets.UTF_8);
    }

    private void recordMissing() {
        when(aggregatingService.fetch(CONTENT_ID)).thenReturn(null);
    }

    private void recordExists() {
        when(aggregatingService.fetch(CONTENT_ID))
                .thenReturn(new AggregatedContent(CONTENT_ID, POKEMON, PIKACHU, ORIGINAL_ID));
    }

}
