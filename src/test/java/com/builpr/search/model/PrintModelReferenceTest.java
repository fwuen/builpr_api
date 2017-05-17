package com.builpr.search.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Provides test-methods for PrintModelReference-class
 */
public class PrintModelReferenceTest {

    private static final int VALID_ID = 4;
    
    /**
     * Create a PrintModelReference-object with a legitimate ID as parameter
     */
    @Test
    public void create() {
        PrintModelReference printModelReference = new PrintModelReference(VALID_ID);

        Assert.assertNotNull(printModelReference);
        Assert.assertEquals(VALID_ID, printModelReference.getId());
    }
    
    /**
     * Try to create a PrintModelReference-object with a negative ID
     * The test should fail with an IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void createWithNegativeId() {
        new PrintModelReference(-1);
    }
    
    /**
     * Try to create a PrintModelReference-object with 0 as ID
     * The test should fail with an IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void createWithIdIsZero() {
        new PrintModelReference(0);
    }

}
