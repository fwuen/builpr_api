package com.builpr.search.filter;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Provides test-methods for FileFilter-class
 */
public class FileFilterTest {

    /**
     * Create a FileFilter-object with a legitimate List-object as parameter
     */
    @Test
    public void create() {
        List<String> files = new ArrayList<String>();
        files.add("file1");
        files.add("file2");

        FileFilter f = new FileFilter(files);
        Assert.assertNotNull(f);
        Assert.assertEquals(files.size(), f.getFileTypes().size());
        Assert.assertTrue(f.getFileTypes().containsAll(files));
    }

    /**
     * Try to create a FileFilter-object with an empty List-object as parameter
     * The test should fail with an IllegalArgumentException
     */
    @Test (expected = IllegalArgumentException.class)
    public void createWithEmptyList() {
        new FileFilter(new ArrayList<>());
    }

    /**
     * Try to create a FileFilter-object with null as parameter
     * The test should fail with a NullPointerException
     */
    @Test (expected = NullPointerException.class)
    public void createWithNull() {
        new FileFilter(null);
    }

}
