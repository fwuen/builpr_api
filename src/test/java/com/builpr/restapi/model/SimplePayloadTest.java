package com.builpr.restapi.model;

import org.junit.Assert;
import org.junit.Test;

public class SimplePayloadTest {

    private static final String PAYLOAD = "test";



    @Test(expected = NullPointerException.class)
    public void createWithNull() {
        new SimplePayload(null);
    }

    @Test
    public void createWithEmptyPayload() {
        SimplePayload simplePayload = new SimplePayload("");

        Assert.assertNotNull(simplePayload);
        Assert.assertNotNull(simplePayload.getPayload());
        Assert.assertEquals("", simplePayload.getPayload());
    }

    @Test
    public void createWithPayload() {
        SimplePayload simplePayload = new SimplePayload(PAYLOAD);

        Assert.assertNotNull(simplePayload);
        Assert.assertNotNull(simplePayload.getPayload());
        Assert.assertEquals(PAYLOAD, simplePayload.getPayload());
    }

    @Test
    public void equals() {
        SimplePayload simplePayload1 = new SimplePayload(PAYLOAD);
        SimplePayload simplePayload2 = new SimplePayload(PAYLOAD);

        Assert.assertNotNull(simplePayload1);
        Assert.assertNotNull(simplePayload2);
        Assert.assertEquals(simplePayload1, simplePayload2);
    }

}
