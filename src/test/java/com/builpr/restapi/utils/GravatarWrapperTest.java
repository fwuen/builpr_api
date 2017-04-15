package com.builpr.restapi.utils;

import com.google.common.base.Preconditions;
import org.apache.commons.validator.routines.UrlValidator;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class GravatarWrapperTest {

    private static final String EMAIL = "syntarex@gmail.com";
    private static final String UNKNOWN_EMAIL = "ulululu@ulululu.de";
    private static final String INVALID_EMAIL = "This is no email";



    @Test
    public void create() {
        new GravatarWrapper();
    }

    @Test(expected = NullPointerException.class)
    public void getUrlWithNull() {
        new GravatarWrapper().getUrl(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUrlWithEmptyEmail() {
        new GravatarWrapper().getUrl("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUrlWithInvalidEmail() {
        new GravatarWrapper().getUrl(INVALID_EMAIL);
    }

    @Test
    public void getUrlWithUnknownEmail() {
        String url = new GravatarWrapper().getUrl(UNKNOWN_EMAIL);

        Assert.assertNotNull(url);
        Assert.assertTrue(url.length() > 0);
        Assert.assertTrue(UrlValidator.getInstance().isValid(url));
        Assert.assertTrue(urlLeadsToImage(url));

        System.out.println(url);
    }

    @Test
    public void getUrlWithEmail() {
        String url = new GravatarWrapper().getUrl(EMAIL);

        Assert.assertNotNull(url);
        Assert.assertTrue(url.length() > 0);
        Assert.assertTrue(UrlValidator.getInstance().isValid(url));
        Assert.assertTrue(urlLeadsToImage(url));
    }

    /* TODO: Code this. */
    private boolean urlLeadsToImage(String url) {
        Preconditions.checkNotNull(url);
        Preconditions.checkArgument(url.length() > 0);
        Preconditions.checkArgument(UrlValidator.getInstance().isValid(url));

        return true;
    }

}
