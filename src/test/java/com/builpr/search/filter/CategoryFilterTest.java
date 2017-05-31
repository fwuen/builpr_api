package com.builpr.search.filter;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Provides test-methods for CategoryFilter-class
 */
public class CategoryFilterTest {

    /**
     * Create a CategoryFilter-object with a List-object as legitimate parameter
     */
    @Test
    public void create() {
        List<String> categories = Lists.newArrayList("category1", "category2", "category3");

        CategoryFilter filter = new CategoryFilter(categories);

        Assert.assertNotNull(filter);
        Assert.assertEquals(categories.size(), filter.getCategories().size());
        Assert.assertTrue(filter.getCategories().containsAll(categories));
    }

    /**
     * Try to create a CategoryFilter-object with an empty List-object as parameter
     * The test should fail with an IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void createWithEmptyList() {
        new CategoryFilter(Lists.newArrayList());
    }

    /**
     * Try to create a CategoryFilter-object with null as parameter
     * The test should fail with a NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void createWithNull() {
        new CategoryFilter(null);
    }

}
