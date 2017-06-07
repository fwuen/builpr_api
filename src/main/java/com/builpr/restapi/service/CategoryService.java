package com.builpr.restapi.service;
import com.builpr.database.db.builpr.category.Category;
import com.builpr.database.db.builpr.category.CategoryManager;
import com.builpr.database.db.builpr.printable.Printable;
import com.builpr.database.db.builpr.printable.PrintableManager;
import com.builpr.database.db.builpr.printable_category.PrintableCategory;
import com.builpr.database.db.builpr.printable_category.PrintableCategoryManager;
import com.builpr.database.db.builpr.user.User;
import com.builpr.database.db.builpr.user.UserManager;
import com.builpr.restapi.utils.Connector;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * printableCategory service
 */
public class CategoryService {

    private PrintableCategoryManager printableCategoryManager;

    private CategoryManager categoryManager;

    private PrintableManager printableManager;

    private UserManager userManager;

    public CategoryService() {
        printableCategoryManager = Connector.getConnection().getOrThrow(PrintableCategoryManager.class);

        categoryManager = Connector.getConnection().getOrThrow(CategoryManager.class);

        printableManager = Connector.getConnection().getOrThrow(PrintableManager.class);
    }

    public Map<Printable, List<Category>> getCategoriesForPrintable(int printableID) {
        return printableCategoryManager.stream().collect(
                groupingBy(printableManager.finderBy(PrintableCategory.PRINTABLE_ID),
                        mapping(categoryManager.finderBy(PrintableCategory.CATEGORY_ID),
                                toList()
                        )
                )
        ).entrySet()
                .stream()
                .filter(e -> e.getKey().getPrintableId() == printableID)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Printable, List<Category>> getCategoriesForUser(int userID) {
        return printableCategoryManager.stream().collect(
                groupingBy(printableManager.finderBy(PrintableCategory.PRINTABLE_ID),
                        mapping(categoryManager.finderBy(PrintableCategory.CATEGORY_ID),
                                toList()
                        )
                )
        ).entrySet()
                .stream()
                .filter(e -> e.getKey().getUploaderId() == userID)
                .collect(toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
