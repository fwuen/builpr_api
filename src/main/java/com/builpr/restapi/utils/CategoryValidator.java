package com.builpr.restapi.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Validator for categories
 */
public class CategoryValidator {
    /**
     * @param categories List<String>
     * @return List<String>
     */
    public List<String> checkCategories(List<String> categories) {
        List<String> newCategories = new ArrayList<>();

        for (String category : categories) {
            if (category.matches("^[\\pL\\pN\\p{Pc}]*$")) {
                category = category.toLowerCase();
                newCategories.add(category);
            }
        }
        // add elements to setOfCategories in order to delete duplicates
        Set<String> setOfCategories = new HashSet<>();
        setOfCategories.addAll(newCategories);
        newCategories.clear();
        newCategories.addAll(setOfCategories);
        return newCategories;
    }
}
