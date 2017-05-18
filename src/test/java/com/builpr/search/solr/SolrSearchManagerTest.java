package com.builpr.search.solr;

import com.builpr.search.SearchManagerException;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class SolrSearchManagerTest {
    
    private static final String BASE_URL = "localhost";
    private static final String REMOTE_BASE_URL = "http://builpr.com:6970/solr";

    @Test
    public void createWithBaseUrl() {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(BASE_URL);

        Assert.assertNotNull(solrSearchManager);
    }

    @Test
    public void createWithMockedSolrClient() {
        SolrClient solrClient = new HttpSolrClient.Builder().withBaseSolrUrl("localhost").build();

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
    public void reachabilityCheckWithSolrClient() {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithSolrClient(new HttpSolrClient.Builder(REMOTE_BASE_URL).build());
        Assert.assertNotNull(solrSearchManager);
    
        try {
            solrSearchManager.isReachable();
        } catch (SearchManagerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void reachabilityCheckWithBaseURL() {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL);
        Assert.assertNotNull(solrSearchManager);
        
        try {
            solrSearchManager.isReachable();
        } catch (SearchManagerException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = NullPointerException.class)
    public void reachabilityCheckWithSolrClientIsNull() {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithSolrClient(null);
    
        try {
            solrSearchManager.isReachable();
        } catch (SearchManagerException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = NullPointerException.class)
    public void reachabilityCheckWithBaseURLIsNull() {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(null);
    
        try {
            solrSearchManager.isReachable();
        } catch (SearchManagerException e) {
            e.printStackTrace();
        }
    }



}
