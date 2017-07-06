package com.builpr.restapi.utils;

import com.builpr.Constants;
import com.builpr.database.bridge.BuilprApplicationBuilder;
import com.builpr.database.bridge.category.Category;
import com.builpr.database.bridge.category.CategoryManager;
import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.printable.PrintableImpl;
import com.builpr.database.bridge.printable_category.PrintableCategory;
import com.builpr.database.bridge.printable_category.PrintableCategoryManager;
import com.builpr.database.bridge.user.User;
import com.builpr.database.bridge.user.UserImpl;
import com.builpr.database.service.DatabaseCategoryManager;
import com.builpr.database.service.DatabasePrintableManager;
import com.builpr.database.service.DatabaseUserManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Markus Goller
 *
 * Tests for the PrintableCategoryHelper
 */
public class PrintableCategoryHelperTest {
    private DatabaseUserManager databaseUserManager;
    private DatabasePrintableManager databasePrintableManager;
    private DatabaseCategoryManager databaseCategoryManager;
    private CategoryManager categoryManager;
    private PrintableCategoryManager printableCategoryManager;
    private PrintableCategoryHelper printableCategoryHelper;

    private static final int TEST_ID = 78978978;
    private static final String TEST_USER = "printcathelper";
    private static List<Category> CATEGORY_LIST = new ArrayList<>();

    public PrintableCategoryHelperTest() {
        databaseUserManager = new DatabaseUserManager();
        databasePrintableManager = new DatabasePrintableManager();
        databaseCategoryManager = new DatabaseCategoryManager();
        categoryManager = new BuilprApplicationBuilder().withPassword(Constants.DATABASE_PASSWORD).build().getOrThrow(CategoryManager.class);
        printableCategoryManager= new BuilprApplicationBuilder().withPassword(Constants.DATABASE_PASSWORD).build().getOrThrow(PrintableCategoryManager.class);

        printableCategoryHelper = new PrintableCategoryHelper();
    }

    @Before
    public void setTestUp() {
        if (databaseUserManager.getByUsername(TEST_USER) != null) {
            databaseUserManager.deleteByUsername(TEST_USER);
        }

        User testUser = new UserImpl()
                .setUsername(TEST_USER)
                .setEmail("markus.goller97@googlemail.com")
                .setPassword(new BCryptPasswordEncoder().encode("password"))
                .setBirthday(new Date(System.currentTimeMillis()))
                .setFirstname("printableCategory")
                .setLastname("testuser")
                .setShowBirthday(false)
                .setShowName(false)
                .setShowEmail(false);

        databaseUserManager.persist(testUser);

        Printable printable = databasePrintableManager.getPrintableById(TEST_ID);
        if (printable != null) {
            databasePrintableManager.deletePrintable(TEST_ID);
        }

        Printable testPrintable = new PrintableImpl()
                .setPrintableId(TEST_ID)
                .setDescription("category description")
                .setTitle("category title")
                .setUploaderId(databaseUserManager.getByUsername(TEST_USER).getUserId())
                .setNumDownloads(1)
                .setFilePath("C:\\test\\not\\existing.stl");

        databasePrintableManager.persist(testPrintable);

        List<String> cat_name = new ArrayList<>();
        cat_name.add("printcathelper1");
        cat_name.add("printcathelper2");
        databaseCategoryManager.update(cat_name);


        Optional<Category> category1 = categoryManager.stream().filter(Category.CATEGORY_NAME.equal("printcathelper1")).findAny();
        Optional<Category> category2 = categoryManager.stream().filter(Category.CATEGORY_NAME.equal("printcathelper2")).findAny();
        category1.ifPresent(CATEGORY_LIST::add);
        category2.ifPresent(CATEGORY_LIST::add);
    }

    @After
    public void clearDatabase() {
        if (databaseUserManager.getByUsername(TEST_USER) != null) {
            databaseUserManager.deleteByUsername(TEST_USER);
        }
        if (categoryManager.stream().filter(Category.CATEGORY_NAME.equal("printcathelper1")) != null) {
            categoryManager.stream().filter(Category.CATEGORY_NAME.equal("printcathelper1")).forEach(categoryManager.remover());
        }
        if (categoryManager.stream().filter(Category.CATEGORY_NAME.equal("printcathelper2")) != null) {
            categoryManager.stream().filter(Category.CATEGORY_NAME.equal("printcathelper2")).forEach(categoryManager.remover());
        }
    }

    @Test
    public void testCreateCategories() {
        printableCategoryHelper.createCategories(CATEGORY_LIST, TEST_ID);
        List<PrintableCategory> list = printableCategoryManager.stream().filter(PrintableCategory.PRINTABLE_ID.equal(TEST_ID)).collect(Collectors.toList());
        Assert.assertTrue(list.size() == 2);
    }
}
