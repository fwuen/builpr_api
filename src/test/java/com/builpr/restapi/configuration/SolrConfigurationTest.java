package com.builpr.restapi.configuration;

import org.apache.solr.client.solrj.SolrClient;
import org.junit.Assert;
import org.junit.Test;

public class SolrConfigurationTest {
    @Test
    public void createSolrClient() {
        SolrClient sc = new SolrConfiguration().solrClient();
        Assert.assertNotNull(sc);
    }
}