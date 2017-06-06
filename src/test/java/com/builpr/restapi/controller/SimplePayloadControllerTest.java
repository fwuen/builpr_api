
package com.builpr.restapi.controller;

import com.builpr.restapi.model.SimplePayload;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SimplePayloadControllerTest extends ControllerTest<SimplePayloadController> {

    private static final String URL = "/simplepayload";
    private static final String KEY = "payload";
    private static final String VALUE = "testWithPayload";


    protected SimplePayloadController createController() {
        return new SimplePayloadController();
    }


    @Test
    public void testWithPayload() throws Exception {
        MvcResult result = mockMvc.perform(
                get(URL).param(KEY, VALUE)
        )
            .andExpect(status().isOk())
            .andReturn();

        SimplePayload payload = getResponseBodyOf(result, SimplePayload.class);

        Assert.assertNotNull(payload);
        Assert.assertNotNull(payload.getPayload());
        Assert.assertEquals(VALUE, payload.getPayload());
    }

    @Test
    public void testWithoutPayload() throws Exception {
        MvcResult result = mockMvc.perform(
                get(URL)
        )
            .andExpect(status().isOk())
            .andReturn();

        SimplePayload payload = getResponseBodyOf(result, SimplePayload.class);

        Assert.assertNotNull(payload);
        Assert.assertNotNull(payload.getPayload());
        Assert.assertTrue(payload.getPayload().length() > 0);
    }

}
