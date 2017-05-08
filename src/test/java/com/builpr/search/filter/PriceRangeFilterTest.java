package com.builpr.search.filter;

import org.apache.http.cookie.SM;
import org.junit.Assert;
import org.junit.Test;

public class PriceRangeFilterTest {

    private static final int SMALL = 1;
    private static final int BIG = 100;

    @Test
    public void create() {
        PriceRangeFilter filter = new PriceRangeFilter(SMALL, BIG);

        Assert.assertNotNull(filter);
        Assert.assertEquals(SMALL, filter.getFromPrice());
        Assert.assertEquals(BIG, filter.getToPrice());
    }

    @Test
    public void createWithFromPriceIsZero() {
        PriceRangeFilter filter = new PriceRangeFilter(0, BIG);

        Assert.assertNotNull(filter);
        Assert.assertEquals(0, filter.getFromPrice());
        Assert.assertEquals(BIG, filter.getToPrice());
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithToPriceIsZero() {
        new PriceRangeFilter(SMALL, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithBothPricesAreZero() {
        new PriceRangeFilter(0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithToPriceIsSmallerThenFromPrice() {
        new PriceRangeFilter(BIG, SMALL);
    }

}
