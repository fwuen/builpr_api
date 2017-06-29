package com.builpr.restapi.converter;


import com.builpr.database.bridge.user.User;
import com.builpr.restapi.model.Request.RegisterRequest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class RegisterRequestToUserModelConverterTest {
    private RegisterRequest request;

    public RegisterRequestToUserModelConverterTest() {

        request = new RegisterRequest().setUsername("test")
                .setPassword("password")
                .setPassword2("password")
                .setEmail("email@mail.de")
                .setBirthday("2017-02-02")
                .setFirstname("Hans")
                .setLastname("Entertainment")
                .setShowName(true)
                .setShowEmail(true)
                .setShowBirthday(true);
    }

    @Test
    public void testConvert() {
        User convertedUser = RegisterRequestToUserModelConverter.from(request);

        Assert.assertEquals(request.getUsername(), convertedUser.getUsername());
        Assert.assertTrue(new BCryptPasswordEncoder().matches(request.getPassword(), convertedUser.getPassword()));
        Assert.assertEquals(request.getEmail(), convertedUser.getEmail());
        Assert.assertEquals(request.getBirthday(), convertedUser.getBirthday().toString());
        Assert.assertEquals(request.getFirstname(), convertedUser.getFirstname());
        Assert.assertEquals(request.getLastname(), convertedUser.getLastname());
        Assert.assertEquals(request.isShowName(), convertedUser.getShowName());
        Assert.assertEquals(request.isShowEmail(), convertedUser.getShowEmail());
        Assert.assertEquals(request.isShowBirthday(), convertedUser.getShowBirthday());
    }
}
