package com.builpr.search.model;

import com.google.common.base.VerifyException;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * @author Felix Wünsche
 */
public class PrintableTest {
    @Test(expected = NullPointerException.class)
    public void createPrintableWithAllNull() {
        Printable.getBuilder().
                withDescription(null).
                withCategories(null).
                withId(0).
                withNumberOfDownloads(0).
                withRating(0).
                withTitle(null).
                withUploadDate(null).
                withUploaderId(0).
                build();
    }
    
    @Test(expected = NullPointerException.class)
    public void createPrintableWithDescriptionNull() {
        Printable.getBuilder().
                withDescription(null).
                withCategories(Lists.newArrayList()).
                withId(0).
                withNumberOfDownloads(0).
                withRating(0).
                withTitle("empty").
                withUploadDate(new Date()).
                withUploaderId(0).
                build();
    }
    
    @Test(expected = NullPointerException.class)
    public void createPrintableWithCategoriesNull() {
        Printable.getBuilder().
                withDescription("empty").
                withCategories(null).
                withId(0).
                withNumberOfDownloads(0).
                withRating(0).
                withTitle("empty").
                withUploadDate(new Date()).
                withUploaderId(0).
                build();
    }
    
    @Test(expected = NullPointerException.class)
    public void createPrintableWithTitleNull() {
        Printable.getBuilder().
                withDescription("empty").
                withCategories(Lists.newArrayList()).
                withId(0).
                withNumberOfDownloads(0).
                withRating(0).
                withTitle(null).
                withUploadDate(new Date()).
                withUploaderId(0).
                build();
    }
    
    @Test(expected = NullPointerException.class)
    public void createPrintableWithDateNull() {
        Printable.getBuilder().
                withDescription("empty").
                withCategories(Lists.newArrayList()).
                withId(0).
                withNumberOfDownloads(0).
                withRating(0).
                withTitle("empty").
                withUploadDate(null).
                withUploaderId(0).
                build();
    }
    
    @Test(expected = VerifyException.class)
    public void createPrintableWithemptyTitle() {
        Printable.getBuilder().
                withDescription("empty").
                withCategories(Lists.newArrayList()).
                withId(0).
                withNumberOfDownloads(0).
                withRating(0).
                withTitle("").
                withUploadDate(new Date()).
                withUploaderId(0).
                build();
    }
    
    @Test(expected = VerifyException.class)
    public void createPrintableWithemptyDescription() {
        Printable.getBuilder().
                withDescription("").
                withCategories(Lists.newArrayList()).
                withId(0).
                withNumberOfDownloads(0).
                withRating(0).
                withTitle("empty").
                withUploadDate(new Date()).
                withUploaderId(0).
                build();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void createPrintableWithIdLessThanZero() {
        Printable.getBuilder().
                withDescription("empty").
                withCategories(Lists.newArrayList()).
                withId(-1).
                withNumberOfDownloads(0).
                withRating(0).
                withTitle("empty").
                withUploadDate(new Date()).
                withUploaderId(0).
                build();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void createPrintableWithRatingLowerThanMinimum() {
        Printable.getBuilder().
                withDescription("empty").
                withCategories(Lists.newArrayList()).
                withId(0).
                withNumberOfDownloads(0).
                withRating(-1).
                withTitle("empty").
                withUploadDate(new Date()).
                withUploaderId(0).
                build();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void createPrintableWithRatingLargerThanMaximum() {
        Printable.getBuilder().
                withDescription("empty").
                withCategories(Lists.newArrayList()).
                withId(0).
                withNumberOfDownloads(0).
                withRating(6).
                withTitle("empty").
                withUploadDate(new Date()).
                withUploaderId(0).
                build();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void createPrintableWithUploaderIdLowerThanZero() {
        Printable.getBuilder().
                withDescription("empty").
                withCategories(Lists.newArrayList()).
                withId(0).
                withNumberOfDownloads(0).
                withRating(0).
                withTitle("empty").
                withUploadDate(new Date()).
                withUploaderId(-1).
                build();
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void createPrintableWithNumberOfDownloadsLowerThanZero() {
        Printable.getBuilder().
                withDescription("empty").
                withCategories(Lists.newArrayList()).
                withId(0).
                withNumberOfDownloads(-1).
                withRating(0).
                withTitle("empty").
                withUploadDate(new Date()).
                withUploaderId(0).
                build();
    }

    @Test
    public void getId() {
        Printable printable = Printable.getBuilder().
                withId(1).
                withCategories(Lists.newArrayList()).
                withTitle("empty").
                withDescription("empty").
                withRating(1).
                withNumberOfDownloads(1).
                withUploadDate(new Date()).
                withUploaderId(1).
                build();

        Assert.assertTrue(printable.getId() == 1);
    }

    @Test
    public void getDescription() {
        Printable printable = Printable.getBuilder().
                withId(1).
                withCategories(Lists.newArrayList()).
                withTitle("empty").
                withDescription("empty").
                withRating(1).
                withNumberOfDownloads(1).
                withUploadDate(new Date()).
                withUploaderId(1).
                build();

        Assert.assertNotNull(printable.getDescription());
        Assert.assertTrue(printable.getDescription().equals("empty"));
    }

    @Test
    public void getTitle() {
        Printable printable = Printable.getBuilder().
                withId(1).
                withCategories(Lists.newArrayList()).
                withTitle("empty").
                withDescription("empty").
                withRating(1).
                withNumberOfDownloads(1).
                withUploadDate(new Date()).
                withUploaderId(1).
                build();

        Assert.assertNotNull(printable.getTitle());
        Assert.assertTrue(printable.getTitle().equals("empty"));
    }

    @Test
    public void getRating() {
        Printable printable = Printable.getBuilder().
                withId(1).
                withCategories(Lists.newArrayList()).
                withTitle("empty").
                withDescription("empty").
                withRating(1).
                withNumberOfDownloads(1).
                withUploadDate(new Date()).
                withUploaderId(1).
                build();

        Assert.assertTrue(printable.getRating() == 1);
    }

    @Test
    public void getCategories() {
        List<String> categories = Lists.newArrayList();
        Printable printable = Printable.getBuilder().
                withId(1).
                withCategories(categories).
                withTitle("empty").
                withDescription("empty").
                withRating(1).
                withNumberOfDownloads(1).
                withUploadDate(new Date()).
                withUploaderId(1).
                build();

        Assert.assertNotNull(printable.getCategories());
        Assert.assertTrue(printable.getCategories().equals(categories));
    }

    @Test
    public void getNumberOfDownloads() {
        Printable printable = Printable.getBuilder().
                withId(1).
                withCategories(Lists.newArrayList()).
                withTitle("empty").
                withDescription("empty").
                withRating(1).
                withNumberOfDownloads(1).
                withUploadDate(new Date()).
                withUploaderId(1).
                build();

        Assert.assertTrue(printable.getNumberOfDownloads() == 1);
    }

    @Test
    public void getUploadDate() {
        Date date = new Date();
        Printable printable = Printable.getBuilder().
                withId(1).
                withCategories(Lists.newArrayList()).
                withTitle("empty").
                withDescription("empty").
                withRating(1).
                withNumberOfDownloads(1).
                withUploadDate(date).
                withUploaderId(1).
                build();

        Assert.assertNotNull(printable.getUploadDate());
        Assert.assertTrue(printable.getUploadDate().equals(date));
    }

    @Test
    public void getUploaderId() {
        Date date = new Date();
        Printable printable = Printable.getBuilder().
                withId(1).
                withCategories(Lists.newArrayList()).
                withTitle("empty").
                withDescription("empty").
                withRating(1).
                withNumberOfDownloads(1).
                withUploadDate(date).
                withUploaderId(1).
                build();

        Assert.assertNotNull(printable.getUploaderId());
        Assert.assertTrue(printable.getUploaderId() == 1);
    }
}
