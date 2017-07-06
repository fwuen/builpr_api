package com.builpr.restapi.converter;

import com.builpr.database.bridge.category.Category;
import com.builpr.database.bridge.category.CategoryImpl;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Markus Goller
 *
 * Tests for CategoryStringConverter
 */
public class CategoryToStringConverterTest {
    private static List<Category> list = new ArrayList<>();

    public CategoryToStringConverterTest() {
        Category category1 = new CategoryImpl()
                .setCategoryName("neueCategory1");
        Category category2 = new CategoryImpl()
                .setCategoryName("neueCategory2");
        Category category3 = new CategoryImpl()
                .setCategoryName("neueCategory3");

        list.add(category1);
        list.add(category2);
        list.add(category3);
    }

    @Test
    public void testConvertCategoriesToString() {
        List<String> categoryNames = CategoryToStringConverter.convertCategoriesToString(list);
        Assert.assertTrue(categoryNames.size() == list.size());
        Assert.assertTrue(Objects.equals(categoryNames.get(0), list.get(0).getCategoryName()));
        Assert.assertTrue(Objects.equals(categoryNames.get(1), list.get(1).getCategoryName()));
        Assert.assertTrue(Objects.equals(categoryNames.get(2), list.get(2).getCategoryName()));
    }
}
