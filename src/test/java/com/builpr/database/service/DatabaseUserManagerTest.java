package com.builpr.database.service;

import com.builpr.Constants;
import com.builpr.database.bridge.BuilprApplicationBuilder;
import com.builpr.database.bridge.user.User;
import com.builpr.database.bridge.user.UserImpl;
import com.builpr.database.bridge.user.UserManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.util.Optional;

/**
 * tests the DatabaseUserManager
 */
public class DatabaseUserManagerTest {

    private static final String TEST_USERNAME = "DB_test_user";
    private static final String TEST_EMAIL = "dbTest@test.de";

    private DatabaseUserManager databaseUserManager;
    private UserManager userManager;

    private User testUser;

    public DatabaseUserManagerTest() {
        databaseUserManager = new DatabaseUserManager();
        userManager = new BuilprApplicationBuilder().withPassword(Constants.DATABASE_PASSWORD).build().getOrThrow(UserManager.class);
    }

    @Before
    public void setUp() {

        Optional<User> user = userManager.stream().filter(User.USERNAME.equal(TEST_USERNAME)).findAny();

        user.ifPresent(userManager::remove);

        testUser = new UserImpl()
                .setUsername(TEST_USERNAME)
                .setEmail(TEST_EMAIL)
                .setPassword(new BCryptPasswordEncoder().encode("password"))
                .setBirthday(new Date(System.currentTimeMillis()))
                .setFirstname("Anthony")
                .setLastname("Modeste")
                .setShowBirthday(false)
                .setShowName(false)
                .setShowEmail(false);

        userManager.persist(testUser);
    }

    @Test
    public void testGetByUsername() {
        User user = databaseUserManager.getByUsername(TEST_USERNAME);

        Assert.assertNotNull(user);
    }

    @Test
    public void testGetByUsernameNonExistent() {
        User user = databaseUserManager.getByUsername("Schwachsinn");

        Assert.assertNull(user);
    }

    @Test
    public void testGetByEmail() {
        User user = databaseUserManager.getByEmail(TEST_EMAIL);

        Assert.assertNotNull(user);
    }

    @Test
    public void testGetByMailNonExistent() {
        User user = databaseUserManager.getByEmail("Schwachsinn@schachsinn.de");

        Assert.assertNull(user);
    }

    @Test
    public void testGetByID() {
        User user = databaseUserManager.getByID(testUser.getUserId());

        Assert.assertNotNull(user);
    }

    @Test
    public void testGetByIDNonExistent() {
        User user = databaseUserManager.getByID(999999999);

        Assert.assertNull(user);
    }

    @Test
    public void testDeleteByUsername() {
        Assert.assertTrue(userManager.stream().anyMatch(User.USERNAME.equal(TEST_USERNAME)));

        databaseUserManager.deleteByUsername(TEST_USERNAME);

        Assert.assertFalse(userManager.stream().anyMatch(User.USERNAME.equal(TEST_USERNAME)));
    }

    @Test
    public void testIsPresent() {
        Assert.assertTrue(databaseUserManager.isPresent(TEST_USERNAME));

        Assert.assertFalse(databaseUserManager.isPresent("irgendwas"));
    }

    @Test
    public void persist() {

        Optional<User> user = userManager.stream().filter(User.USERNAME.equal(TEST_USERNAME)).findAny();

        user.ifPresent(userManager::remove);

        Optional<User> user0 = userManager.stream().filter(User.USERNAME.equal(TEST_USERNAME)).findAny();

        Assert.assertFalse(user0.isPresent());


        databaseUserManager.persist(testUser);

        Optional<User> user1 = userManager.stream().filter(User.USERNAME.equal(TEST_USERNAME)).findAny();

        user.ifPresent(userManager::remove);


        Assert.assertTrue(user1.isPresent());
    }

}
