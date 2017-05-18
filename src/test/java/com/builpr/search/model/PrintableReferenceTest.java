package com.builpr.search.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Provides test-methods for PrintableReference-class
 */
public class PrintableReferenceTest {

    private static final int VALID_ID = 4;
    
    /**
     * Create a PrintableReference-object with a legitimate ID as parameter
     */
    @Test
    public void create() {
        PrintableReference printableReference = new PrintableReference(VALID_ID);

        Assert.assertNotNull(printableReference);
        Assert.assertEquals(VALID_ID, printableReference.getId());
    }
    
    /**
     * Try to create a PrintableReference-object with a negative ID
     * The test should fail with an IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void createWithNegativeId() {
        new PrintableReference(-1);
    }
    
    /**
     * Try to create a PrintableReference-object with 0 as ID
     * The test should fail with an IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void createWithIdIsZero() {
        new PrintableReference(0);
    }

}
