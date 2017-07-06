package com.builpr.restapi.converter;

import com.builpr.Constants;
import com.builpr.database.bridge.BuilprApplicationBuilder;
import com.builpr.database.bridge.category.Category;
import com.builpr.database.bridge.category.CategoryManager;
import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.printable.PrintableImpl;
import com.builpr.database.bridge.rating.Rating;
import com.builpr.database.bridge.rating.RatingImpl;
import com.builpr.database.bridge.user.User;
import com.builpr.database.bridge.user.UserImpl;
import com.builpr.database.service.*;
import com.builpr.restapi.utils.PrintableCategoryHelper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Markus Goller
 *
 * Tests for PrintableToSolrPrintableConverter
 */
public class PrintableToSolrPrintableConverterTest {
    private DatabasePrintableManager databasePrintableManager;
    private DatabaseUserManager databaseUserManager;
    private DatabaseRatingManager databaseRatingManager;
    private DatabaseCategoryManager databaseCategoryManager;
    private PrintableCategoryHelper helper;
    private CategoryManager categoryManager;

    private static final String TEST_USER = "Solrconverter";
    private static final String TEST_USER2 = "Solrconverter2";

    private static final int TEST_ID = 784512;

    private static final String TEST_DESCRIPTION = "testdescription";
    private static final String TEST_TITLE = "testtitle";

    private List<String> category_names = new ArrayList<>();

    public PrintableToSolrPrintableConverterTest() {
        databasePrintableManager = new DatabasePrintableManager();
        databaseUserManager = new DatabaseUserManager();
        databaseRatingManager = new DatabaseRatingManager();
        databaseCategoryManager = new DatabaseCategoryManager();
        helper = new PrintableCategoryHelper();

        categoryManager = new BuilprApplicationBuilder().withPassword(Constants.DATABASE_PASSWORD).build().getOrThrow(CategoryManager.class);
    }

    @Before
    public void setTestUp() {
        User user = new UserImpl()
                .setUsername(TEST_USER)
                .setEmail("solrprintable@googlemail.com")
                .setPassword(new BCryptPasswordEncoder().encode("password"))
                .setBirthday(new Date(System.currentTimeMillis()))
                .setFirstname("rating")
                .setLastname("testuser")
                .setShowBirthday(false)
                .setShowName(false)
                .setShowEmail(false);

        if (databaseUserManager.getByUsername(TEST_USER) != null) {
            databaseUserManager.deleteByUsername(TEST_USER);
        }
        databaseUserManager.persist(user);

        User testUser2 = new UserImpl()
                .setUsername(TEST_USER2)
                .setEmail("solrprintable297@googlemail.com")
                .setPassword(new BCryptPasswordEncoder().encode("password"))
                .setBirthday(new Date(System.currentTimeMillis()))
                .setFirstname("rating2")
                .setLastname("testuser2")
                .setShowBirthday(false)
                .setShowName(false)
                .setShowEmail(false);

        if (databaseUserManager.getByUsername(TEST_USER2) != null) {
            databaseUserManager.deleteByUsername(TEST_USER2);
        }
        databaseUserManager.persist(testUser2);

        if (databasePrintableManager.isPresent(TEST_ID)) {
            databasePrintableManager.deletePrintable(TEST_ID);
        }

        Printable testPrintable = new PrintableImpl()
                .setPrintableId(TEST_ID)
                .setDescription(TEST_DESCRIPTION)
                .setTitle(TEST_TITLE)
                .setUploaderId(databaseUserManager.getByUsername(TEST_USER).getUserId())
                .setNumDownloads(1)
                .setFilePath("C:\\solr\\printable\\existing.stl");

        databasePrintableManager.persist(testPrintable);

        Rating rating = new RatingImpl()
                .setRating(5)
                .setPrintableId(TEST_ID)
                .setUserId(databaseUserManager.getByUsername(TEST_USER).getUserId())
                .setMsg("newrating");

        databaseRatingManager.persist(rating);


        category_names.add("solrtestcategory");
        databaseCategoryManager.update(category_names);
        List<Category> categoryList = categoryManager.stream().filter(Category.CATEGORY_NAME.equal("solrtestcategory")).collect(Collectors.toList());
        helper.createCategories(categoryList, TEST_ID);
    }

    @After
    public void clearDatabase() {
        if (databaseUserManager.getByUsername(TEST_USER) != null) {
            databaseUserManager.deleteByUsername(TEST_USER);
        }
        if (databaseUserManager.getByUsername(TEST_USER2) != null) {
            databaseUserManager.deleteByUsername(TEST_USER2);
        }
        if (categoryManager.stream().filter(Category.CATEGORY_NAME.equal("solrtestcategory")) != null) {
            categoryManager.stream().filter(Category.CATEGORY_NAME.equal("solrtestcategory")).forEach(categoryManager.remover());
        }
    }

    @Test
    public void testGetSolrPrintable() {
        Printable db_printable = databasePrintableManager.getPrintableById(TEST_ID);
        com.builpr.search.model.Printable printable = PrintableToSolrPrintableConverter.getSolrPrintable(db_printable);
        Assert.assertNotNull(printable);
        Assert.assertTrue(Objects.equals(printable.getDescription(), TEST_DESCRIPTION));
        Assert.assertTrue(Objects.equals(printable.getTitle(), TEST_TITLE));
        Assert.assertTrue(printable.getId() == TEST_ID);
        Assert.assertTrue(printable.getNumberOfDownloads() == db_printable.getNumDownloads());
        Assert.assertTrue(printable.getUploaderId() == db_printable.getUploaderId());
        Assert.assertTrue(printable.getUploadDate().getTime() == new Date(db_printable.getUploadTime().getTime()).getTime());
        Assert.assertTrue(printable.getRating() == 5);
        Assert.assertTrue(printable.getCategories().size() == category_names.size());
    }
}
