package com.builpr.restapi.controller;


import com.builpr.database.db.builpr.user.User;
import com.builpr.database.db.builpr.user.UserImpl;
import com.builpr.restapi.AbstractDatabaseTestcase;
import org.junit.Test;

import java.sql.Date;

public class UserControllerTest extends AbstractDatabaseTestcase {
    public UserControllerTest() {
        User a = new UserImpl().setUsername("Hans")
                .setBirthday(new Date(1242344))
                .setPassword("Hallo123")
                .setFirstname("hans")
                .setLastname("Wurst")
                .setEmail("Email@mail.de");

        User b = new UserImpl().setUsername("Hans")
                .setBirthday(new Date(1242344))
                .setPassword("Hallo123")
                .setFirstname("hans")
                .setLastname("Wurst")
                .setEmail("Email@mail.de");

        addToDataset(a);
        addToDataset(b);
    }

    @Test
    public void test() {

    }
}
