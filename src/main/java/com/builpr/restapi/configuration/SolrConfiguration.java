package com.builpr.restapi.configuration;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

import javax.annotation.Resource;

@Configuration
@EnableSolrRepositories(basePackages={"com.acme.solr"}, multicoreSupport = true)
public class SolrConfiguration {
    
    static final String SOLR_HOST = "localhost";
    
    @Resource
    private Environment environment;
    
    @Bean
    public SolrClient solrClient() {
        String solrHost = environment.getRequiredProperty(SOLR_HOST);
        return new HttpSolrClient(solrHost);
    }
    
}
