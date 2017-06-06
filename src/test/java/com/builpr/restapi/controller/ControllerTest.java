package com.builpr.restapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Preconditions;
import org.junit.Before;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

public abstract class ControllerTest<ControllerType> {

    private ObjectMapper objectMapper = new ObjectMapper();
    protected MockMvc mockMvc;

    protected abstract ControllerType createController();

    public <T> T getResponseBodyOf(MvcResult result, Class<T> clazz) {
        Preconditions.checkNotNull(result);

        try {
            return objectMapper.readValue(
                    result.getResponse().getContentAsString(),
                    clazz
            );
        } catch(IOException ex) {
            throw new RuntimeException(ex);
        }
    }



    @Before
    public void mockController() {
        this.mockMvc = standaloneSetup(createController()).build();
    }

}
