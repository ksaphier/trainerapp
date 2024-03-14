package com.ksaphier.trainerapp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.http.MediaType;

@SpringBootTest
@AutoConfigureMockMvc
public class ExerciseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testAddExercise() throws Exception {
        MediaType mediaType = MediaType.APPLICATION_JSON;
        if (mediaType != null) {
            mockMvc.perform(post("/exercises")
                    .contentType(mediaType)
                    .content("{\"name\":\"Test Exercise\",\"description\":\"Test Description\"}"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.name").value("Test Exercise"))
                    .andExpect(jsonPath("$.description").value("Test Description"));
        }
    }
}