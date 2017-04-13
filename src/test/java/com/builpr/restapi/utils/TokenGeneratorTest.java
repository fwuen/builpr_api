package com.builpr.restapi.utils;

import org.junit.Assert;
import org.junit.Test;

public class TokenGeneratorTest {

    private static final int SIZE = 128;



    @Test
    public void generate() {
        String token = TokenGenerator.generate();

        Assert.assertNotNull(token);
        Assert.assertEquals(SIZE, token.length());
    }

}
