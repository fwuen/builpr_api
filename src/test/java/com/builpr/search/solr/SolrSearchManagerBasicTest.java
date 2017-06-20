package com.builpr.search.solr;

import com.builpr.search.SearchManagerException;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.Assert;
import org.junit.Test;

public class SolrSearchManagerBasicTest {
    //Creation Tests
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Test
    public void createWithBaseUrl() {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        
        Assert.assertNotNull(solrSearchManager);
    }
    
    @Test
    public void createWithMockedSolrClient() {
        SolrClient solrClient = new HttpSolrClient.Builder().withBaseSolrUrl(SolrTestConstants.REMOTE_BASE_URL_EXTERN).build();
        
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
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithSolrClient(new HttpSolrClient.Builder(SolrTestConstants.REMOTE_BASE_URL_EXTERN).build());
        Assert.assertNotNull(solrSearchManager);
    }
    
    @Test
    public void reachabilityCheckWithBaseURL() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
        Assert.assertNotNull(solrSearchManager);
        
        solrSearchManager.isReachable();
    }
    
    @Test
    public void solrServerIsReachable() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SolrTestConstants.REMOTE_BASE_URL_EXTERN);
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
    
}
