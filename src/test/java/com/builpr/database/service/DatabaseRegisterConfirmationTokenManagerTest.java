package com.builpr.database.service;

import com.builpr.Constants;
import com.builpr.database.bridge.BuilprApplicationBuilder;
import com.builpr.database.bridge.register_confirmation_token.RegisterConfirmationToken;
import com.builpr.database.bridge.register_confirmation_token.RegisterConfirmationTokenImpl;
import com.builpr.database.bridge.register_confirmation_token.RegisterConfirmationTokenManager;
import com.builpr.database.bridge.user.User;
import com.builpr.database.bridge.user.UserImpl;
import com.builpr.restapi.utils.TokenGenerator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;
import java.util.Optional;

/**
 * tests the DatabaseRegisterConfirmationManager
 */
public class DatabaseRegisterConfirmationTokenManagerTest {
    private static String TEST_USER_NAME = "regTokenTest";
    private RegisterConfirmationToken testTokenEntry;
    private User testUser;
    private DatabaseUserManager databaseUserManager;
    private DatabaseRegisterConfirmationTokenManager databaseRegisterConfirmationTokenManager;
    private RegisterConfirmationTokenManager registerConfirmationTokenManager;
    private String token;

    public DatabaseRegisterConfirmationTokenManagerTest() {
        databaseUserManager = new DatabaseUserManager();
        registerConfirmationTokenManager = new BuilprApplicationBuilder().withPassword(Constants.DATABASE_PASSWORD)
                .build().getOrThrow(RegisterConfirmationTokenManager.class);
        databaseRegisterConfirmationTokenManager = new DatabaseRegisterConfirmationTokenManager();
    }

    @Before
    public void setUp() {

        if (databaseUserManager.isPresent(TEST_USER_NAME)) {
            databaseUserManager.deleteByUsername(TEST_USER_NAME);
        }

        testUser = new UserImpl()
                .setUsername(TEST_USER_NAME)
                .setEmail("databaseRegister@token.de")
                .setPassword(new BCryptPasswordEncoder().encode("password"))
                .setBirthday(new Date(System.currentTimeMillis()))
                .setFirstname("Michael")
                .setLastname("Ballack")
                .setShowBirthday(false)
                .setShowName(false)
                .setShowEmail(false);

        databaseUserManager.persist(testUser);

        TokenGenerator generator = new TokenGenerator(60, true);
        token = generator.generate();
        testTokenEntry = new RegisterConfirmationTokenImpl()
                .setUserId(testUser.getUserId())
                .setToken(token);

        Optional<RegisterConfirmationToken> entry = registerConfirmationTokenManager.stream().
                filter(
                        RegisterConfirmationToken.USER_ID.equal(testUser.getUserId())
                                .and(RegisterConfirmationToken.TOKEN.equal(token))).
                findFirst();

        entry.ifPresent(registerConfirmationTokenManager::remove);

        registerConfirmationTokenManager.persist(testTokenEntry);
    }

    @Test
    public void testPersist() throws Exception {
        testTokenEntry = new RegisterConfirmationTokenImpl()
                .setUserId(testUser.getUserId())
                .setToken(token);

        // if there is a similar token already in the database, delete it
        Optional<RegisterConfirmationToken> entry = registerConfirmationTokenManager.stream().
                filter(
                        RegisterConfirmationToken.USER_ID.equal(testUser.getUserId())
                                .and(RegisterConfirmationToken.TOKEN.equal(token))).
                findFirst();

        entry.ifPresent(registerConfirmationTokenManager::remove);

        Assert.assertFalse(
                registerConfirmationTokenManager.stream().
                        anyMatch(RegisterConfirmationToken.USER_ID.equal
                                (testUser.getUserId())
                                .and(RegisterConfirmationToken.TOKEN.equal(token)))
        );

        databaseRegisterConfirmationTokenManager.persist(testTokenEntry);

        Assert.assertTrue(
                registerConfirmationTokenManager.stream().
                        anyMatch(RegisterConfirmationToken.USER_ID.equal
                                (testUser.getUserId())
                                .and(RegisterConfirmationToken.TOKEN.equal(token)))
        );
    }

    @Test
    public void testIsPresent() {
        Assert.assertTrue(databaseRegisterConfirmationTokenManager.isPresent(testUser.getUserId(), token));
    }

    @Test
    public void testGetTokenEntry() {
        RegisterConfirmationToken entry = databaseRegisterConfirmationTokenManager.getTokenEntry(testUser.getUserId(), token);
        Assert.assertEquals(testTokenEntry, entry);
    }

    /*@Test
    public void testDelete() {
        Assert.assertTrue(
                registerConfirmationTokenManager.stream().
                        anyMatch(RegisterConfirmationToken.USER_ID.equal
                                (testUser.getUserId())
                                .and(RegisterConfirmationToken.TOKEN.equal(token)))
        );

        databaseRegisterConfirmationTokenManager.delete(testTokenEntry);

        Assert.assertFalse(
                registerConfirmationTokenManager.stream().
                        anyMatch(RegisterConfirmationToken.USER_ID.equal
                                (testUser.getUserId())
                                .and(RegisterConfirmationToken.TOKEN.equal(token)))
        );
    }*/
}
