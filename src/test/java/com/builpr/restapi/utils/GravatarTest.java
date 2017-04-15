package com.builpr.restapi.utils;

import org.apache.commons.validator.routines.UrlValidator;
import org.junit.Assert;
import org.junit.Test;

public class GravatarTest {

    private static final String EMAIL = "syntarex@gmail.com";
    private static final String NOT_REGISTERED_EMAIL = "ululul@ulululu.de";



    @Test(expected = NullPointerException.class)
    public void getImageURLWithNull() {
        Gravatar.getImageURLByEmail(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getImageURLWithEmpty() {
        Gravatar.getImageURLByEmail("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getImageURLWithInvalidEmail() {
        Gravatar.getImageURLByEmail("This is no email");
    }

    @Test
    public void getImageURLWithEmail() {
        String imageURL = Gravatar.getImageURLByEmail(EMAIL);

        Assert.assertNotNull(imageURL);
        Assert.assertTrue(imageURL.length() > 0);
        Assert.assertTrue(new UrlValidator().isValid(imageURL));
    }

    @Test
    public void getDefaultImageURLWithUnregisteredEmail() {
        String imageURL = Gravatar.getImageURLByEmail(NOT_REGISTERED_EMAIL);

        Assert.assertNotNull(imageURL);
        Assert.assertTrue(imageURL.length() > 0);
        Assert.assertTrue(new UrlValidator().isValid(imageURL));
    }

}
