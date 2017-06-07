package com.builpr.restapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Preconditions;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@WebAppConfiguration
@SpringBootTest
public abstract class ControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;



    private ObjectMapper objectMapper = new ObjectMapper();
    protected MockMvc mockMvc;



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
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();
    }

}
