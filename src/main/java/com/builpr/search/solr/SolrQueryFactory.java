package com.builpr.search.solr;

import com.builpr.search.Order;
import com.builpr.search.filter.Filter;
import lombok.NonNull;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.List;

public class SolrQueryFactory {

    public SolrQuery getQueryWith(
            @NonNull String term,
            @NonNull List<Filter> filter,
            @NonNull Order order
    ) {
        return null; /* TODO: Build tha Query here. */
    }

    public SolrQuery getQueryFindAll() {
        return new SolrQuery("*:*");
    }

}
