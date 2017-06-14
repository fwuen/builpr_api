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
     * @param request PrintableEditRequest
     * @return void
     */
    public void update(PrintableEditRequest request, List<String> categoryList) {
        // Kategorien (id, name)
        DatabaseCategoryManager databaseCategoryManager = new DatabaseCategoryManager();
        List<Category> categories = databaseCategoryManager.getCategoriesByList(categoryList);
        // bestehende löschen
        List<PrintableCategory> printableCategories = getListByID(request.getPrintableID());
        boolean success = false;
        if (printableCategories != null) {
            for (PrintableCategory printableCategory : printableCategories) {
                delete(printableCategory);
            }
        }
        if (categories != null && success) {
            // neue Kategorien erstellen
            createCategories(categories, request.getPrintableID());
        }
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

    public void deleteCategoriesForPrintable(int printableID) {
        this.getDao().stream().filter(PrintableCategory.PRINTABLE_ID.equal(printableID)).forEach(this.getDao().remover());
    }

    private void delete(PrintableCategory printableCategory) {
        getDao().remove(printableCategory);
    }
}
