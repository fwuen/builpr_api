package com.builpr.restapi.utils;

import com.builpr.database.bridge.category.Category;
import com.builpr.database.bridge.printable_category.PrintableCategory;
import com.builpr.database.bridge.printable_category.PrintableCategoryImpl;
import com.builpr.database.service.DatabasePrintableCategoryManager;

import java.util.List;

/**
 * PrintableCategoryHelper
 */
public class PrintableCategoryHelper {

    private DatabasePrintableCategoryManager databasePrintableCategoryManager;

    public PrintableCategoryHelper() {
        databasePrintableCategoryManager = new DatabasePrintableCategoryManager();
    }

    /**
     * Create PrintableCategories
     *
     * @param list        List<Category>
     * @param printableID int
     */
    public void createCategories(List<Category> list, int printableID) {
        for (Category category : list) {
            if (category.getCategoryId() != 0) {
                PrintableCategoryImpl printableCategory = new PrintableCategoryImpl();
                printableCategory.setPrintableId(printableID);
                printableCategory.setCategoryId(category.getCategoryId());
                databasePrintableCategoryManager.persist(printableCategory);
            }
        }
    }
}
