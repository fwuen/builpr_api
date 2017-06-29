package com.builpr.restapi.converter;


import com.builpr.database.bridge.user.User;
import com.builpr.database.bridge.user.UserImpl;
import com.builpr.restapi.model.Request.ProfileEditRequest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Date;

public class ProfileEditRequestToUserModelConverterTest {

    private ProfileEditRequest request;
    private User oldUser;

    public ProfileEditRequestToUserModelConverterTest() {
        oldUser = new UserImpl()
                .setUsername("Hans")
                .setPassword("abcd")
                .setFirstname("Hans")
                .setLastname("Entertainment")
                .setEmail("hans@entertainment.de")
                .setBirthday(new Date(System.currentTimeMillis()))
                .setShowName(false)
                .setShowEmail(false)
                .setShowBirthday(false);

        request = new ProfileEditRequest()
                .setEmail("email@mail.de")
                .setFirstName("firstname")
                .setLastName("lastname")
                .setDescription("hallo")
                .setOldPassword("password1")
                .setPassword("password")
                .setPassword2("password")
                .setShowBirthday(true)
                .setShowEmail(true)
                .setShowName(true);
    }

    @Test
    public void testConvert() {
        User newUser = ProfileEditRequestToUserModelConverter.editUser(oldUser, request);

        Assert.assertEquals(request.getEmail(), newUser.getEmail());
        Assert.assertEquals(request.getFirstName(), newUser.getFirstname());
        Assert.assertEquals(request.getLastName(), newUser.getLastname());
        Assert.assertEquals(request.getDescription(), newUser.getDescription().get());
        Assert.assertTrue(new BCryptPasswordEncoder().matches(request.getPassword(), newUser.getPassword()));
        Assert.assertEquals(request.getShowBirthday(), newUser.getShowBirthday());
        Assert.assertEquals(request.getShowEmail(), newUser.getShowEmail());
        Assert.assertEquals(request.getShowName(), newUser.getShowName());

    }

}
