package com.builpr.database.service;

import com.builpr.database.bridge.printable_category.PrintableCategory;
import com.builpr.database.bridge.printable_category.PrintableCategoryManager;


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


    public void persist(PrintableCategory printableCategory) {
        getDao().persist(printableCategory);
    }

    /**
     * @param printableCategory PrintableCategory
     * @return void
     */
    public void delete(PrintableCategory printableCategory) {
        getDao().remove(printableCategory);
    }
}
