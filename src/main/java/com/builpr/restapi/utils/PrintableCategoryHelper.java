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

    /**
     * @param printableID int
     * @param categoryID  int
     * @return void
     */

    public void create(int printableID, int categoryID) {
        PrintableCategoryImpl printableCategory = new PrintableCategoryImpl();
        printableCategory.setPrintableId(printableID);
        printableCategory.setCategoryId(categoryID);
        databasePrintableCategoryManager.persist(printableCategory);
    }

    /**
     * @param printableID int
     * @return void
     */
    public void deleteCategoriesForPrintable(int printableID) {
        List<PrintableCategory> printableCategories = databasePrintableCategoryManager.getListByID(printableID);
        if (printableCategories != null) {
            for (PrintableCategory printableCategory : printableCategories) {
                databasePrintableCategoryManager.delete(printableCategory);
            }
        }
    }

}
