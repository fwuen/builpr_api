package com.builpr.restapi.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface SolrModelRepository extends SolrCrudRepository{
    Page findByTitleAndDescription(String searchTerm, Pageable page);
}
