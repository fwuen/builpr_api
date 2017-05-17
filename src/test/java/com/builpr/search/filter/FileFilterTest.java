package com.builpr.search.filter;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class FileFilterTest {

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

    @Test
    public void createWithOneElement() {
        List<String> file = new ArrayList<String>();
        file.add("file1");

        FileFilter f = new FileFilter(file);
        Assert.assertNotNull(f);
        Assert.assertEquals(file.size(), f.getFileTypes().size());
        Assert.assertTrue(f.getFileTypes().containsAll(file));
    }

    @Test (expected = IllegalArgumentException.class)
    public void createWithEmptyList() {
        new FileFilter(new ArrayList<>());
    }

    @Test (expected = NullPointerException.class)
    public void createWithNull() {
        new FileFilter(null);
    }

}
