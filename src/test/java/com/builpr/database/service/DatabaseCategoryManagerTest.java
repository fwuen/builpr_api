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
import java.util.stream.Collectors;

/**
 * Tests for DatabaseCategoryManager
 */
public class DatabaseCategoryManagerTest {
    private DatabaseUserManager databaseUserManager;
    private DatabasePrintableManager databasePrintableManager;
    private DatabaseCategoryManager databaseCategoryManager;
    private DatabasePrintableCategoryManager databasePrintableCategoryManager;
    private CategoryManager categoryManager;
    private static final String TEST_USER = "category_user";
    private static final int TEST_ID = 77777777;
    private static final String TEST_CATEGORY = "categorytest1";
    private static final String TEST_CATEGORY1 = "categorytest2";
    private static final String TEST_CATEGORY2 = "categorytest3";

    private Printable testPrintable;
    private List<PrintableCategory> printableCategories = new ArrayList<>();


    public DatabaseCategoryManagerTest() {
        databaseUserManager = new DatabaseUserManager();
        databasePrintableManager = new DatabasePrintableManager();
        databaseCategoryManager = new DatabaseCategoryManager();
        databasePrintableCategoryManager = new DatabasePrintableCategoryManager();
        categoryManager = new BuilprApplicationBuilder().withPassword(Constants.DATABASE_PASSWORD).build().getOrThrow(CategoryManager.class);
    }

    @Before
    public void setTestUp() {
        User testUser = new UserImpl()
                .setUsername(TEST_USER)
                .setEmail("markus.goller97@googlemail.com")
                .setPassword(new BCryptPasswordEncoder().encode("password"))
                .setBirthday(new Date(System.currentTimeMillis()))
                .setFirstname("category")
                .setLastname("user")
                .setShowBirthday(false)
                .setShowName(false)
                .setShowEmail(false);

        if (databaseUserManager.getByUsername(TEST_USER) != null) {
            databaseUserManager.deleteByUsername(TEST_USER);
        }
        databaseUserManager.persist(testUser);

        Printable printable = databasePrintableManager.getPrintableById(TEST_ID);
        if (printable != null) {
            databasePrintableManager.deletePrintable(TEST_ID);
        }

        testPrintable = new PrintableImpl()
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
        if (databaseUserManager.getByUsername(TEST_USER) != null) {
            databaseUserManager.deleteByUsername(TEST_USER);
        }
        if (categoryManager.stream().filter(Category.CATEGORY_NAME.equal("updatedCategory")) != null) {
            categoryManager.stream().filter(Category.CATEGORY_NAME.equal("updatedCategory")).forEach(categoryManager.remover());
        }
        if (categoryManager.stream().filter(Category.CATEGORY_NAME.equal("newcreatedcategory")) != null) {
            categoryManager.stream().filter(Category.CATEGORY_NAME.equal("newcreatedcategory")).forEach(categoryManager.remover());
        }
    }

    @Test
    public void testGetCategoriesForPrintable() {
        List<Category> categoryList = databaseCategoryManager.getCategoriesForPrintable(TEST_ID);
        Assert.assertTrue(categoryList.size() == 3);
    }

    @Test
    public void testGetCategoriesByPrintableCategoryList() {
        List<Category> categoryList = databaseCategoryManager.getCategoriesByPrintableCategoryList(printableCategories);
        Assert.assertTrue(categoryList.size() == printableCategories.size());
    }

    @Test
    public void testGetCategoriesByList() {
        List<String> list = new ArrayList<>();
        list.add(TEST_CATEGORY);
        list.add(TEST_CATEGORY1);
        list.add(TEST_CATEGORY2);
        List<Category> categoryList = databaseCategoryManager.getCategoriesByList(list);

        Assert.assertTrue(categoryList.size() == 3);
    }

    @Test
    public void testUpdate() {
        List<Category> categoryList1 = categoryManager.stream().collect(Collectors.toList());

        List<String> list = new ArrayList<>();
        list.add(TEST_CATEGORY);
        list.add(TEST_CATEGORY1);
        list.add(TEST_CATEGORY2);
        list.add("updatedCategory");

        databaseCategoryManager.update(list);

        List<Category> categoryList2 = categoryManager.stream().collect(Collectors.toList());

        Assert.assertTrue((categoryList1.size() + 1) == categoryList2.size());
    }

    @Test
    public void testCreate() {
        Optional<Category> category = categoryManager.stream().filter(Category.CATEGORY_NAME.equal("newcreatedcategory")).findAny();
        category.ifPresent(categoryManager::remove);

        List<Category> categoryList1 = categoryManager.stream().collect(Collectors.toList());
        databaseCategoryManager.create("newcreatedcategory");
        List<Category> categoryList2 = categoryManager.stream().collect(Collectors.toList());

        Assert.assertTrue(categoryList1.size() + 1 == categoryList2.size());
    }
}
