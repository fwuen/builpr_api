package com.builpr.search.filter;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Felix Wünsche
 */
public class MinimumRatingFilterTest {

    @Test
    public void createWithLowestPossibleRating() {
        MinimumRatingFilter filter = new MinimumRatingFilter(
                MinimumRatingFilter.LOWEST_POSSIBLE_RATING
        );

        Assert.assertNotNull(filter);
        Assert.assertEquals(MinimumRatingFilter.LOWEST_POSSIBLE_RATING, filter.getMinimumRating());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithNegative() {
        new MinimumRatingFilter(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithTooLowRating() {
        new MinimumRatingFilter(
                MinimumRatingFilter.LOWEST_POSSIBLE_RATING - 1
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithTooHighRating() {
        new MinimumRatingFilter(
                MinimumRatingFilter.HIGHEST_POSSIBLE_RATING + 1
        );
    }

}
