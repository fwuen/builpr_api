package com.builpr.restapi.converter;

import com.builpr.Constants;
import com.builpr.database.bridge.BuilprApplicationBuilder;
import com.builpr.database.bridge.category.Category;
import com.builpr.database.bridge.category.CategoryManager;
import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.printable.PrintableImpl;
import com.builpr.database.bridge.rating.Rating;
import com.builpr.database.bridge.rating.RatingImpl;
import com.builpr.database.bridge.rating.RatingManager;
import com.builpr.database.bridge.user.User;
import com.builpr.database.bridge.user.UserImpl;
import com.builpr.database.service.DatabaseCategoryManager;
import com.builpr.database.service.DatabasePrintableManager;
import com.builpr.database.service.DatabaseUserManager;
import com.builpr.restapi.model.Response.printable.PrintablePayload;
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
import java.util.Optional;

/**
 * Tests for PrintableToPrintablePayloadConverter
 */
public class PrintableToPrintablePayloadConverterTest {
    private DatabasePrintableManager databasePrintableManager;
    private DatabaseUserManager databaseUserManager;
    private DatabaseCategoryManager databaseCategoryManager;
    private PrintableCategoryHelper printableCategoryHelper;

    private CategoryManager categoryManager;
    private RatingManager ratingManager;

    private static final String TEST_USER = "printConverter";
    private static final String TEST_USER2 = "printConverter2";

    private static final int TEST_ID = 12121212;
    private static final int TEST_ID2 = 21212121;

    private static final String TEST_DESCRIPTION = "converter description";
    private static final String TEST_TITLE = "converter title";

    private Rating rating;

    public PrintableToPrintablePayloadConverterTest() {
        databasePrintableManager = new DatabasePrintableManager();
        databaseUserManager = new DatabaseUserManager();
        databaseCategoryManager = new DatabaseCategoryManager();
        printableCategoryHelper = new PrintableCategoryHelper();
        categoryManager = new BuilprApplicationBuilder().withPassword(Constants.DATABASE_PASSWORD).build().getOrThrow(CategoryManager.class);
        ratingManager = new BuilprApplicationBuilder().withPassword(Constants.DATABASE_PASSWORD).build().getOrThrow(RatingManager.class);
    }

    @Before
    public void setTestUp() {
        User testUser = new UserImpl()
                .setUsername(TEST_USER)
                .setEmail("markus.goller397@googlemail.com")
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
        databaseUserManager.persist(testUser);

        User testUser2 = new UserImpl()
                .setUsername(TEST_USER2)
                .setEmail("markus.goller497@googlemail.com")
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
                .setFilePath("C:\\test\\not\\existing.stl");

        databasePrintableManager.persist(testPrintable);

        if (databasePrintableManager.isPresent(TEST_ID2)) {
            databasePrintableManager.deletePrintable(TEST_ID2);
        }

        Printable testPrintable2 = new PrintableImpl()
                .setPrintableId(TEST_ID2)
                .setDescription(TEST_DESCRIPTION)
                .setTitle(TEST_TITLE)
                .setUploaderId(databaseUserManager.getByUsername(TEST_USER2).getUserId())
                .setNumDownloads(1)
                .setFilePath("C:\\test2\\not\\existing.stl");

        databasePrintableManager.persist(testPrintable2);


        User ratingUser = databaseUserManager.getByUsername(TEST_USER2);
        Optional<Rating> list = ratingManager.stream()
                .filter(Rating.USER_ID.equal(ratingUser.getUserId())
                        .and(Rating.PRINTABLE_ID.equal(TEST_ID)))
                .findAny();

        list.ifPresent(ratingManager::remove);
        rating = new RatingImpl()
                .setRating(5)
                .setMsg("ratingtestmsg")
                .setPrintableId(TEST_ID)
                .setUserId(ratingUser.getUserId());

        ratingManager.persist(rating);


        List<String> cat_name = new ArrayList<>();
        cat_name.add("printcathelper1");
        cat_name.add("printcathelper2");
        databaseCategoryManager.update(cat_name);

        List<Category> categoryList = new ArrayList<>();
        Optional<Category> category1 = categoryManager.stream().filter(Category.CATEGORY_NAME.equal("printcathelper1")).findAny();
        Optional<Category> category2 = categoryManager.stream().filter(Category.CATEGORY_NAME.equal("printcathelper2")).findAny();
        category1.ifPresent(categoryList::add);
        category2.ifPresent(categoryList::add);
        printableCategoryHelper.createCategories(categoryList, TEST_ID);

    }

    @After
    public void clearDatabase() {
        if (databaseUserManager.getByUsername(TEST_USER) != null) {
            databaseUserManager.deleteByUsername(TEST_USER);
        }

        if (databaseUserManager.getByUsername(TEST_USER2) != null) {
            databaseUserManager.deleteByUsername(TEST_USER2);
        }

        if (categoryManager.stream().filter(Category.CATEGORY_NAME.equal("printcathelper1")) != null) {
            categoryManager.stream().filter(Category.CATEGORY_NAME.equal("printcathelper1")).forEach(categoryManager.remover());
        }
        if (categoryManager.stream().filter(Category.CATEGORY_NAME.equal("printcathelper2")) != null) {
            categoryManager.stream().filter(Category.CATEGORY_NAME.equal("printcathelper2")).forEach(categoryManager.remover());
        }
    }

    @Test
    public void testFrom() {
        Printable printable = databasePrintableManager.getPrintableById(TEST_ID);
        PrintablePayload payload = PrintableModelToPrintablePayloadConverter.from(printable);

        Assert.assertTrue(Objects.equals(printable.getTitle(), payload.getTitle()));
        Assert.assertTrue(printable.getDescription().isPresent());
        Assert.assertTrue(rating.getMsg().isPresent());
        Assert.assertTrue(Objects.equals(printable.getDescription().get(), payload.getDescription()));
        Assert.assertTrue(printable.getNumDownloads() == payload.getDownloads());
        Assert.assertTrue(printable.getPrintableId() == payload.getPrintableID());
        Assert.assertTrue(printable.getUploaderId() == payload.getOwnerID());
        Assert.assertTrue(printable.getUploaderId() == payload.getOwnerID());
        Assert.assertTrue(payload.getRatings().size() == 1);
        Assert.assertTrue(payload.getRatings().get(0).getOwnerID() == rating.getUserId());
        Assert.assertTrue(Objects.equals(payload.getRatings().get(0).getText(), rating.getMsg().get()));
        Assert.assertTrue(payload.getRatings().get(0).getRating() == rating.getRating());
        Assert.assertTrue(Objects.equals(payload.getUploadTime(), printable.getUploadTime().toString()));
        Assert.assertTrue(payload.getCategories().size() == 2);

    }
}
