package com.builpr.search.filter;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author Alexander Zeitler
 */
public class CategoryFilterTest {
    
    @Test
    public void create() {
        List<String> categories = Lists.newArrayList("category1", "category2", "category3");
        
        CategoryFilter filter = new CategoryFilter(categories);
        
        Assert.assertNotNull(filter);
        Assert.assertEquals(categories.size(), filter.getCategories().size());
        Assert.assertTrue(filter.getCategories().containsAll(categories));
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void createWithEmptyList() {
        new CategoryFilter(Lists.newArrayList());
    }
    
    @Test(expected = NullPointerException.class)
    public void createWithNull() {
        new CategoryFilter(null);
    }
    
}
