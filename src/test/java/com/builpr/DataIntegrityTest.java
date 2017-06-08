package com.builpr;

import com.builpr.database.db.builpr.user.User;
import com.builpr.database.db.builpr.user.UserImpl;
import com.builpr.restapi.service.UserService;
import org.junit.Before;

import java.sql.Date;

public abstract class DataIntegrityTest {

    protected static final String TEST_USER_NAME = "test";

    @Before
    public void fillDatabaseWithTestData() {
        clearDatabase();
        addUserToDatabase();
    }

    private void clearDatabase() {
        new UserService().deleteByUsername(TEST_USER_NAME);
    }

    private void addUserToDatabase() {
        User user = new UserImpl();
        user.setUsername(TEST_USER_NAME);
        user.setPassword(TEST_USER_NAME);
        user.setEmail(TEST_USER_NAME + "@" + TEST_USER_NAME + ".de");
        user.setFirstname(TEST_USER_NAME);
        user.setLastname(TEST_USER_NAME);
        user.setBirthday(new Date(0));

        new UserService().persist(user);
    }

    /* TODO: Add more test data */

}
