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
    public void reachabilityCheck() {
        SolrClient solr = new HttpSolrClient.Builder(REMOTE_BASE_URL).build();
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithSolrClient(solr);
        
    }

    /* TODO: Teste die Methoden auf ihre Funktionalität */

}
