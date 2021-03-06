package com.builpr.database.service;

import com.builpr.Constants;
import com.builpr.database.bridge.BuilprApplicationBuilder;
import com.builpr.database.bridge.category.Category;
import com.builpr.database.bridge.category.CategoryImpl;
import com.builpr.database.bridge.category.CategoryManager;
import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.printable.PrintableImpl;
import com.builpr.database.bridge.printable_category.PrintableCategory;
import com.builpr.database.bridge.printable_category.PrintableCategoryImpl;
import com.builpr.database.bridge.user.User;
import com.builpr.database.bridge.user.UserImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Markus Goller
 *
 * Tests for the DatabasePrintableCategoryManager
 */
public class DatabasePrintableCategoryManagerTest {
    private DatabaseUserManager databaseUserManager;
    private DatabasePrintableManager databasePrintableManager;
    private DatabasePrintableCategoryManager databasePrintableCategoryManager;
    private CategoryManager categoryManager;

    private static final String TEST_USER = "PrintCatUser";
    private static final int TEST_ID = 696969696;
    private static final String TEST_CATEGORY = "printcategorytest1";
    private static final String TEST_CATEGORY1 = "printcategorytest2";
    private static final String TEST_CATEGORY2 = "printcategorytest3";
    private static final String TEST_CATEGORY3 = "printcategorytest4";
    private List<PrintableCategory> printableCategories = null;


    public DatabasePrintableCategoryManagerTest() {
        databaseUserManager = new DatabaseUserManager();
        databasePrintableManager = new DatabasePrintableManager();
        databasePrintableCategoryManager = new DatabasePrintableCategoryManager();
        categoryManager = new BuilprApplicationBuilder().withPassword(Constants.DATABASE_PASSWORD).build().getOrThrow(CategoryManager.class);
        printableCategories = new ArrayList<>();

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

        Optional<Category> list = categoryManager.stream().filter(Category.CATEGORY_ID.equal(1000)).findAny();
        list.ifPresent(categoryManager::remove);

        Category category = new CategoryImpl()
                .setCategoryId(1000)
                .setCategoryName(TEST_CATEGORY);
        categoryManager.persist(category);

        list = categoryManager.stream().filter(Category.CATEGORY_ID.equal(1001)).findAny();
        list.ifPresent(categoryManager::remove);

        Category category1 = new CategoryImpl()
                .setCategoryId(1001)
                .setCategoryName(TEST_CATEGORY1);
        categoryManager.persist(category1);

        list = categoryManager.stream().filter(Category.CATEGORY_ID.equal(1002)).findAny();
        list.ifPresent(categoryManager::remove);

        Category category2 = new CategoryImpl()
                .setCategoryId(1002)
                .setCategoryName(TEST_CATEGORY2);
        categoryManager.persist(category2);


        PrintableCategory printableCategory = new PrintableCategoryImpl()
                .setCategoryId(1000)
                .setPrintableId(TEST_ID);
        databasePrintableCategoryManager.persist(printableCategory);

        PrintableCategory printableCategory1 = new PrintableCategoryImpl()
                .setCategoryId(1001)
                .setPrintableId(TEST_ID);
        databasePrintableCategoryManager.persist(printableCategory1);

        PrintableCategory printableCategory2 = new PrintableCategoryImpl()
                .setCategoryId(1002)
                .setPrintableId(TEST_ID);
        databasePrintableCategoryManager.persist(printableCategory2);

        printableCategories.add(printableCategory);
        printableCategories.add(printableCategory1);
        printableCategories.add(printableCategory2);

        list = categoryManager.stream().filter(Category.CATEGORY_ID.equal(1003)).findAny();
        list.ifPresent(categoryManager::remove);

        Category category3 = new CategoryImpl()
                .setCategoryId(1003)
                .setCategoryName(TEST_CATEGORY3);
        categoryManager.persist(category3);

    }

    @After
    public void clearDatabase() {
        if (categoryManager.stream().filter(Category.CATEGORY_ID.equal(1000)) != null) {
            categoryManager.stream().filter(Category.CATEGORY_ID.equal(1000)).forEach(categoryManager.remover());
        }
        if (categoryManager.stream().filter(Category.CATEGORY_ID.equal(1001)) != null) {
            categoryManager.stream().filter(Category.CATEGORY_ID.equal(1001)).forEach(categoryManager.remover());
        }
        if (categoryManager.stream().filter(Category.CATEGORY_ID.equal(1002)) != null) {
            categoryManager.stream().filter(Category.CATEGORY_ID.equal(1002)).forEach(categoryManager.remover());
        }
        if (categoryManager.stream().filter(Category.CATEGORY_NAME.equal("printcategorytest4")) != null) {
            categoryManager.stream().filter(Category.CATEGORY_NAME.equal("printcategorytest4")).forEach(categoryManager.remover());
        }
        if (categoryManager.stream().filter(Category.CATEGORY_NAME.equal("printcategorytest5")) != null) {
            categoryManager.stream().filter(Category.CATEGORY_NAME.equal("printcategorytest5")).forEach(categoryManager.remover());
        }
        if (categoryManager.stream().filter(Category.CATEGORY_NAME.equal("printcategorytest6")) != null) {
            categoryManager.stream().filter(Category.CATEGORY_NAME.equal("printcategorytest6")).forEach(categoryManager.remover());
        }

        if (databaseUserManager.getByUsername(TEST_USER) != null) {
            databaseUserManager.deleteByUsername(TEST_USER);
        }
    }

    @Test
    public void testGetListByID() {
        List<PrintableCategory> list = databasePrintableCategoryManager.getListByID(TEST_ID);
        Assert.assertTrue(list.size() == 3);
    }

    @Test
    public void testPersist() {
        PrintableCategory printableCategory = new PrintableCategoryImpl()
                .setCategoryId(1003)
                .setPrintableId(TEST_ID);
        databasePrintableCategoryManager.persist(printableCategory);
        List<PrintableCategory> list = databasePrintableCategoryManager.getListByID(TEST_ID);
        Assert.assertTrue(list.size() == 4);
    }
}
