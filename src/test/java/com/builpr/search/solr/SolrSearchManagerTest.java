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

public class SolrSearchManagerTest {

    private static final String LOCAL_BASE_URL = "http://localhost/solr";
    private static final String REMOTE_BASE_URL = "http://192.168.1.50:8983/solr";
    private static final String REMOTE_BASE_URL_EXTERN = "http://builpr.com:8983/solr";

    /*
    @BeforeClass
    public static void prepareSolrSearchManagerTest() {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        
    }
    */
    
    //Creation Tests
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Test
    public void createWithBaseUrl() {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);

        Assert.assertNotNull(solrSearchManager);
    }

    @Test
    public void createWithMockedSolrClient() {
        SolrClient solrClient = new HttpSolrClient.Builder().withBaseSolrUrl(REMOTE_BASE_URL_EXTERN).build();

        SolrSearchManager solrSearchManager = SolrSearchManager.createWithSolrClient(solrClient);

        Assert.assertNotNull(solrSearchManager);
    }

    @Test(expected = NullPointerException.class)
    public void createWithBaseURLIsNull() {
        SolrSearchManager.createWithBaseURL(null);
    }

    @Test(expected = NullPointerException.class)
    public void createWithSolrClientIsNull() {
        SolrSearchManager.createWithSolrClient(null);
    }

    //Reachability Tests
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Test
    public void reachabilityCheckWithSolrClient() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithSolrClient(new HttpSolrClient.Builder(REMOTE_BASE_URL_EXTERN).build());
        Assert.assertNotNull(solrSearchManager);

        System.out.println("" + solrSearchManager.isReachable());
    }

    @Test
    public void reachabilityCheckWithBaseURL() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        Assert.assertNotNull(solrSearchManager);

        solrSearchManager.isReachable();
    }

    @Test
    public void solrServerIsReachable() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        Assert.assertNotNull(solrSearchManager);
        boolean reachable = solrSearchManager.isReachable();
        Assert.assertTrue(reachable);
    }

    @Test(expected = NullPointerException.class)
    public void reachabilityCheckWithSolrClientIsNull() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithSolrClient(null);

        solrSearchManager.isReachable();
    }

    @Test(expected = NullPointerException.class)
    public void reachabilityCheckWithBaseURLIsNull() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(null);

        solrSearchManager.isReachable();
    }

    //Index Tests
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Test
    public void indexRandomStringsWithCommit() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        List<String> categories = new ArrayList<String>();
        categories.add("egories");
        categories.add("cat");
        categories.add("awesome");
        categories.add("random");
        categories.add("very");
        categories.add("some");

        Date date = new Date(System.currentTimeMillis());

        Preconditions.checkNotNull(solrSearchManager);
        Preconditions.checkNotNull(categories);
        Preconditions.checkNotNull(date);

        Printable p5 = Printable.getBuilder().
                withId(10).
                withTitle(SolrTestConstants.p5Title).
                withDescription(SolrTestConstants.p5Description).
                withRating(5).
                withCategories(categories).
                withUploaderId(1).
                withUploadDate(date).
                withNumberOfDownloads(274422342).
                build();

        Preconditions.checkNotNull(p5);
        solrSearchManager.addToIndex(p5);
    }

    @Test
    public void indexWithCommit() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        List<String> categories = new ArrayList<String>();
        categories.add("3D");
        categories.add("car");
        categories.add("faberdashery");
        categories.add("makerbot");
        categories.add("PLA");
        categories.add("vehicle");

        Date date = new Date(System.currentTimeMillis());

        Preconditions.checkNotNull(solrSearchManager);
        Preconditions.checkNotNull(categories);
        Preconditions.checkNotNull(date);

        Printable p1 = Printable.getBuilder().
                withId(1).
                withTitle(SolrTestConstants.p1Title).
                withDescription(SolrTestConstants.p1Description).
                withRating(1).
                withCategories(categories).
                withUploaderId(1).
                withUploadDate(date).
                withNumberOfDownloads(27442).
                build();

        Preconditions.checkNotNull(p1);
        solrSearchManager.addToIndex(p1);
    }

    @Test
    public void indexWithMultipleFilesAndCommit() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        List<String> categories = new ArrayList<String>();
        Date date = new Date(System.currentTimeMillis());
        List<Indexable> indexables = new ArrayList<>();

        Preconditions.checkNotNull(solrSearchManager);
        Preconditions.checkNotNull(categories);
        Preconditions.checkNotNull(date);
        Preconditions.checkNotNull(indexables);

        Printable p1 = Printable.getBuilder().
                withCategories(categories).
                withDescription("Tower").
                withId(2).
                withNumberOfDownloads(13).
                withRating(0).
                withTitle("Customizable tower").
                withUploadDate(date).
                withUploaderId(1).
                build();

        categories.add("plane");
        categories.add("ww1");
        Printable p2 = Printable.getBuilder().
                withCategories(categories).
                withDescription(SolrTestConstants.p2Description).
                withRating(5).
                withNumberOfDownloads(30).
                withId(3).
                withTitle("Gotha G.V").
                withUploadDate(date).
                withUploaderId(1).
                build();

        categories.clear();
        Printable p3 = Printable.getBuilder().
                withCategories(categories).
                withDescription(SolrTestConstants.p3Description).
                withRating(3).
                withNumberOfDownloads(526).
                withId(4).
                withTitle("Hookah/Shisha").
                withUploadDate(date).
                withUploaderId(1).
                build();

        Printable p4 = Printable.getBuilder().
                withCategories(categories).
                withDescription(SolrTestConstants.p4Description).
                withRating(2).
                withNumberOfDownloads(18340).
                withId(5).
                withUploadDate(date).
                withUploaderId(1).
                withTitle(SolrTestConstants.p4Title).
                build();

        indexables.add(p1);
        indexables.add(p2);
        indexables.add(p3);
        indexables.add(p4);

        Preconditions.checkNotNull(indexables);

        for (Indexable i : indexables)
            Preconditions.checkNotNull(i);

        solrSearchManager.addToIndex(indexables);
    }

    @Test
    public void indexWithMultipleFilesAndSameCategoriesAndCommit() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        List<String> categories = new ArrayList<String>();
        Date date = new Date(System.currentTimeMillis());
        List<Indexable> indexables = new ArrayList<>();

        Preconditions.checkNotNull(solrSearchManager);
        Preconditions.checkNotNull(categories);
        Preconditions.checkNotNull(date);
        Preconditions.checkNotNull(indexables);

        categories.add("car");
        Printable p1 = Printable.getBuilder().
                withCategories(categories).
                withDescription("car2").
                withId(6).
                withNumberOfDownloads(13).
                withRating(0).
                withTitle("car2").
                withUploadDate(date).
                withUploaderId(1).
                build();

        Printable p2 = Printable.getBuilder().
                withCategories(categories).
                withDescription("car3").
                withRating(5).
                withNumberOfDownloads(30).
                withId(7).
                withTitle("car3").
                withUploadDate(date).
                withUploaderId(1).
                build();

        Printable p3 = Printable.getBuilder().
                withCategories(categories).
                withDescription("car4").
                withRating(3).
                withNumberOfDownloads(526).
                withId(8).
                withTitle("car4").
                withUploadDate(date).
                withUploaderId(1).
                build();

        Printable p4 = Printable.getBuilder().
                withCategories(categories).
                withDescription("car5").
                withRating(2).
                withNumberOfDownloads(18340).
                withId(9).
                withUploadDate(date).
                withUploaderId(1).
                withTitle("car5").
                build();

        indexables.add(p1);
        indexables.add(p2);
        indexables.add(p3);
        indexables.add(p4);

        for (Indexable p : indexables)
            Preconditions.checkNotNull(p);

        solrSearchManager.addToIndex(indexables);
    }
    
    @Test(expected = NullPointerException.class)
    public void indexNull() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        Indexable idx = null;
        solrSearchManager.addToIndex(idx);
    }
    
    @Test(expected = NullPointerException.class)
    public void indexNullList() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        List<Indexable> indexableList = null;
        solrSearchManager.addToIndex(indexableList);
    }

    //Search Tests
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------

    @Test
    public void searchWithSimpleTerm() throws SearchManagerException {

        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);

        Preconditions.checkNotNull(solrSearchManager);
    
        List<PrintableReference> res = Lists.newArrayList();
        res = solrSearchManager.search("tower");
        for(PrintableReference pr : res) {
            System.out.println(pr.getId() + " " + pr.toString());
        }
    }

    @Test
    public void searchWithTermAndMinimumRating() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);

        List<Filter> filters = new ArrayList<>();
        filters.add(new MinimumRatingFilter(3));

        Preconditions.checkNotNull(solrSearchManager);
        Preconditions.checkNotNull(filters);
        Preconditions.checkState(filters.size() == 1);
    
        List<PrintableReference> res = Lists.newArrayList();
        res = solrSearchManager.search("car", filters);
        for(PrintableReference pr : res) {
            System.out.println(pr.getId() + " " + pr.toString());
        }
    }

    @Test
    public void searchWithTermAndCategory() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);

        List<Filter> filters = new ArrayList<>();
        List<String> categories = new ArrayList<>();
        categories.add("car");
        filters.add(new CategoryFilter(categories));

        Preconditions.checkNotNull(solrSearchManager);
        Preconditions.checkNotNull(filters);
        Preconditions.checkNotNull(categories);
        Preconditions.checkState(categories.size() == 1);
        Preconditions.checkState(filters.size() == 1);
        List<PrintableReference> pr = solrSearchManager.search("car", filters);

        Verify.verifyNotNull(pr);
        Verify.verify(pr.size() > 0);

        // Does not belong to the actual test; for the developer to see the found Printables
        for (PrintableReference prf : pr) {
            System.out.println(prf.getId() + " " + prf.toString());
        }
    }

    @Test
    public void searchWithTermAndCategoryAndMinimumRating() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);

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
        List<PrintableReference> pref = solrSearchManager.search("car", filters);
        for (PrintableReference prf : pref) {
            System.out.println(prf.getId() + " " + prf.toString());
        }
    }
    
    @Test
    public void searchWithCategory() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        List<String> categories = Lists.newArrayList();
        categories.add("car");
        CategoryFilter cf = new CategoryFilter(categories);
        List<Filter> filterList = Lists.newArrayList();
        filterList.add(cf);
        List<PrintableReference> pref = solrSearchManager.search("", filterList);
        for (PrintableReference prf : pref) {
            System.out.println(prf.getId() + " " + prf.toString());
        }
    }
    
    @Test
    public void searchWithCategoryAndMinimumRating() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        List<String> categories = Lists.newArrayList();
        categories.add("car");
        CategoryFilter cf = new CategoryFilter(categories);
        MinimumRatingFilter mrf = new MinimumRatingFilter(1);
        List<Filter> filterList = Lists.newArrayList();
        filterList.add(cf);
        filterList.add(mrf);
        List<PrintableReference> pref = solrSearchManager.search("", filterList);
        for (PrintableReference prf : pref) {
            System.out.println(prf.getId() + " " + prf.toString());
        }
    }
    
    @Test
    public void searchWithMinimumRating() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        MinimumRatingFilter mrf = new MinimumRatingFilter(1);
        List<Filter> filterList = Lists.newArrayList();
        filterList.add(mrf);
        List<PrintableReference> pref = solrSearchManager.search("", filterList);
        for (PrintableReference prf : pref) {
            System.out.println(prf.getId() + " " + prf.toString());
        }
    }
    
    @Test(expected = NullPointerException.class)
    public void searchWithNullTerm() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        Preconditions.checkNotNull(solrSearchManager);
        solrSearchManager.search(null);
    }

    @Test(expected = NullPointerException.class)
    public void searchWithTermAndNullFilterList() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        Preconditions.checkNotNull(solrSearchManager);
        List<Filter> filterList = null;

        solrSearchManager.search("test", filterList);
    }
    
    //TODO entweder komplett entfernen, da mit @NonNull überprüft wird, dass Wert nicht null ist oder funktionsfähig machen
    
    @Test
    public void searchWithTermAndNullCategory() throws SearchManagerException{
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        Preconditions.checkNotNull(solrSearchManager);
        List<Filter> filterList = Lists.newArrayList();
        CategoryFilter f = null;
        
        Preconditions.checkNotNull(filterList);
        filterList.add(f);
        List<PrintableReference> res = solrSearchManager.search("test", filterList);
        Verify.verify(res.size() == 0);
    }
    
    @Test
    public void searchWithTermAndNullMinimumRating() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        Preconditions.checkNotNull(solrSearchManager);
        List<Filter> filterList = Lists.newArrayList();
        MinimumRatingFilter mrf = null;
        filterList.add(mrf);
    
        List<PrintableReference> res = solrSearchManager.search("test", filterList);
        Verify.verify(res.size() == 0);
    }
    
    @Test
    public void searchWithTermAndNullCategoryAndNullMinimumRating() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        MinimumRatingFilter mrf = null;
        CategoryFilter cf = null;
        List<Filter> filterList = Lists.newArrayList();
        filterList.add(cf);
        filterList.add(mrf);
        List<PrintableReference> res = solrSearchManager.search("test", filterList);
        Verify.verify(res.size() == 0);
    }
    
    @Test
    public void searchWithTermAndCategoryAndNullMinimumRating() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        MinimumRatingFilter mrf = null;
        List<String> categories = Lists.newArrayList();
        categories.add("cat");
        CategoryFilter cf = new CategoryFilter(categories);
        List<Filter> filterList = Lists.newArrayList();
        filterList.add(cf);
        filterList.add(mrf);
        List<PrintableReference> res = solrSearchManager.search("test", filterList);
        Verify.verify(res.size() == 0);
    }
    
    @Test
    public void searchWithTermAndNullCategoryAndMinimumRating() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        MinimumRatingFilter mrf = new MinimumRatingFilter(1);
        CategoryFilter cf = null;
        List<Filter> filterList = Lists.newArrayList();
        filterList.add(mrf);
        filterList.add(cf);
        List<PrintableReference> res = solrSearchManager.search("test", filterList);
        Verify.verify(res.size() == 0);
    }
    
    @Test(expected = NullPointerException.class)
    public void searchWithNullTermAndCategoryAndMinimumRating() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
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
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        MinimumRatingFilter mrf = new MinimumRatingFilter(1);
        CategoryFilter cf = null;
        List<Filter> filterList = Lists.newArrayList();
        filterList.add(cf);
        filterList.add(mrf);
        solrSearchManager.search(null, filterList);
    }

    //TODO: Test zu Test machen
    @Test
    public void searchWithTermAndSortByName() throws SearchManagerException
    {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);

        solrSearchManager.search("shisha", SORT.ALPHABETICAL);
    }

    //TODO: Test zu Test machen
    @Test
    public void searchWithTermAndSortByRating() throws SearchManagerException
    {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);

        solrSearchManager.search("shisha", SORT.RATING);
    }

    //TODO: Test zu Test machen
    @Test
    public void searchWithTermAndSortByDownloads() throws SearchManagerException
    {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);

        solrSearchManager.search("shisha", SORT.DOWNLOADS);
    }

    //TODO: Test zu Test machen
    @Test
    public void searchWithTermAndSortByUploadDate() throws SearchManagerException
    {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);

        solrSearchManager.search("shisha", SORT.UPLOAD_DATE);
    }

    //TODO: Test zu Test machen
    @Test
    public void searchWithTermAndSortByNameAndOrderAsc() throws SearchManagerException
    {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);

        solrSearchManager.search("shisha", SORT.ALPHABETICAL, ORDER.ASC);
    }

    //TODO: Test zu Test machen
    @Test
    public void searchWithTermAndSortByRatingAndOrderAsc() throws SearchManagerException
    {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);

        solrSearchManager.search("shisha", SORT.RATING, ORDER.ASC);
    }

    //TODO: Test zu Test machen
    @Test
    public void searchWithTermAndSortByDownloadsAndOrderAsc() throws SearchManagerException
    {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);

        solrSearchManager.search("shisha", SORT.DOWNLOADS, ORDER.ASC);
    }

    //TODO: Test zu Test machen
    @Test
    public void searchWithTermAndSortByUploadDateAndOrderAsc() throws SearchManagerException
    {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);

        solrSearchManager.search("shisha", SORT.UPLOAD_DATE, ORDER.ASC);
    }

    //TODO: Test zu Test machen
    @Test
    public void searchWithTermAndSortByNameAndOrderDesc() throws SearchManagerException
    {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);

        solrSearchManager.search("shisha", SORT.ALPHABETICAL, ORDER.DESC);
    }

    //TODO: Test zu Test machen
    @Test
    public void searchWithTermAndSortByRatingAndOrderDesc() throws SearchManagerException
    {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);

        solrSearchManager.search("shisha", SORT.RATING, ORDER.DESC);
    }

    //TODO: Test zu Test machen
    @Test
    public void searchWithTermAndSortByDownloadsAndOrderDesc() throws SearchManagerException
    {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);

        solrSearchManager.search("shisha", SORT.DOWNLOADS, ORDER.DESC);
    }

    //TODO: Test zu Test machen
    @Test
    public void searchWithTermAndSortByUploadDateAndOrderDesc() throws SearchManagerException
    {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);

        solrSearchManager.search("shisha", SORT.UPLOAD_DATE, ORDER.DESC);
    }
    
/*
    //Delete/Clear Index Tests
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------
    //TODO Delete Tests
    @Test (expected = NullPointerException.class)
    public void testDeleteFromIndexSinglePrintableNull() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        PrintableReference printableReference = null;
        solrSearchManager.deleteFromIndex(printableReference);
    }

    @Test (expected = NullPointerException.class)
    public void testDeleteFromIndexMultiplePrintablesListNull() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        List<PrintableReference> list = null;
        solrSearchManager.deleteFromIndex(list);
    }

    @Test (expected = NullPointerException.class)
    public void testDeleteFromIndexMultiplePrintablesOneIsNull() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        List<PrintableReference> list = Lists.newArrayList();
        PrintableReference printableReference = null;
        list.add(printableReference);
        solrSearchManager.deleteFromIndex(list);
    }

    @Test
    public void deleteSinglePrintableFromIndex() {

    }

    @Test
    public void deleteMultiplePrintablesFromIndex() {

    }
    
    
    @Test
    public void testClearIndex() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        Preconditions.checkNotNull(solrSearchManager);
        solrSearchManager.clearIndex();
    }
    */

    /*
    @AfterClass
    public static void clearIndex() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        Preconditions.checkNotNull(solrSearchManager);
        solrSearchManager.clearIndex();
    }
    */
}
