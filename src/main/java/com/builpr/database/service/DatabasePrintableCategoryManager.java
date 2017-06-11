package com.builpr.database.service;

import com.builpr.database.bridge.category.Category;
import com.builpr.database.bridge.printable_category.PrintableCategory;
import com.builpr.database.bridge.printable_category.PrintableCategoryImpl;
import com.builpr.database.bridge.printable_category.PrintableCategoryManager;
import com.builpr.restapi.model.Request.Printable.PrintableEditRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * printableCategory manager
 */
public class DatabasePrintableCategoryManager extends DatabaseManager<PrintableCategoryManager> {
private DatabaseCategoryManager databaseCategoryManager = new DatabaseCategoryManager();
    public DatabasePrintableCategoryManager() {
        super(PrintableCategoryManager.class);
    }

    /**
     * @param request PrintableEditRequest
     * @return void
     */
    public void update(PrintableEditRequest request) {
        // neue Kategorien (namen)
        List<String> newCategories = request.getCategories();
        // Kategorien (id, name)
        List<Category> categories = new ArrayList<>();
        // Umwandeln von Namen der Kategorien auf deren ID's
        for (String categoryName : newCategories) {
            List<Category> foundCategory = databaseCategoryManager.getDao().stream().filter(Category.CATEGORY_NAME.equal(categoryName)).collect(Collectors.toList());
            categories.add(foundCategory.get(0));
        }
        // bestehende löschen
        this.getDao().stream().filter(PrintableCategory.PRINTABLE_ID.equal(request.getPrintableID())).forEach(this.getDao().remover());
        // neue Kategorien erstellen
        for (Category category : categories) {
            create(request.getPrintableID(), category.getCategoryId());
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
}
