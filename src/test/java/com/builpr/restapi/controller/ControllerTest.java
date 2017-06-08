package com.builpr.restapi.controller;

import com.builpr.DataIntegrityTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Preconditions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
@WebAppConfiguration
@AutoConfigureMockMvc
public abstract class ControllerTest extends DataIntegrityTest {

    @Autowired
    protected MockMvc mockMvc;



    private ObjectMapper objectMapper = new ObjectMapper();



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

}
