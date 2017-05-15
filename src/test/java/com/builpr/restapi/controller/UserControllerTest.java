package com.builpr.restapi.controller;


import com.builpr.database.db.builpr.user.User;
import com.builpr.database.db.builpr.user.UserImpl;
import com.builpr.database.db.builpr.user.UserManager;
import com.builpr.restapi.model.PublicUser;
import com.builpr.restapi.utils.Connector;
import org.junit.*;

import java.sql.Date;
import java.util.stream.Collectors;

public class UserControllerTest {

    private UserManager userManager;
    private UserController userController;
    private PublicUser testUser;

    public UserControllerTest() {
        userManager = Connector.getConnection().getOrThrow(UserManager.class);

        userController = new UserController();
    }

    @Before
    public void setUp() {
        User user = new UserImpl();

        user.setUsername("Hans");
        user.setEmail("hans@wurst.de");
        user.setBirthday(new Date(12345344));
        user.setFirstname("Hans");
        user.setLastname("Wurst");
        user.setPassword("alasdpf");

        userManager.persist(user);

        testUser = new PublicUser(user);
    }

    @After
    public void tearDown() {
        for (User usr :
                userManager.stream().collect(Collectors.toList())) {
            userManager.remove(usr);
        }
    }

    @Test
    public void userControllerTest() {
        try {
            PublicUser actualUser = userController.showProfile(testUser.getUsername());

            Assert.assertEquals(testUser, actualUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
