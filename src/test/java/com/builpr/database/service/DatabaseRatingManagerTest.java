package com.builpr.database.service;

import com.builpr.Constants;
import com.builpr.database.bridge.BuilprApplicationBuilder;
import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.printable.PrintableImpl;
import com.builpr.database.bridge.rating.Rating;
import com.builpr.database.bridge.rating.RatingImpl;
import com.builpr.database.bridge.rating.RatingManager;
import com.builpr.database.bridge.user.User;
import com.builpr.database.bridge.user.UserImpl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Tests for DatabaseRatingManager
 */
public class DatabaseRatingManagerTest {

    private DatabaseUserManager databaseUserManager;
    private DatabasePrintableManager databasePrintableManager;
    private DatabaseRatingManager databaseRatingManager;
    private RatingManager ratingManager;

    private static final String TEST_USER = "testrating";
    private static final String TEST_USER2 = "testrating2";
    private static final int TEST_ID = 8888888;
    private static final int TEST_ID2 = 9999999;
    private static final String TEST_TITLE = "test title";
    private static final String TEST_DESCRIPTION = "test description";

    private Rating testRating;

    public DatabaseRatingManagerTest() {
        databaseUserManager = new DatabaseUserManager();
        databasePrintableManager = new DatabasePrintableManager();
        databaseRatingManager = new DatabaseRatingManager();
        ratingManager = new BuilprApplicationBuilder().withPassword(Constants.DATABASE_PASSWORD).build().getOrThrow(RatingManager.class);
    }

    @Before
    public void setTestUp() {
        User testUser = new UserImpl()
                .setUsername(TEST_USER)
                .setEmail("markus.goller97@googlemail.com")
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
                .setEmail("markus2.goller97@googlemail.com")
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
    public void testIsPresent() {
        Assert.assertFalse(databaseRatingManager.isPresent(0));
        Assert.assertTrue(databaseRatingManager.isPresent(testRating.getRatingId()));
    }

    @Test
    public void testGetRatingsForPrintable() {
        List<Rating> list = databaseRatingManager.getRatingsForPrintable(TEST_ID);
        Assert.assertFalse(list.isEmpty());
        Assert.assertTrue(list.size() == 1);
        Assert.assertTrue(list.get(0).getRating() == 5);
        Assert.assertTrue(list.get(0).getPrintableId() == TEST_ID);
        Assert.assertTrue(list.get(0).getUserId() == databaseUserManager.getByUsername(TEST_USER2).getUserId());
        Assert.assertTrue(Objects.equals(list.get(0).getMsg().get(), "ratingtestmsg"));
    }

    @Test
    public void testPersist() {
        Optional<Rating> list = ratingManager.stream()
                .filter(Rating.USER_ID.equal(databaseUserManager.getByUsername(TEST_USER).getUserId())
                        .and(Rating.PRINTABLE_ID.equal(TEST_ID2)))
                .findAny();
        Assert.assertFalse(list.isPresent());
        Rating rating = new RatingImpl()
                .setRating(5)
                .setUserId(databaseUserManager.getByUsername(TEST_USER).getUserId())
                .setPrintableId(TEST_ID2);
        databaseRatingManager.persist(rating);
        Optional<Rating> list2 = ratingManager.stream()
                .filter(Rating.USER_ID.equal(databaseUserManager.getByUsername(TEST_USER).getUserId())
                        .and(Rating.PRINTABLE_ID.equal(TEST_ID2)))
                .findAny();
        Assert.assertTrue(list2.isPresent());
    }

    @Test

    public void testGetRatingByIds() {
        Assert.assertNotNull(databaseRatingManager.getRatingByIds(TEST_ID, databaseUserManager.getByUsername(TEST_USER2).getUserId()));
        Assert.assertNull(databaseRatingManager.getRatingByIds(0, 0));
    }

    @Test
    public void testGetRatingByRatingID() {
        Optional<Rating> list = ratingManager.stream()
                .filter(Rating.USER_ID.equal(databaseUserManager.getByUsername(TEST_USER2).getUserId())
                        .and(Rating.PRINTABLE_ID.equal(TEST_ID)))
                .findAny();
        Assert.assertNotNull(databaseRatingManager.getRatingByRatingID(list.get().getRatingId()));

        Assert.assertNull(databaseRatingManager.getRatingByRatingID(0));
    }

    @Test
    public void testDeleteRatingByID() {
        Optional<Rating> list = ratingManager.stream()
                .filter(Rating.USER_ID.equal(databaseUserManager.getByUsername(TEST_USER2).getUserId())
                        .and(Rating.PRINTABLE_ID.equal(TEST_ID)))
                .findAny();
        Assert.assertTrue(list.isPresent());

        databaseRatingManager.deleteRatingByID(list.get().getRatingId());

        Optional<Rating> list2 = ratingManager.stream()
                .filter(Rating.USER_ID.equal(databaseUserManager.getByUsername(TEST_USER2).getUserId())
                        .and(Rating.PRINTABLE_ID.equal(TEST_ID)))
                .findAny();
        Assert.assertFalse(list2.isPresent());

    }
}
