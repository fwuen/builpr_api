package com.builpr;

import com.builpr.database.DatabaseUserManager;
import com.builpr.database.bridge.user.User;
import com.builpr.database.bridge.user.UserImpl;
import org.junit.Before;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;

public abstract class DataIntegrityTest {

    protected static final String TEST_USER_NAME = "test";

    @Before
    public void fillDatabaseWithTestData() {
        clearDatabase();
        addUserToDatabase();
    }

    private void clearDatabase() {
        new DatabaseUserManager().deleteByUsername(TEST_USER_NAME);
    }

    private void addUserToDatabase() {
        User user = new UserImpl();
        user.setUsername(TEST_USER_NAME);
        user.setPassword(new BCryptPasswordEncoder().encode(TEST_USER_NAME));
        user.setEmail(TEST_USER_NAME + "@" + TEST_USER_NAME + ".de");
        user.setFirstname(TEST_USER_NAME);
        user.setLastname(TEST_USER_NAME);
        user.setBirthday(new Date(0));

        new DatabaseUserManager().persist(user);
    }

}
