package com.builpr.restapi.converter;

import com.builpr.database.bridge.category.Category;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class CategoryToStringConverter {

    public static List<String> convertCategoriesToString(List<Category> categories) {
        List<String> categoryNames = new ArrayList<>();
        for (Category category : categories) {
            categoryNames.add(category.getCategoryName());
        }
        return categoryNames;
    }
}
