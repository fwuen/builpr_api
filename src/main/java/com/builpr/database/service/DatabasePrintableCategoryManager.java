package com.builpr.database.service;

import com.builpr.database.bridge.category.Category;
import com.builpr.database.bridge.printable_category.PrintableCategory;
import com.builpr.database.bridge.printable_category.PrintableCategoryImpl;
import com.builpr.database.bridge.printable_category.PrintableCategoryManager;
import com.builpr.restapi.model.Request.Printable.PrintableEditRequest;


import java.util.List;
import java.util.stream.Collectors;


/**
 * printableCategory manager
 */
public class DatabasePrintableCategoryManager extends DatabaseManager<PrintableCategoryManager> {

    public DatabasePrintableCategoryManager() {
        super(PrintableCategoryManager.class);
    }

    /**
     * @param printableID int
     * @return List<PrintableCategory>
     */
    public List<PrintableCategory> getListByID(int printableID) {
        return getDao().stream().filter(PrintableCategory.PRINTABLE_ID.equal(printableID)).collect(Collectors.toList());
    }

    /**
     * @param list        List<Category>
     * @param printableID int
     */
    public void createCategories(List<Category> list, int printableID) {
        for (Category category : list) {
            if (category.getCategoryId() != 0)
                create(printableID, category.getCategoryId());
        }
    }

    /**
     * @param printableID int
     * @param categoryID  int
     * @return void
     */
    public void create(int printableID, int categoryID) {
        PrintableCategoryImpl category = new PrintableCategoryImpl();
        category.setPrintableId(printableID);
        category.setCategoryId(categoryID);
        this.getDao().persist(category);
    }

    /**
     * @param printableID int
     * @return void
     */
    public void deleteCategoriesForPrintable(int printableID) {
        List<PrintableCategory> printableCategories = getListByID(printableID);
        if (printableCategories != null) {
            for (PrintableCategory printableCategory : printableCategories) {
                delete(printableCategory);
            }
        }
    }

    /**
     * @param printableCategory PrintableCategory
     * @return void
     */
    private void delete(PrintableCategory printableCategory) {
        this.getDao().remove(printableCategory);
    }
}
