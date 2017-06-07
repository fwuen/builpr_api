
package com.builpr.restapi.controller;

import com.builpr.restapi.model.SimplePayload;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/* TODO: Auth funktioniert aber User Abgleich läuft irgnedwie nicht. */
public class SimplePayloadControllerTest extends ControllerTest {

    private static final String URL = "/simplepayload";
    private static final String KEY = "payload";
    private static final String VALUE = "testWithPayload";



    @Test
    @Ignore
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
    @Ignore
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
