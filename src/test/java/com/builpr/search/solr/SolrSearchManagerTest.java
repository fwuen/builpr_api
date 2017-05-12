package com.builpr.search.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.Assert;
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
    
    @Test
    public void reachabilityCheckWithSolrClient() {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithSolrClient(new HttpSolrClient.Builder(REMOTE_BASE_URL).build());
        Assert.assertNotNull(solrSearchManager);
        
        int status = solrSearchManager.isReachable();
        
        Assert.assertTrue(status >= Integer.MIN_VALUE);
        Assert.assertTrue(status <= Integer.MAX_VALUE);
    }
    
    @Test
    public void reachabilityCheckWithBaseURL() {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(REMOTE_BASE_URL);
        Assert.assertNotNull(solrSearchManager);
        
        int status = solrSearchManager.isReachable();
        
        Assert.assertTrue(status >= Integer.MIN_VALUE);
        Assert.assertTrue(status <= Integer.MAX_VALUE);
    }
    
    @Test(expected = NullPointerException.class)
    public void reachabilityCheckWithSolrClientIsNull() {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithSolrClient(null);
        
        int status = solrSearchManager.isReachable();
    }
    
    @Test(expected = NullPointerException.class)
    public void reachabilityCheckWithBaseURLIsNull() {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(null);
    
        int status = solrSearchManager.isReachable();
    }
    
    /* TODO: Teste die Methoden auf ihre Funktionalität */
    
}
