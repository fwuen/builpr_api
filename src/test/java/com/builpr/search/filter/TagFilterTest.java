package com.builpr.search.filter;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Provides test-methods for TagFilter-class
 */
public class TagFilterTest {

    /**
     * Create a TagFilter-object with a List-object as legitimate parameter
     */
    @Test
    public void create() {
        List<String> tags = Lists.newArrayList("tag1", "tag2", "tag3");

        TagFilter filter = new TagFilter(tags);

        Assert.assertNotNull(filter);
        Assert.assertEquals(tags.size(), filter.getTags().size());
        Assert.assertTrue(filter.getTags().containsAll(tags));
    }

    /**
     * Try to create a TagFilter-object with an empty List-object as parameter
     * The test should fail with an IllegalArgumentException
     */
    @Test(expected = IllegalArgumentException.class)
    public void createWithEmptyList() {
        new TagFilter(Lists.newArrayList());
    }

    /**
     * Try to create a TagFilter-object with null as parameter
     * The test should fail with a NullPointerException
     */
    @Test(expected = NullPointerException.class)
    public void createWithNull() {
        new TagFilter(null);
    }

}
