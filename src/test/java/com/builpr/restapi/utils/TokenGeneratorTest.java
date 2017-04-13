package com.builpr.restapi.utils;

import com.google.common.collect.Sets;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class TokenGeneratorTest {

    @Test
    public void create() {
        new TokenGenerator();
    }

    @Test
    public void defaultTokenSizeIsGreaterThenZero() {
        Assert.assertTrue(new TokenGenerator().getTokenSize() > 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setTokenSizeToZero() {
        new TokenGenerator().setTokenSize(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNegativeTokenSize() {
        new TokenGenerator().setTokenSize(-1);
    }

    @Test
    public void setPositiveTokenSize() {
        TokenGenerator tokenGenerator = new TokenGenerator();

        Assert.assertNotNull(tokenGenerator);

        int newSize = 77;
        tokenGenerator.setTokenSize(newSize);

        Assert.assertEquals(newSize, tokenGenerator.getTokenSize());
    }

    @Test
    public void generate() {
        TokenGenerator tokenGenerator = new TokenGenerator();

        Assert.assertNotNull(tokenGenerator);

        String token = tokenGenerator.generate();

        Assert.assertNotNull(token);
        Assert.assertEquals(tokenGenerator.getTokenSize(), token.length());
    }

    @Test
    public void setTokenSizeAndGenerate() {
        TokenGenerator tokenGenerator = new TokenGenerator();

        Assert.assertNotNull(tokenGenerator);

        int newSize = 55;
        tokenGenerator.setTokenSize(newSize);

        Assert.assertEquals(newSize, tokenGenerator.getTokenSize());

        String token = tokenGenerator.generate();

        Assert.assertNotNull(token);
        Assert.assertEquals(newSize, token.length());
    }

    @Test
    public void tokenIsUnique() {
        Set<String> tokens = Sets.newHashSet();

        TokenGenerator tokenGenerator = new TokenGenerator();

        Assert.assertNotNull(tokenGenerator);

        for(int i = 0; i < 100; i++) {
            tokens.add(tokenGenerator.generate());
        }
    }

}