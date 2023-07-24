package br.com.productapi.controllers;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
public class StatusControllerTest {

    private MockMvc mockMvc;

    @Test
    public void testGetApiStatus() throws Exception {
        StatusController statusController = new StatusController();
        mockMvc = MockMvcBuilders.standaloneSetup(statusController).build();

        ResultActions resultActions = mockMvc.perform(get("/api/status")
                .contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(status().isOk());

        resultActions.andExpect(content().contentType(MediaType.APPLICATION_JSON));

        resultActions.andExpect(jsonPath("$.service").value("Product-API"));
        resultActions.andExpect(jsonPath("$.status").value("up"));
        resultActions.andExpect(jsonPath("$.httpStatus").value("OK"));
    }
}