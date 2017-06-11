package com.builpr.database.service;


import com.builpr.database.bridge.category.Category;
import com.builpr.database.bridge.category.CategoryImpl;
import com.builpr.database.bridge.category.CategoryManager;
import com.builpr.database.bridge.printable_category.PrintableCategory;
import com.builpr.restapi.model.Request.Printable.PrintableEditRequest;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * printableCategory service
 */
public class DatabaseCategoryManager extends DatabaseManager<CategoryManager> {

    private DatabasePrintableCategoryManager printableCategoryManager;

    public DatabaseCategoryManager() {
        super(CategoryManager.class);

        printableCategoryManager = new DatabasePrintableCategoryManager();
    }

    /**
     * @param printableID id of the printable
     * @return a list with every category the given printable has
     */
    public List<Category> getCategoriesForPrintable(int printableID) {
        return printableCategoryManager.getDao().stream().filter(PrintableCategory.PRINTABLE_ID.equal(printableID))
                .map(getDao().finderBy(PrintableCategory.CATEGORY_ID)).collect(toList());
    }

    /**
     * @param request PrintableEditRequest
     * @return void
     */
    public void update(PrintableEditRequest request) {
        List<String> categories = request.getCategories();
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
