package com.builpr.search.solr;

import com.builpr.search.SearchManagerException;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.beans.DocumentObjectBinder;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class SolrSearchManagerTest {
    
    private static final String LOCAL_BASE_URL = "http://localhost/solr";
    private static final String REMOTE_BASE_URL = "http://192.168.1.50:8983/solr";

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
    
        solrSearchManager.isReachable();
    }

    @Test
    public void reachabilityCheckWithBaseURL() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL);
        Assert.assertNotNull(solrSearchManager);
        
        solrSearchManager.isReachable();
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



}
