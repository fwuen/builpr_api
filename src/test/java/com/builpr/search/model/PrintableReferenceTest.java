package com.builpr.search.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alexander Zeitler
 */
public class PrintableReferenceTest {

    private static final String VALID_ID = "4";

    @Test
    public void create() {
        PrintableReference printableReference = new PrintableReference(VALID_ID);

        Assert.assertNotNull(printableReference);
        Assert.assertEquals(VALID_ID, printableReference.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithNegativeId() {
        new PrintableReference("-1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithIdIsZero() {
        new PrintableReference("0");
    }

}
