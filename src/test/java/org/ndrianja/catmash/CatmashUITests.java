package org.ndrianja.catmash;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CatmashUITests {

    @Autowired
    private MockMvc mvc;

    // @MockBean
    // private EmployeeService service;

    @Before
    public void init() throws JsonParseException, JsonMappingException, IOException {

    }

    @Test
    public void homePageTest() throws Exception {
        mvc.perform(get("/").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(view().name("scores"));
    }

    @Test
    public void scoresPageTest() throws Exception {
        mvc.perform(get("/scores").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(view().name("scores"));
    }

    @Test
    public void votePageTest() throws Exception {
        mvc.perform(get("/vote").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(view().name("vote"));
    }

    @Test
    public void votePageWithIdTest() throws Exception {
        mvc.perform(get("/vote/MTgwODA3MA").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                .andExpect(view().name("vote"));
    }

}
