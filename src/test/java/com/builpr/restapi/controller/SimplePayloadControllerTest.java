
package com.builpr.restapi.controller;

import com.builpr.Constants;
import com.builpr.restapi.model.SimplePayload;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SimplePayloadControllerTest extends ControllerTest {

    private static final String KEY = "payload";
    private static final String VALUE = "testWithPayload";



    @Test
    public void testSecurity() throws Exception {
        mockMvc.perform(
                get(Constants.URL_SIMPLEPAYLOAD)
        )
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(TEST_USER_NAME)
    public void testWithPayload() throws Exception {
        MvcResult result = mockMvc.perform(
                get(Constants.URL_SIMPLEPAYLOAD).param(KEY, VALUE)
        )
            .andExpect(status().isOk())
            .andReturn();

        SimplePayload payload = getResponseBodyOf(result, SimplePayload.class);

        Assert.assertNotNull(payload);
        Assert.assertNotNull(payload.getPayload());
        Assert.assertEquals(VALUE, payload.getPayload());
    }

    @Test
    @WithMockUser(TEST_USER_NAME)
    public void testWithoutPayload() throws Exception {
        MvcResult result = mockMvc.perform(
                get(Constants.URL_SIMPLEPAYLOAD)
        )
            .andExpect(status().isOk())
            .andReturn();

        SimplePayload payload = getResponseBodyOf(result, SimplePayload.class);

        Assert.assertNotNull(payload);
        Assert.assertNotNull(payload.getPayload());
        Assert.assertTrue(payload.getPayload().length() > 0);
    }

}
