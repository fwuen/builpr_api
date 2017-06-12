package com.builpr.database.service;


import com.builpr.database.bridge.category.Category;
import com.builpr.database.bridge.category.CategoryImpl;
import com.builpr.database.bridge.category.CategoryManager;
import com.builpr.database.bridge.printable_category.PrintableCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * printableCategory service
 */
public class DatabaseCategoryManager extends DatabaseManager<CategoryManager> {

    public DatabaseCategoryManager() {
        super(CategoryManager.class);
    }

    /**
     * @param printableID id of the printable
     * @return a list with every category the given printable has
     */
    public List<Category> getCategoriesForPrintable(int printableID) {
        DatabasePrintableCategoryManager printableCategoryManager = new DatabasePrintableCategoryManager();
        return printableCategoryManager.getDao().stream().filter(PrintableCategory.PRINTABLE_ID.equal(printableID))
                .map(getDao().finderBy(PrintableCategory.CATEGORY_ID)).collect(toList());
    }

    /**
     * @param printableID int
     * @return List<Category>
     */
    public List<Category> getCategoriesByID(int printableID) {
        DatabasePrintableCategoryManager printableCategoryManager = new DatabasePrintableCategoryManager();
        List<PrintableCategory> printableCategories = printableCategoryManager.getListByID(printableID);
        List<Category> categories = new ArrayList<>();
        for (PrintableCategory printableCategory : printableCategories) {
            List<Category> found = getDao().stream().filter(Category.CATEGORY_ID.equal(printableCategory.getCategoryId())).collect(Collectors.toList());
            categories.add(found.get(0));
        }
        return categories;
    }

    /**
     * @param list List<String>
     * @return List<Category>
     */
    public List<Category> getCategoriesByList(List<String> list) {
        List<Category> categoryList = new ArrayList<>();
        for (String categoryName : list) {
            List<Category> foundCategory = getDao().stream().filter(Category.CATEGORY_NAME.equal(categoryName)).collect(Collectors.toList());
            categoryList.add(foundCategory.get(0));
        }
        return categoryList;
    }

    /**
     * @param categories List<String>
     * @return void
     */
    public void update(List<String> categories) {
        List<String> existingCategories = getDao().stream().map(Category.CATEGORY_NAME.getter()).collect(Collectors.toList());
        for (String categoryName : categories) {
            if (!existingCategories.contains(categoryName)) {
                create(categoryName);
            }
        }
    }

    /**
     * @param categoryName String
     * @return void
     */
    public void create(String categoryName) {
        CategoryImpl newCategory = new CategoryImpl();
        newCategory.setCategoryName(categoryName);
        this.getDao().persist(newCategory);
    }

}
