package com.builpr.search.filter;

import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TagFilterTest {

    @Test
    public void create() {
        List<String> tags = Lists.newArrayList("tag1", "tag2", "tag3");

        TagFilter filter = new TagFilter(tags);

        Assert.assertNotNull(filter);
        Assert.assertEquals(tags.size(), filter.getTags().size());
        Assert.assertTrue(filter.getTags().containsAll(tags));
    }

    @Test(expected = IllegalArgumentException.class)
    public void createWithEmptyList() {
        new TagFilter(Lists.newArrayList());
    }

    @Test(expected = NullPointerException.class)
    public void createWithNull() {
        new TagFilter(null);
    }

}
