package com.builpr.restapi.converter;

import com.builpr.database.bridge.category.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Converter for Categories
 */
public class CategoryToStringConverter {

    /**
     * Converting a Category to its name
     * @param categories List<Category>
     * @return List<String>
     */
    public static List<String> convertCategoriesToString(List<Category> categories) {
        List<String> categoryNames = new ArrayList<>();
        for (Category category : categories) {
            categoryNames.add(category.getCategoryName());
        }
        return categoryNames;
    }
}
