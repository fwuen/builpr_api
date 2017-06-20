package com.builpr.search.model;

import com.google.common.base.VerifyException;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.Date;

/**
 * @author Felix Wünsche
 */
public class PrintableTest {
    @Test (expected = NullPointerException.class)
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
    
    @Test (expected = NullPointerException.class)
    public void createPrintableWithDescriptionNull() {
        Printable.getBuilder().
                withDescription(null).
                withCategories(Lists.newArrayList()).
                withId(0).
                withNumberOfDownloads(0).
                withRating(0).
                withTitle("Empty").
                withUploadDate(new Date()).
                withUploaderId(0).
                build();
    }
    
    @Test (expected = NullPointerException.class)
    public void createPrintableWithCategoriesNull() {
        Printable.getBuilder().
                withDescription("Empty").
                withCategories(null).
                withId(0).
                withNumberOfDownloads(0).
                withRating(0).
                withTitle("Empty").
                withUploadDate(new Date()).
                withUploaderId(0).
                build();
    }
    
    @Test (expected = NullPointerException.class)
    public void createPrintableWithTitleNull() {
        Printable.getBuilder().
                withDescription("Empty").
                withCategories(Lists.newArrayList()).
                withId(0).
                withNumberOfDownloads(0).
                withRating(0).
                withTitle(null).
                withUploadDate(new Date()).
                withUploaderId(0).
                build();
    }
    
    @Test (expected = NullPointerException.class)
    public void createPrintableWithDateNull() {
        Printable.getBuilder().
                withDescription("Empty").
                withCategories(Lists.newArrayList()).
                withId(0).
                withNumberOfDownloads(0).
                withRating(0).
                withTitle("Empty").
                withUploadDate(null).
                withUploaderId(0).
                build();
    }
    
    @Test (expected = VerifyException.class)
    public void createPrintableWithEmptyTitle() {
        Printable.getBuilder().
                withDescription("Empty").
                withCategories(Lists.newArrayList()).
                withId(0).
                withNumberOfDownloads(0).
                withRating(0).
                withTitle("").
                withUploadDate(new Date()).
                withUploaderId(0).
                build();
    }
    
    @Test (expected = VerifyException.class)
    public void createPrintableWithEmptyDescription() {
        Printable.getBuilder().
                withDescription("").
                withCategories(Lists.newArrayList()).
                withId(0).
                withNumberOfDownloads(0).
                withRating(0).
                withTitle("Empty").
                withUploadDate(new Date()).
                withUploaderId(0).
                build();
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void createPrintableWithIdLessThanZero() {
        Printable.getBuilder().
                withDescription("Empty").
                withCategories(Lists.newArrayList()).
                withId(-1).
                withNumberOfDownloads(0).
                withRating(0).
                withTitle("Empty").
                withUploadDate(new Date()).
                withUploaderId(0).
                build();
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void createPrintableWithRatingLowerThanMinimum() {
        Printable.getBuilder().
                withDescription("Empty").
                withCategories(Lists.newArrayList()).
                withId(0).
                withNumberOfDownloads(0).
                withRating(-1).
                withTitle("Empty").
                withUploadDate(new Date()).
                withUploaderId(0).
                build();
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void createPrintableWithRatingLargerThanMaximum() {
        Printable.getBuilder().
                withDescription("Empty").
                withCategories(Lists.newArrayList()).
                withId(0).
                withNumberOfDownloads(0).
                withRating(6).
                withTitle("Empty").
                withUploadDate(new Date()).
                withUploaderId(0).
                build();
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void createPrintableWithUploaderIdLowerThanZero() {
        Printable.getBuilder().
                withDescription("Empty").
                withCategories(Lists.newArrayList()).
                withId(0).
                withNumberOfDownloads(0).
                withRating(0).
                withTitle("Empty").
                withUploadDate(new Date()).
                withUploaderId(-1).
                build();
    }
    
    @Test (expected = IllegalArgumentException.class)
    public void createPrintableWithNumberOfDownloadsLowerThanZero() {
        Printable.getBuilder().
                withDescription("Empty").
                withCategories(Lists.newArrayList()).
                withId(0).
                withNumberOfDownloads(-1).
                withRating(0).
                withTitle("Empty").
                withUploadDate(new Date()).
                withUploaderId(0).
                build();
    }
}
