package com.builpr.restapi.configuration;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

import javax.annotation.Resource;

@Configuration
@EnableSolrRepositories(basePackages={"com.acme.solr"}, multicoreSupport = true)
public class SolrConfiguration {

    //TODO: Tests über Spring/Gradle ausführen (sonst wird Spring nicht ausgeführt)
    //TODO: Server-Property auslagern
    /*
    @Value("solr.host")
    private String solr_host;
    */

    //TODO: baseURL-Parameter mit private-Attribut solr_host ersetzen
    @Bean
    public SolrClient solrClient() {
        return new HttpSolrClient("localhost");
    }
    
}
