package com.builpr.database.service;


import com.builpr.database.bridge.category.Category;
import com.builpr.database.bridge.category.CategoryImpl;
import com.builpr.database.bridge.category.CategoryManager;
import com.builpr.database.bridge.printable_category.PrintableCategory;
import com.builpr.restapi.converter.CategoryToStringConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * @author Markus Goller
 *
 * printableCategory service
 */
public class DatabaseCategoryManager extends DatabaseManager<CategoryManager> {
    public DatabaseCategoryManager() {
        super(CategoryManager.class);
    }

    /**
     * Collects all categories as objects for a Printable
     *
     * @param printableID id of the printable
     * @return a list with every category the given printable has
     */
    public List<Category> getCategoriesForPrintable(int printableID) {
        DatabasePrintableCategoryManager printableCategoryManager = new DatabasePrintableCategoryManager();
        return printableCategoryManager.getDao().stream().filter(PrintableCategory.PRINTABLE_ID.equal(printableID))
                .map(getDao().finderBy(PrintableCategory.CATEGORY_ID)).collect(toList());
    }

    /**
     * Returns a list of category-object
     *
     * @param printableCategories List<PrintableCategory>
     * @return List<Category>
     */
    public List<Category> getCategoriesByPrintableCategoryList(List<PrintableCategory> printableCategories) {
        List<Category> categories = new ArrayList<>();
        for (PrintableCategory printableCategory : printableCategories) {
            List<Category> found = getDao().stream().filter(Category.CATEGORY_ID.equal(printableCategory.getCategoryId())).collect(Collectors.toList());
            categories.add(found.get(0));
        }
        return categories;
    }

    /**
     * Returns a list of category-objects
     *
     * @param list List<String>
     * @return List<Category>
     */
    public List<Category> getCategoriesByList(List<String> list) {
        List<Category> categoryList = new ArrayList<>();
        for (String categoryName : list) {
            List<Category> foundCategory = getDao().stream().filter(Category.CATEGORY_NAME.equal(categoryName)).collect(Collectors.toList());
            if (foundCategory != null && !foundCategory.isEmpty()) {
                categoryList.add(foundCategory.get(0));
            }
        }
        if (categoryList.isEmpty()) {
            return null;
        }
        return categoryList;
    }

    /**
     * If needed the database will get extended by new categories
     *
     * @param categories List<String>
     * @return void
     */
    public void update(List<String> categories) {
        List<Category> existingCategories = getDao().stream().collect(Collectors.toList());
        List<String> categoryNames = CategoryToStringConverter.convertCategoriesToString(existingCategories);
        for (String category : categories) {
            if (!categoryNames.contains(category)) {
                create(category);
            }
        }
    }

    /**
     * Saving a new category in the database
     * @param categoryName String
     * @return void
     */
    public void create(String categoryName) {
        CategoryImpl newCategory = new CategoryImpl();
        newCategory.setCategoryName(categoryName);
        this.getDao().persist(newCategory);
    }
}
