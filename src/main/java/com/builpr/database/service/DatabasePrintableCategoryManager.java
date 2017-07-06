package com.builpr.database.service;

import com.builpr.database.bridge.printable_category.PrintableCategory;
import com.builpr.database.bridge.printable_category.PrintableCategoryManager;


import java.util.List;
import java.util.stream.Collectors;


/**
 *
 * @author Markus Goller
 * printableCategory manager
 */
public class DatabasePrintableCategoryManager extends DatabaseManager<PrintableCategoryManager> {

    public DatabasePrintableCategoryManager() {
        super(PrintableCategoryManager.class);
    }

    /**
     * Returns a list of objects referring to a printable
     *
     * @param printableID int
     * @return List<PrintableCategory>
     */
    public List<PrintableCategory> getListByID(int printableID) {
        return getDao().stream().filter(PrintableCategory.PRINTABLE_ID.equal(printableID)).collect(Collectors.toList());
    }

    /**
     * Persistently save a PrintableCategory in the database
     *
     * @param printableCategory PrintableCategory
     */
    public void persist(PrintableCategory printableCategory) {
        getDao().persist(printableCategory);
    }

    /**
     * Delete a PrintableCategory from database
     *
     * @param printableCategory PrintableCategory
     * @return void
     */
    public void delete(PrintableCategory printableCategory) {
        getDao().remove(printableCategory);
    }
}
