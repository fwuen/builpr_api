package com.builpr.restapi.converter;

import com.builpr.Constants;
import com.builpr.database.bridge.BuilprApplicationBuilder;
import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.printable.PrintableImpl;
import com.builpr.database.bridge.rating.Rating;
import com.builpr.database.bridge.rating.RatingImpl;
import com.builpr.database.bridge.rating.RatingManager;
import com.builpr.database.bridge.user.User;
import com.builpr.database.bridge.user.UserImpl;
import com.builpr.database.service.DatabasePrintableManager;
import com.builpr.database.service.DatabaseRatingManager;
import com.builpr.database.service.DatabaseUserManager;
import com.builpr.restapi.model.Response.rating.RatingPayload;
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
 * Tests for RatingModelToRatingPayloadConverterTest
 */
public class RatingModelToRatingPayloadConverterTest {
    private DatabaseUserManager databaseUserManager;
    private DatabasePrintableManager databasePrintableManager;
    private DatabaseRatingManager databaseRatingManager;
    private RatingManager ratingManager;

    private static final String TEST_USER = "ratinghelper";
    private static final String TEST_USER2 = "ratinghelper1";
    private static final int TEST_ID = 7845123;
    private static final int TEST_ID2 = 123456;
    private static final String TEST_TITLE = "test title";
    private static final String TEST_DESCRIPTION = "test description";

    private Rating testRating;

    public RatingModelToRatingPayloadConverterTest() {
        databaseUserManager = new DatabaseUserManager();
        databasePrintableManager = new DatabasePrintableManager();
        databaseRatingManager = new DatabaseRatingManager();
        ratingManager = new BuilprApplicationBuilder().withPassword(Constants.DATABASE_PASSWORD).build().getOrThrow(RatingManager.class);
    }

    @Before
    public void setTestUp() {
        User testUser = new UserImpl()
                .setUsername(TEST_USER)
                .setEmail("ratinghelper97@googlemail.com")
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
                .setEmail("ratinghelper297@googlemail.com")
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
        Rating rating = new RatingImpl()
                .setRating(5)
                .setMsg("ratingtestmsg")
                .setPrintableId(TEST_ID)
                .setUserId(ratingUser.getUserId());

        ratingManager.persist(rating);
        Optional<Rating> rating1 = ratingManager.stream()
                .filter(Rating.USER_ID.equal(ratingUser.getUserId())
                        .and(Rating.PRINTABLE_ID.equal(TEST_ID)))
                .findAny();
        rating1.ifPresent(rating2 -> testRating = rating2);
    }

    @After
    public void clearDatabase() {
        if (databaseUserManager.getByUsername(TEST_USER) != null) {
            databaseUserManager.deleteByUsername(TEST_USER);
        }

        if (databaseUserManager.getByUsername(TEST_USER2) != null) {
            databaseUserManager.deleteByUsername(TEST_USER2);
        }
    }

    @Test
    public void testFrom() {
        RatingPayload payload = RatingModelToRatingPayloadConverter.from(testRating);
        Assert.assertNotNull(payload);
        Assert.assertTrue(payload.getOwnerID() == testRating.getUserId());
        Assert.assertTrue(payload.getRating() == testRating.getRating());
        Assert.assertTrue(Objects.equals(payload.getText(), testRating.getMsg().get()));
        Assert.assertTrue(Objects.equals(payload.getTime(), testRating.getRatingTime().toString()));
    }

    @Test
    public void testFromList() {
        List<Rating> ratings = new ArrayList<>();
        ratings.add(testRating);

        List<RatingPayload> list = RatingModelToRatingPayloadConverter.from(ratings);

        Assert.assertFalse(list.isEmpty());
        Assert.assertTrue(list.size() == 1);
        Assert.assertTrue(list.get(0).getOwnerID() == testRating.getUserId());
        Assert.assertTrue(list.get(0).getRating() == testRating.getRating());
        Assert.assertTrue(Objects.equals(list.get(0).getText(), testRating.getMsg().get()));
        Assert.assertTrue(Objects.equals(list.get(0).getTime(), testRating.getRatingTime().toString()));
    }
}

