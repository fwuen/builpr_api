package com.builpr.search.model;

import org.junit.Assert;
import org.junit.Test;

public class PrintModelReferenceTest {

    private static final int VALID_ID = 4;

    @Test
    public void create() {
        PrintModelReference printModelReference = new PrintModelReference(VALID_ID);

        Assert.assertNotNull(printModelReference);
        Assert.assertEquals(VALID_ID, printModelReference.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithNegativeId() {
        new PrintModelReference(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithIdIsZero() {
        new PrintModelReference(0);
    }

}
