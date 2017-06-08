package com.builpr.search.solr;

import com.builpr.search.ORDER;
import com.builpr.search.SORT;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SolrSearchManagerTest {

    private static final String LOCAL_BASE_URL = "http://localhost/solr";
    private static final String REMOTE_BASE_URL = "http://192.168.1.50:8983/solr";
    private static final String REMOTE_BASE_URL_EXTERN = "http://192.168.1.50:8983/solr";

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

        Printable p1 = Printable.getBuilder().
                withId(10).
                withTitle("azreiöjypoOIPAÖOIRHQHWEUPRIALDIFGhahueiprqi").
                withDescription("FPWQOIQEROQWIEUöfuqpweiurhqweriaödööfejÖHPIQUHEWJRK:BNLKJDXVOUIHERQLEHR").
                withRating(5).
                withCategories(categories).
                withUploaderId(1).
                withUploadDate(date).
                withNumberOfDownloads(274422342).
                build();

        Preconditions.checkNotNull(p1);
        solrSearchManager.addToIndex(p1);
    }

    //TODO Test korrigieren, der testet aktuell nicht wirklich was
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
                withTitle("car (mercedes c9 inspired)").
                withDescription(("This car model is loosely based on a mercedes C9 le man car.\n" +
                        "\n" +
                        "The model was split in half as it prints better this way. Unfortunately on my set up it warped quite badly and so it could do with some tweaking.\n" +
                        "\n" +
                        "The model is pictured in two halves but can be glued together on an unwarped print.\n" +
                        "\n" +
                        "I've uploaded a step. file for anyone who would like to edit the model.\n" +
                        "\n" +
                        "UPDATE1- I've added two more files, an stl and stp of 'car thingi whole'. This is the same model which will probably require support material but you won't need to glue the two halves together. Thanks for the suggestion jfpion.\n" +
                        "\n" +
                        "Please upload a photo if you print one :)\n" +
                        "\n" +
                        "The example pictured was printed at 0.2mm on a rep2 with a 25% infill which is definitely overkill. I also halved the default extruder speed in makerware in the hope of improving surface finish. It took about 4 hours to print. The arctic white plastic is from faberdashery, definitely recommended despite the price.\n" +
                        "\n" +
                        "I would also suggest enlarging the stl file before printing, as you can see on my print the wheel arch was too thin so it hasn't been filled in properly.\n" +
                        "\n" +
                        "The wheels were super glued in place but I'm sure someone could modify the car to allow for rotating wheels. The aim was to glue the two body halves together, which should be straight forward as long as your model doesn't warp!\n" +
                        "\n" +
                        "I positioned a number of ear rafts around the car from whpthomas http://www.thingiverse.com/thing:38272. The aim was to reduce warping although the blue tape I was printing on started to lift off the build plate, I think printing PLA straight onto the build plate would be fine or just using stronger wider tape.\n").toLowerCase()).
                withRating(1).
                withCategories(categories).
                withUploaderId(1).
                withUploadDate(date).
                withNumberOfDownloads(27442).
                build();

        Preconditions.checkNotNull(p1);
        solrSearchManager.addToIndex(p1);
    }

    //TODO Test korrigieren, der testet aktuell nicht wirklich was
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
                withDescription("This is just a simple gotha bomber from world war 1. print up on its nose with supports for best result. enjoy!").
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
                withDescription("Its just a thin hookah / shisha...\nNOT smokable... :D").
                withRating(3).
                withNumberOfDownloads(526).
                withId(4).
                withTitle("Hookah/Shisha").
                withUploadDate(date).
                withUploaderId(1).
                build();

        Printable p4 = Printable.getBuilder().
                withCategories(categories).
                withDescription("This is a quick model I threw together making a fidget spinner. I thought I'd change things up a bit and make it easier to hold onto and fun to do cool \"tricks\" on almost like a yoyo. Enjoy and do as you wish.\n" +
                        "Print Settings\n" +
                        "\n" +
                        "Printer:\n" +
                        "Prusa i3 Mk 2\n" +
                        "\n" +
                        "Rafts:\n" +
                        "No\n" +
                        "\n" +
                        "Supports:\n" +
                        "No\n" +
                        "\n" +
                        "Resolution:\n" +
                        ".2\n" +
                        "\n" +
                        "Infill:\n" +
                        "At least 20%\n" +
                        "\n" +
                        "\n" +
                        "Notes:\n" +
                        "You will need:\n" +
                        "\n" +
                        "4x Bearings (preferably ABEC 7 or better)\n" +
                        "3D printer (Duh)\n" +
                        "super glue if you wanna be risky\n" +
                        "sand paper to get correct resistance on cap\n" +
                        "\n" +
                        "Print using PETG or PLA for best fit, and sand down caps pole to match inside of bearing for the correct resistance of your bearing (Many Manufacturers are slightly different)").
                withRating(2).
                withNumberOfDownloads(18340).
                withId(5).
                withUploadDate(date).
                withUploaderId(1).
                withTitle("Trick Fidget Spinner (Three and Two weight models)").
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

    //TODO Test korrigieren, der testet aktuell nicht wirklich was
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

    //TODO Test korrigieren, der testet aktuell nicht wirklich was
    @Test
    public void searchWithSimpleTerm() throws SearchManagerException {

        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);

        Preconditions.checkNotNull(solrSearchManager);
        List<PrintableReference> pr = solrSearchManager.search("shisha");

        Verify.verifyNotNull(pr);
        Verify.verify(pr.size() > 0);

        // Does not belong to the actual test; shisha for the developer to see the found Printables
        for (PrintableReference prf : pr) {
            System.out.println(prf.toString());
        }
    }

    //TODO Test korrigieren, der testet aktuell nicht wirklich was
    @Test
    public void searchWithTermAndMinimumRating() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);

        List<Filter> filters = new ArrayList<>();
        filters.add(new MinimumRatingFilter(2));

        Preconditions.checkNotNull(solrSearchManager);
        Preconditions.checkNotNull(filters);
        Preconditions.checkState(filters.size() == 1);
        List<PrintableReference> pr = solrSearchManager.search("shisha", filters);

        Verify.verifyNotNull(pr);
        Verify.verify(pr.size() > 0);

        // Does not belong to the actual test; shisha for the developer to see the found Printables
        for (PrintableReference prf : pr) {
            System.out.println(prf.getId() + " " + prf.toString());
        }
    }

    //TODO Test korrigieren, der testet aktuell nicht wirklich was
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

        // Does not belong to the actual test; shisha for the developer to see the found Printables
        for (PrintableReference prf : pr) {
            System.out.println(prf.getId() + " " + prf.toString());
        }
    }

    //TODO Test korrigieren, der testet aktuell nicht wirklich was
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
        List<PrintableReference> pr = solrSearchManager.search("car", filters);


        Verify.verifyNotNull(pr);
        Verify.verify(pr.size() > 0);

        // Does not belong to the actual test; shisha for the developer to see the found Printables
        for (PrintableReference prf : pr) {
            System.out.println(prf.getId() + " " + prf.toString());
        }
    }

    @Test
    public void searchWithCategory() {

    }

    @Test
    public void searchWithCategoryAndMinimumRating() {

    }

    @Test
    public void searchWithMinimumRating() {

    }

    @Test(expected = NullPointerException.class)
    public void searchWithNullTerm() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        Preconditions.checkNotNull(solrSearchManager);
        solrSearchManager.search(null);
    }

    @Test(expected = SearchManagerException.class)
    public void searchWithTermAndNullCategory() throws SearchManagerException{
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        Preconditions.checkNotNull(solrSearchManager);
        List<Filter> filterList = Lists.newArrayList();
        CategoryFilter f = null;

        Preconditions.checkNotNull(filterList);
        filterList.add(f);
        solrSearchManager.search("test", filterList);
    }

    @Test(expected = NullPointerException.class)
    public void searchWithTermAndNullFilterList() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        Preconditions.checkNotNull(solrSearchManager);
        List<Filter> filterList = null;

        solrSearchManager.search("test", filterList);
    }

    @Test(expected = SearchManagerException.class)
    public void searchWithTermAndNullMinimumRating() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        Preconditions.checkNotNull(solrSearchManager);
        List<Filter> filterList = Lists.newArrayList();
        MinimumRatingFilter mrf = null;
        filterList.add(mrf);

        solrSearchManager.search("test", filterList);
    }

    @Ignore
    @Test(expected = NullPointerException.class)
    public void searchWithTermAndNullCategoryAndNullMinimumRating() {

    }

    @Ignore
    @Test(expected = NullPointerException.class)
    public void searchWithTermAndCategoryAndNullMinimumRating() {

    }

    @Ignore
    @Test(expected = NullPointerException.class)
    public void searchWithTermAndNullCategoryAndMinimumRating() {

    }

    @Ignore
    @Test(expected = NullPointerException.class)
    public void searchWithNullTermAndCategoryAndMinimumRating() {

    }

    @Ignore
    @Test(expected = NullPointerException.class)
    public void searchWithNullTermAndNullCategoryAndMinimumRating() {

    }

    @Ignore
    @Test(expected = NullPointerException.class)
    public void indexNullAndCommit() {

    }

    @Ignore
    @Test(expected = NullPointerException.class)
    public void indexMultipleNullAndCommit() {

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
    @Test
    public void testClearIndex() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL_EXTERN);
        Preconditions.checkNotNull(solrSearchManager);
        solrSearchManager.clearIndex();
    }*/
}
