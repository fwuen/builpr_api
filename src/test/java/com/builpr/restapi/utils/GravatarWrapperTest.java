package com.builpr.restapi.utils;

import com.google.common.base.Preconditions;
import org.apache.commons.validator.routines.UrlValidator;
import org.junit.Assert;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * @author Dominic Fuchs
 */
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

        Assert.assertTrue(urlIsValid(url));
        Assert.assertTrue(urlLeadsToImage(url));
    }

    @Test
    public void getUrlWithEmail() {
        String url = new GravatarWrapper().getUrl(EMAIL);

        Assert.assertTrue(urlIsValid(url));
        Assert.assertTrue(urlLeadsToImage(url));
    }



    private boolean urlLeadsToImage(String url) {
        Preconditions.checkArgument(urlIsValid(url));

        try {
            BufferedImage image = ImageIO.read(new URL(url));

            Assert.assertNotNull(image);
            Assert.assertTrue(image.getWidth() > 0);
            Assert.assertTrue(image.getHeight() > 0);
        } catch(Exception ex) {
            return false;
        }

        return true;
    }

    private boolean urlIsValid(String url) {
        Assert.assertNotNull(url);
        Assert.assertTrue(url.length() > 0);
        Assert.assertTrue(UrlValidator.getInstance().isValid(url));

        return true;
    }

}
