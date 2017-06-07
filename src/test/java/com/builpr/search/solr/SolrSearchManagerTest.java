package com.builpr.search.solr;

import com.builpr.search.SearchManagerException;
import com.builpr.search.filter.Filter;
import com.builpr.search.filter.MinimumRatingFilter;
import com.builpr.search.model.Indexable;
import com.builpr.search.model.Printable;
import com.builpr.search.model.PrintableReference;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.util.NamedList;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SolrSearchManagerTest {
    
    private static final String LOCAL_BASE_URL = "http://localhost/solr";
    private static final String REMOTE_BASE_URL = "http://192.168.1.50:8983/solr";
    private static final String REMOTE_BASE_URL_EXTERN = "http://builpr.com:8983/solr";

    @Test
    public void createWithBaseUrl() {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL);

        Assert.assertNotNull(solrSearchManager);
    }

    @Test
    public void createWithMockedSolrClient() {
        SolrClient solrClient = new HttpSolrClient.Builder().withBaseSolrUrl(REMOTE_BASE_URL).build();

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

    //TODO: alle reachability-Tests überprüfen
    @Test
    public void reachabilityCheckWithSolrClient() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithSolrClient(new HttpSolrClient.Builder(REMOTE_BASE_URL).build());
        Assert.assertNotNull(solrSearchManager);
    
        System.out.println(""+solrSearchManager.isReachable());
    }

    @Test
    public void reachabilityCheckWithBaseURL() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL);
        Assert.assertNotNull(solrSearchManager);
        
        solrSearchManager.isReachable();
    }

    @Test
    public void solrServerIsReachable() {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL);
        Assert.assertNotNull(solrSearchManager);

        try {
            boolean reachable = solrSearchManager.isReachable();
            Assert.assertTrue(reachable);
        } catch (SearchManagerException e) {
            e.printStackTrace();
        }
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
    public void testIndexWithCommit() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL);
        List<String> categories = new ArrayList<String>();
        categories.add("3D");
        categories.add("car");
        categories.add("faberdashery");
        categories.add("makerbot");
        categories.add("PLA");
        categories.add("vehicle");

        Date date = new Date(System.currentTimeMillis());

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
        solrSearchManager.addToIndex(p1);
    }
    
    @Test
    public void testIndexWithMultipleFilesAndCommit() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL);
        List<String> categories = new ArrayList<String>();
        Date date = new Date(System.currentTimeMillis());
        List<Indexable> indexables = new ArrayList<>();
        
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
        
        solrSearchManager.addToIndex(indexables);
    }

    //TODO make it work
    @Test
    public void testSearchWithSimpleTerm() throws SearchManagerException {
        /*
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(
                REMOTE_BASE_URL
        );
        List<PrintableReference> pr = solrSearchManager.search("car");
        PrintableReference ref = pr.get(0);
        */

        /*
        SolrClient solr = new HttpSolrClient.Builder().withBaseSolrUrl(REMOTE_BASE_URL).build();
        SolrQuery query = new SolrQuery();
        //query.set("q", "title:*car*");
        query.setQuery("title:*car*");
        QueryResponse res = null;
        try {
            res = solr.query("testing", query);
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SolrDocumentList list = res.getResults();
        list.get(0);
        */

        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL);

        List<PrintableReference> pr = solrSearchManager.search("shisha");

        for(PrintableReference prf : pr) {
            System.out.println(prf.toString());
        }
    }

    @Test
    public void testSearchWithTermAndFilter() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL);
    
        List<Filter> filters = new ArrayList<>();
        filters.add(new MinimumRatingFilter(2));
        List<PrintableReference> pr = solrSearchManager.search("just", filters);
        
        for(PrintableReference prf : pr) {
            System.out.println(prf.getId() + " " + prf.toString());
        }
    }
    
}
