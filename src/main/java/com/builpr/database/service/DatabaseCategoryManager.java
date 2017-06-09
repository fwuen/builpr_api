package com.builpr.database.service;


import com.builpr.database.bridge.category.Category;
import com.builpr.database.bridge.category.CategoryManager;
import com.builpr.database.bridge.printable_category.PrintableCategory;

import java.util.List;
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
     *
     * @param printableID id of the printable
     * @return a list with every category the given printable has
     */
    public List<Category> getCategoriesForPrintable(int printableID) {
        return printableCategoryManager.getDao().stream().filter(PrintableCategory.PRINTABLE_ID.equal(printableID))
                .map(getDao().finderBy(PrintableCategory.CATEGORY_ID)).collect(toList());
    }

}
