package com.builpr.restapi.utils;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Markus Goller
 *
 * Tests for the correct validation of categories
 */
public class CategoryValidatorTest {
    private CategoryValidator categoryValidator;
    private List<String> categories = new ArrayList<>();
    private static final String CATEGORY1 = "EinGanzNormalerTag";
    private static final String CATEGORY2 = "NURKLEINBUCHSTABEN";
    private static final String CATEGORY3 = "tagsdürfennur20buchstabenhaben";
    private static final String CATEGORY4 = "KeineSonderzeichen:";
    private static final String CATEGORY5 = "KeineSonderzeichen[";
    private static final String CATEGORY6 = "KeineSonderzeichen]";
    private static final String CATEGORY7 = "KeineSonderzeichen{";
    private static final String CATEGORY8 = "KeineSonderzeichen}";
    private static final String CATEGORY9 = "KeineSonderzeichen|";
    private static final String CATEGORY10 = "KeineSonderzeichen#";
    private static final String CATEGORY11 = "KeineSonderzeichen&";
    private static final String CATEGORY12 = "KeineSonderzeichen*";
    private static final String CATEGORY13 = "KeineSonderzeichen=";
    private static final String CATEGORY14 = "KeineSonderzeichen§";
    private static final String CATEGORY15 = "KeineSonderzeichen$";
    private static final String CATEGORY16 = "KeineSonderzeichen%";
    private static final String CATEGORY17 = "KeineSonderzeichen/";
    private static final String CATEGORY18 = "KeineSonderzeichen(";
    private static final String CATEGORY19 = "KeineSonderzeichen)";
    private static final String CATEGORY20 = "KeineSonderzeichen!";

    public CategoryValidatorTest() {
        categoryValidator = new CategoryValidator();
        categories.add(CATEGORY1);
        categories.add(CATEGORY2);
        categories.add(CATEGORY3);
        categories.add(CATEGORY4);
        categories.add(CATEGORY5);
        categories.add(CATEGORY6);
        categories.add(CATEGORY7);
        categories.add(CATEGORY8);
        categories.add(CATEGORY9);
        categories.add(CATEGORY10);
        categories.add(CATEGORY11);
        categories.add(CATEGORY12);
        categories.add(CATEGORY13);
        categories.add(CATEGORY14);
        categories.add(CATEGORY15);
        categories.add(CATEGORY16);
        categories.add(CATEGORY17);
        categories.add(CATEGORY18);
        categories.add(CATEGORY19);
        categories.add(CATEGORY20);
    }

    @Test
    public void testCheckCategories() {
        List<String> checkedCategories = categoryValidator.checkCategories(categories);
        Assert.assertTrue(checkedCategories.size() < categories.size());
        Assert.assertTrue(Objects.equals(checkedCategories.get(0), "einganznormalertag"));
        Assert.assertTrue(Objects.equals(checkedCategories.get(1), "nurkleinbuchstaben"));
        Assert.assertTrue(checkedCategories.size() == 2);
    }
}
