package com.builpr.search.solr;

import com.builpr.Constants;
import com.builpr.search.SearchManagerException;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Felix Wünsche
 */
public class SolrSearchManagerBasicTest {
    //Creation Tests
    //--------------------------------------------------------------------------------------------------------------------------------------------------------------
    @Test
    public void createWithBaseUrl() {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(Constants.SOLR_BASE_URL);
        
        Assert.assertNotNull(solrSearchManager);
    }
    
    @Test
    public void createWithMockedSolrClient() {
        SolrClient solrClient = new HttpSolrClient.Builder().withBaseSolrUrl(Constants.SOLR_BASE_URL).build();
        
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
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithSolrClient(new HttpSolrClient.Builder(Constants.SOLR_BASE_URL).build());
        Assert.assertNotNull(solrSearchManager);
    }
    
    @Test
    public void reachabilityCheckWithBaseURL() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(Constants.SOLR_BASE_URL);
        Assert.assertNotNull(solrSearchManager);
        
        solrSearchManager.isReachable();
    }
    
    @Test
    public void solrServerIsReachable() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(Constants.SOLR_BASE_URL);
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
    
    @Test(expected = SearchManagerException.class)
    public void reachabilityCheckWithNotReachableServer() throws SearchManagerException {
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL("WRONG_BASE_URL");
        Assert.assertNotNull(solrSearchManager);
        solrSearchManager.isReachable();
    }
}
