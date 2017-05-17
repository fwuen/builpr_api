package com.builpr.search.filter;

import org.junit.Assert;
import org.junit.Test;

/**
 * Proves test-methods for MinimumRatingFilter-class
 */
public class MinimumRatingFilterTest {

    /**
     * Create a MinimumRatingFilter-object with the lowest possible rating as parameter
     */
    @Test
    public void createWithLowestPossibleRating() {
        MinimumRatingFilter filter = new MinimumRatingFilter(
                MinimumRatingFilter.LOWEST_POSSIBLE_RATING
        );

        Assert.assertNotNull(filter);
        Assert.assertEquals(MinimumRatingFilter.LOWEST_POSSIBLE_RATING, filter.getMinimumRating());
    }

    /**
     * Try to create a MinimumRatingFilter-object with 0 as a parameter
     * The test should fail with an IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void createWithZero() {
        new MinimumRatingFilter(0);
    }

    /**
     * Try to create a MinimumRatingFilter-object with a negative parameter
     * The test should fail with an IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void createWithNegative() {
        new MinimumRatingFilter(-1);
    }

    /**
     * Try to create a MinimumRatingFilter-object with a too low rating as parameter
     * The test should fail with an IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void createWithTooLowRating() {
        new MinimumRatingFilter(
                MinimumRatingFilter.LOWEST_POSSIBLE_RATING - 1
        );
    }

    /**
     * Try to create a MinimumRatingfilter-object with a too high rating as parameter
     * The test should fail with an IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void createWithTooHighRating() {
        new MinimumRatingFilter(
                MinimumRatingFilter.HIGHEST_POSSIBLE_RATING + 1
        );
    }

}
