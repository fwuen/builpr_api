package com.builpr.search.solr;

import com.builpr.search.ORDER;
import com.builpr.search.SORT;
import com.builpr.search.SearchManager;
import com.builpr.search.SearchManagerException;
import com.builpr.search.filter.CategoryFilter;
import com.builpr.search.filter.Filter;
import com.builpr.search.filter.MinimumRatingFilter;
import com.builpr.search.model.Indexable;
import com.builpr.search.model.Printable;
import com.builpr.search.model.PrintableReference;
import com.google.common.base.Preconditions;
import com.google.common.base.Verify;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.assertj.core.util.Lists;
import org.junit.*;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Felix Wünsche
 */
public class SolrSearchTest {
    
    private static final int car1Id = 1;
    private static final int towerId = 2;
    private static final int planeId = 3;
    private static final int shishaId = 4;
    private static final int fidgetId = 5;
    private static final int car2Id = 6;
    private static final int car3Id = 7;
    private static final int car4Id = 8;
    private static final int car5Id = 9;
    private static final int shisha2Id = 10;
    
    @BeforeClass
    public static void prepareIndexForSearch() throws SearchManagerException {
        
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        List<String> categoriesCar1 = Lists.newArrayList();
        List<String> categoriesCarsWithSameCategory = Lists.newArrayList();
        List<String> categoriesTower = Lists.newArrayList();
        List<String> categoriesShisha = Lists.newArrayList();
        List<String> categoriesFidget = Lists.newArrayList();
        List<String> categoriesPlane = Lists.newArrayList();
        List<Indexable> indexables = new ArrayList<>();
        categoriesCar1.add("3D");
        categoriesCar1.add("car");
        categoriesCar1.add("faberdashery");
        categoriesCar1.add("makerbot");
        categoriesCar1.add("PLA");
        categoriesCar1.add("vehicle");
        
        Date date = new Date(System.currentTimeMillis());
        
        indexables.add(Printable.getBuilder().
                withId(1).
                withTitle(SolrTestConstants.p1Title).
                withDescription(SolrTestConstants.p1Description).
                withRating(1).
                withCategories(categoriesCar1).
                withUploaderId(car1Id).
                withUploadDate(date).
                withNumberOfDownloads(27442).
                build());
        
        indexables.add(Printable.getBuilder().
                withCategories(categoriesTower).
                withDescription("Tower").
                withId(towerId).
                withNumberOfDownloads(13).
                withRating(0).
                withTitle("Customizable tower").
                withUploadDate(date).
                withUploaderId(1).
                build());
        
        categoriesPlane.add("plane");
        categoriesPlane.add("ww1");
        indexables.add(Printable.getBuilder().
                withCategories(categoriesPlane).
                withDescription(SolrTestConstants.p2Description).
                withRating(5).
                withNumberOfDownloads(30).
                withId(planeId).
                withTitle("Gotha G.V").
                withUploadDate(date).
                withUploaderId(1).
                build());
        
        indexables.add(Printable.getBuilder().
                withCategories(categoriesShisha).
                withDescription(SolrTestConstants.p3Description).
                withRating(3).
                withNumberOfDownloads(526).
                withId(shishaId).
                withTitle("Hookah/Shisha").
                withUploadDate(date).
                withUploaderId(1).
                build());
        
        indexables.add(Printable.getBuilder().
                withCategories(categoriesFidget).
                withDescription(SolrTestConstants.p4Description).
                withRating(2).
                withNumberOfDownloads(18340).
                withId(fidgetId).
                withUploadDate(date).
                withUploaderId(1).
                withTitle(SolrTestConstants.p4Title).
                build());
        
        categoriesCarsWithSameCategory.add("car");
        indexables.add(Printable.getBuilder().
                withCategories(categoriesCarsWithSameCategory).
                withDescription("car2").
                withId(car2Id).
                withNumberOfDownloads(13).
                withRating(0).
                withTitle("car2").
                withUploadDate(date).
                withUploaderId(1).
                build());
        
        indexables.add(Printable.getBuilder().
                withCategories(categoriesCarsWithSameCategory).
                withDescription("car3").
                withRating(5).
                withNumberOfDownloads(30).
                withId(car3Id).
                withTitle("car3").
                withUploadDate(date).
                withUploaderId(1).
                build());
        
        indexables.add(Printable.getBuilder().
                withCategories(categoriesCarsWithSameCategory).
                withDescription("car4").
                withRating(3).
                withNumberOfDownloads(526).
                withId(car4Id).
                withTitle("car4").
                withUploadDate(date).
                withUploaderId(1).
                build());
        
        indexables.add(Printable.getBuilder().
                withCategories(categoriesCarsWithSameCategory).
                withDescription("car5").
                withRating(2).
                withNumberOfDownloads(18340).
                withId(car5Id).
                withUploadDate(date).
                withUploaderId(1).
                withTitle("car5").
                build());
        
        indexables.add(Printable.getBuilder().
                withCategories(categoriesShisha).
                withDescription(SolrTestConstants.p3Description).
                withRating(1).
                withNumberOfDownloads(1).
                withId(shisha2Id).
                withTitle("A Shisha").
                withUploadDate(new Date(System.currentTimeMillis() - 10000)).
                withUploaderId(1).
                build());
        
        solrSearchManager.addToIndex(indexables);
    }
    
    @Test
    public void searchWithSimpleTerm() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        Preconditions.checkNotNull(solrSearchManager);
        List<PrintableReference> res = solrSearchManager.search("tower");
        Assert.assertTrue(res.size() == 1);
        Assert.assertTrue(Integer.parseInt(res.get(0).getId()) == towerId);
    }
    
    @Test
    public void searchWithTermAndMinimumRating() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        List<Filter> filters = new ArrayList<>();
        filters.add(new MinimumRatingFilter(3));
        Preconditions.checkNotNull(solrSearchManager);
        Preconditions.checkNotNull(filters);
        Preconditions.checkState(filters.size() == 1);
        List<PrintableReference> res = solrSearchManager.search("car", filters);
        Assert.assertTrue(res.size() == 2);
        Assert.assertTrue(Integer.parseInt(res.get(0).getId()) == car3Id);
        Assert.assertTrue(Integer.parseInt(res.get(1).getId()) == car4Id);
    }
    
    @Test
    public void searchWithTermAndCategory() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        List<Filter> filters = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        categories.add("car");
        filters.add(new CategoryFilter(categories));
        Preconditions.checkNotNull(solrSearchManager);
        Preconditions.checkNotNull(filters);
        Preconditions.checkNotNull(categories);
        Preconditions.checkState(categories.size() == 1);
        Preconditions.checkState(filters.size() == 1);
        List<PrintableReference> res = solrSearchManager.search("car", filters);
        Assert.assertTrue(res.size() == 5);
        Assert.assertTrue(Integer.parseInt(res.get(0).getId()) == car1Id);
        Assert.assertTrue(Integer.parseInt(res.get(1).getId()) == car2Id);
        Assert.assertTrue(Integer.parseInt(res.get(2).getId()) == car3Id);
        Assert.assertTrue(Integer.parseInt(res.get(3).getId()) == car4Id);
        Assert.assertTrue(Integer.parseInt(res.get(4).getId()) == car5Id);
    }
    
    @Test
    public void searchWithTermAndCategoryAndMinimumRating() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        List<Filter> filters = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        categories.add("car");
        filters.add(new MinimumRatingFilter(2));
        filters.add(new CategoryFilter(categories));
        Preconditions.checkNotNull(solrSearchManager);
        Preconditions.checkNotNull(filters);
        Preconditions.checkNotNull(categories);
        Preconditions.checkState(filters.size() == 2);
        Preconditions.checkState(categories.size() == 1);
        List<PrintableReference> res = solrSearchManager.search("car", filters);
        Assert.assertTrue(res.size() == 3);
        Assert.assertTrue(Integer.parseInt(res.get(0).getId()) == car3Id);
        Assert.assertTrue(Integer.parseInt(res.get(1).getId()) == car4Id);
        Assert.assertTrue(Integer.parseInt(res.get(2).getId()) == car5Id);
    }
    
    @Test
    public void searchWithCategory() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        List<String> categories = Lists.newArrayList();
        categories.add("car");
        CategoryFilter cf = new CategoryFilter(categories);
        List<Filter> filterList = Lists.newArrayList();
        filterList.add(cf);
        List<PrintableReference> res = solrSearchManager.search("", filterList);
        Assert.assertTrue(res.size() == 5);
        Assert.assertTrue(Integer.parseInt(res.get(0).getId()) == car1Id);
        Assert.assertTrue(Integer.parseInt(res.get(1).getId()) == car2Id);
        Assert.assertTrue(Integer.parseInt(res.get(2).getId()) == car3Id);
        Assert.assertTrue(Integer.parseInt(res.get(3).getId()) == car4Id);
        Assert.assertTrue(Integer.parseInt(res.get(4).getId()) == car5Id);
    }
    
    @Test
    public void searchWithCategoryAndMinimumRating() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        List<String> categories = Lists.newArrayList();
        categories.add("car");
        CategoryFilter cf = new CategoryFilter(categories);
        MinimumRatingFilter mrf = new MinimumRatingFilter(1);
        List<Filter> filterList = Lists.newArrayList();
        filterList.add(cf);
        filterList.add(mrf);
        List<PrintableReference> res = solrSearchManager.search("", filterList);
        Assert.assertTrue(res.size() == 4);
        Assert.assertTrue(Integer.parseInt(res.get(0).getId()) == car1Id);
        Assert.assertTrue(Integer.parseInt(res.get(1).getId()) == car3Id);
        Assert.assertTrue(Integer.parseInt(res.get(2).getId()) == car4Id);
        Assert.assertTrue(Integer.parseInt(res.get(3).getId()) == car5Id);
    }
    
    @Test
    public void searchWithMinimumRating() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        MinimumRatingFilter mrf = new MinimumRatingFilter(2);
        List<Filter> filterList = Lists.newArrayList();
        filterList.add(mrf);
        List<PrintableReference> res = solrSearchManager.search("", filterList);
        Assert.assertTrue(res.size() == 6);
        Assert.assertTrue(Integer.parseInt(res.get(0).getId()) == planeId);
        Assert.assertTrue(Integer.parseInt(res.get(1).getId()) == shishaId);
        Assert.assertTrue(Integer.parseInt(res.get(2).getId()) == fidgetId);
        Assert.assertTrue(Integer.parseInt(res.get(3).getId()) == car3Id);
        Assert.assertTrue(Integer.parseInt(res.get(4).getId()) == car4Id);
        Assert.assertTrue(Integer.parseInt(res.get(5).getId()) == car5Id);
    }
    
    @Test(expected = NullPointerException.class)
    public void searchWithNullTerm() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        Preconditions.checkNotNull(solrSearchManager);
        solrSearchManager.search(null);
    }
    
    @Test(expected = NullPointerException.class)
    public void searchWithTermAndNullFilterList() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        Preconditions.checkNotNull(solrSearchManager);
        List<Filter> filterList = null;
        
        solrSearchManager.search("test", filterList);
    }
    
    @Test
    public void searchWithTermAndNullCategory() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        Preconditions.checkNotNull(solrSearchManager);
        List<Filter> filterList = Lists.newArrayList();
        CategoryFilter f = null;
        Preconditions.checkNotNull(filterList);
        filterList.add(f);
        List<PrintableReference> res = solrSearchManager.search("test", filterList);
        Assert.assertTrue(res.size() == 0);
    }
    
    @Test
    public void searchWithTermAndNullMinimumRating() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        Preconditions.checkNotNull(solrSearchManager);
        List<Filter> filterList = Lists.newArrayList();
        MinimumRatingFilter mrf = null;
        filterList.add(mrf);
        List<PrintableReference> res = solrSearchManager.search("test", filterList);
        Assert.assertTrue(res.size() == 0);
    }
    
    @Test
    public void searchWithTermAndNullCategoryAndNullMinimumRating() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        MinimumRatingFilter mrf = null;
        CategoryFilter cf = null;
        List<Filter> filterList = Lists.newArrayList();
        filterList.add(cf);
        filterList.add(mrf);
        List<PrintableReference> res = solrSearchManager.search("test", filterList);
        Assert.assertTrue(res.size() == 0);
    }
    
    @Test
    public void searchWithTermAndCategoryAndNullMinimumRating() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        MinimumRatingFilter mrf = null;
        List<String> categories = Lists.newArrayList();
        categories.add("cat");
        CategoryFilter cf = new CategoryFilter(categories);
        List<Filter> filterList = Lists.newArrayList();
        filterList.add(cf);
        filterList.add(mrf);
        List<PrintableReference> res = solrSearchManager.search("test", filterList);
        Assert.assertTrue(res.size() == 0);
    }
    
    @Test
    public void searchWithTermAndNullCategoryAndMinimumRating() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        MinimumRatingFilter mrf = new MinimumRatingFilter(1);
        CategoryFilter cf = null;
        List<Filter> filterList = Lists.newArrayList();
        filterList.add(mrf);
        filterList.add(cf);
        List<PrintableReference> res = solrSearchManager.search("test", filterList);
        Assert.assertTrue(res.size() == 0);
    }
    
    @Test(expected = NullPointerException.class)
    public void searchWithNullTermAndCategoryAndMinimumRating() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        MinimumRatingFilter mrf = new MinimumRatingFilter(1);
        List<String> categories = Lists.newArrayList();
        categories.add("cat");
        CategoryFilter cf = new CategoryFilter(categories);
        List<Filter> filterList = Lists.newArrayList();
        filterList.add(cf);
        filterList.add(mrf);
        solrSearchManager.search(null, filterList);
    }
    
    @Test(expected = NullPointerException.class)
    public void searchWithNullTermAndNullCategoryAndMinimumRating() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        MinimumRatingFilter mrf = new MinimumRatingFilter(1);
        CategoryFilter cf = null;
        List<Filter> filterList = Lists.newArrayList();
        filterList.add(cf);
        filterList.add(mrf);
        solrSearchManager.search(null, filterList);
    }
    
    @Test
    public void searchWithTermAndSortByName() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        List<PrintableReference> res = solrSearchManager.search("shisha", SORT.ALPHABETICAL);
        Assert.assertTrue(res.size() == 2);
        Assert.assertTrue(Integer.parseInt(res.get(0).getId()) == shisha2Id);
        Assert.assertTrue(Integer.parseInt(res.get(1).getId()) == shishaId);
    }
    
    @Test
    public void searchWithTermAndSortByRating() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        List<PrintableReference> res = solrSearchManager.search("shisha", SORT.RATING);
        Assert.assertTrue(res.size() == 2);
        Assert.assertTrue(Integer.parseInt(res.get(0).getId()) == shisha2Id);
        Assert.assertTrue(Integer.parseInt(res.get(1).getId()) == shishaId);
    }
    
    @Test
    public void searchWithTermAndSortByDownloads() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        List<PrintableReference> res = solrSearchManager.search("shisha", SORT.DOWNLOADS);
        Assert.assertTrue(res.size() == 2);
        Assert.assertTrue(Integer.parseInt(res.get(0).getId()) == shisha2Id);
        Assert.assertTrue(Integer.parseInt(res.get(1).getId()) == shishaId);
    }
    
    @Test
    public void searchWithTermAndSortByUploadDate() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        List<PrintableReference> res = solrSearchManager.search("shisha", SORT.UPLOAD_DATE);
        Assert.assertTrue(res.size() == 2);
        Assert.assertTrue(Integer.parseInt(res.get(0).getId()) == shisha2Id);
        Assert.assertTrue(Integer.parseInt(res.get(1).getId()) == shishaId);
    }
    
    @Test
    public void searchWithTermAndSortByNameAndOrderAsc() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        List<PrintableReference> res = solrSearchManager.search("shisha", SORT.ALPHABETICAL, ORDER.ASC);
        Assert.assertTrue(res.size() == 2);
        Assert.assertTrue(Integer.parseInt(res.get(0).getId()) == shisha2Id);
        Assert.assertTrue(Integer.parseInt(res.get(1).getId()) == shishaId);
    }
    
    @Test
    public void searchWithTermAndSortByRatingAndOrderAsc() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        List<PrintableReference> res = solrSearchManager.search("shisha", SORT.RATING, ORDER.ASC);
        Assert.assertTrue(res.size() == 2);
        Assert.assertTrue(Integer.parseInt(res.get(0).getId()) == shisha2Id);
        Assert.assertTrue(Integer.parseInt(res.get(1).getId()) == shishaId);
    }
    
    @Test
    public void searchWithTermAndSortByDownloadsAndOrderAsc() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        List<PrintableReference> res = solrSearchManager.search("shisha", SORT.DOWNLOADS, ORDER.ASC);
        Assert.assertTrue(res.size() == 2);
        Assert.assertTrue(Integer.parseInt(res.get(0).getId()) == shisha2Id);
        Assert.assertTrue(Integer.parseInt(res.get(1).getId()) == shishaId);
    }
    
    @Test
    public void searchWithTermAndSortByUploadDateAndOrderAsc() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        List<PrintableReference> res = solrSearchManager.search("shisha", SORT.UPLOAD_DATE, ORDER.ASC);
        Assert.assertTrue(res.size() == 2);
        Assert.assertTrue(Integer.parseInt(res.get(0).getId()) == shisha2Id);
        Assert.assertTrue(Integer.parseInt(res.get(1).getId()) == shishaId);
    }
    
    @Test
    public void searchWithTermAndSortByNameAndOrderDesc() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        List<PrintableReference> res = solrSearchManager.search("shisha", SORT.ALPHABETICAL, ORDER.DESC);
        Assert.assertTrue(res.size() == 2);
        Assert.assertTrue(Integer.parseInt(res.get(0).getId()) == shishaId);
        Assert.assertTrue(Integer.parseInt(res.get(1).getId()) == shisha2Id);
    }
    
    @Test
    public void searchWithTermAndSortByRatingAndOrderDesc() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        List<PrintableReference> res = solrSearchManager.search("shisha", SORT.RATING, ORDER.DESC);
        Assert.assertTrue(res.size() == 2);
        Assert.assertTrue(Integer.parseInt(res.get(0).getId()) == shishaId);
        Assert.assertTrue(Integer.parseInt(res.get(1).getId()) == shisha2Id);
    }
    
    @Test
    public void searchWithTermAndSortByDownloadsAndOrderDesc() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        List<PrintableReference> res = solrSearchManager.search("shisha", SORT.DOWNLOADS, ORDER.DESC);
        Assert.assertTrue(res.size() == 2);
        Assert.assertTrue(Integer.parseInt(res.get(0).getId()) == shishaId);
        Assert.assertTrue(Integer.parseInt(res.get(1).getId()) == shisha2Id);
    }
    
    @Test
    public void searchWithTermAndSortByUploadDateAndOrderDesc() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        List<PrintableReference> res = solrSearchManager.search("shisha", SORT.UPLOAD_DATE, ORDER.DESC);
        Assert.assertTrue(res.size() == 2);
        Assert.assertTrue(Integer.parseInt(res.get(0).getId()) == shishaId);
        Assert.assertTrue(Integer.parseInt(res.get(1).getId()) == shisha2Id);
    }

    //TODO
    @Test
    public void searchWithTermAndCategoryFilterAndSortByTitle() {

    }

    @Test
    public void searchWithTermAndMinimumRatingFilterAndSortByTitle() {

    }

    @Test
    public void searchWithTermAndCategoryFilterAndSortByDownloads() {

    }

    @Test
    public void searchWithTermAndMinimumRatingFilterAndSortByDownloads() {

    }

    @Test
    public void searchWithTermAndCategoryFilterAndSortByRelevance() {

    }

    @Test
    public void searchWithTermAndMinimumRatingFilterAndSortByRelevance() {

    }

    @Test
    public void searchWithTermAndCategoryFilterAndSortByRating() {

    }

    @Test
    public void searchWithTermAndMinimumRatingFilterAndSortByRating() {

    }

    @Test
    public void searchWithTermAndCategoryFilterAndSortByUploadDate() {

    }

    @Test
    public void searchWithTermAndMinimumRatingFilterAndSortByUploadDate() {

    }

    //TODO wie?
    @Test
    public void searchWithTermAndInvalidOrder() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
    }
    
    @Test(expected = NullPointerException.class)
    public void searchWithTermAndNullOrder() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        solrSearchManager.search("shisha", SORT.ALPHABETICAL, null);
    }
    
    //TODO wie?
    @Test
    public void searchWithTermAndInvalidSort() {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
    }
    
    //TODO wie?
    @Test
    public void searchWithTermAndNullSort() {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
    }
    
    @AfterClass
    public static void clearIndex() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        Preconditions.checkNotNull(solrSearchManager);
        solrSearchManager.clearIndex();
    }
}
