package org.grpcvsrest.raggr.rest;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.grpcvsrest.raggr.repo.AggregatedContent;
import org.grpcvsrest.raggr.repo.InMemoryContentRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@RunWith(SpringRunner.class)
public class ContentControllerTest {
    private static final int CONTENT_ID = 1;
    private static final String POKEMON = "Pokemon";
    private static final String PIKACHU = "Pikachu";
    private static final int ORIGINAL_ID = 42;

    @MockBean
    private InMemoryContentRepo repo;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testExistingContent() throws Exception {
        // given
        recordExists();

        // when
        mockMvc.perform(post("/content/1")
        ) // then
                .andExpect(status().is(200))
                .andExpect(content().json(expectedContent()));

    }

    private String expectedContent() throws IOException {
        return Resources.toString(
                Resources.getResource("aggregated_content.json"),
                Charsets.UTF_8);
    }

    private void recordExists() {
        when(repo.find(CONTENT_ID)).thenReturn(new AggregatedContent(CONTENT_ID, POKEMON, PIKACHU, ORIGINAL_ID));
    }

}
