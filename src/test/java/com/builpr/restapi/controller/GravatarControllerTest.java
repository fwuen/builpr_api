package com.builpr.restapi.controller;

import com.builpr.Constants;
import com.builpr.restapi.model.Response.GravatarResponse;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GravatarControllerTest extends ControllerTest {

    private static final String PARAM_KEY = "email";
    private static final String PARAM_VALUE = "syntarex@gmail.com";



    @Test
    public void getGravatarWithValidEmail() throws Exception {
        MvcResult result = mockMvc.perform(
                get(Constants.URL_GRAVATAR).param(PARAM_KEY, PARAM_VALUE)
        )
                .andExpect(status().isOk())
                .andReturn();

        GravatarResponse response = getResponseBodyOf(result, GravatarResponse.class);

        Assert.assertNotNull(response);
        Assert.assertEquals(true, response.isSuccess());
        Assert.assertNotNull(response.getPayload());
        Assert.assertTrue(response.getPayload().length() > 0);

        System.out.println(response.getPayload());
    }

    @Test
    public void getGravatarWithInvalidEmail() throws Exception {
        MvcResult result = mockMvc.perform(
                get(Constants.URL_GRAVATAR).param(PARAM_KEY, "invalid")
        )
                .andExpect(status().isOk())
                .andReturn();

        GravatarResponse response = getResponseBodyOf(result, GravatarResponse.class);

        Assert.assertNotNull(response);
        Assert.assertEquals(true, response.isSuccess());
        Assert.assertNotNull(response.getPayload());
        Assert.assertTrue(response.getPayload().length() > 0);
        Assert.assertEquals(Constants.USER_IMAGE_GRAVATAR_NOT_SET, response.getPayload());
    }

    @Test
    public void getGravatarWithNoEmail() throws Exception {
        mockMvc.perform(
                get(Constants.URL_GRAVATAR)
        )
                .andExpect(status().isBadRequest());
    }

}
