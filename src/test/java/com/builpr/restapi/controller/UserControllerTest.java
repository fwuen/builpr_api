package com.builpr.restapi.controller;


import com.builpr.database.db.builpr.user.UserImpl;
import com.builpr.database.db.builpr.user.UserManager;
import com.builpr.restapi.ApiRestTestCase;
import com.builpr.restapi.utils.Connector;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.stream.Collectors;

public class UserControllerTest extends ApiRestTestCase {

    public UserControllerTest() {
        try {
            addToDataset(new UserImpl().setUid(1)
                    .setUsername("Hansi")
                    .setFirstname("Hans")
            .setBirthday(new Date(9013246))
            .setEmail("Test@Test.de")
            .setLastname("Wurst")
            .setPassword("aoasdf")
            .setRegtime(new Timestamp(19283074))
            .setShowBirthday(true)
            .setShowEmail(true)
            .setShowName(true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testTest() {
        int i = Connector.getUserManager().stream().collect(Collectors.toList()).size();

        Assert.assertEquals(1, i);
    }
}
