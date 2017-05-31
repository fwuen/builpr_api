package com.builpr.search.solr;

import com.builpr.search.Order;
import com.builpr.search.Sort;
import com.builpr.search.filter.*;
import lombok.NonNull;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.List;

public class SolrQueryFactory {

    public SolrQuery getQueryWith(
            @NonNull String term,
            @NonNull List<Filter> filter,
            @NonNull Sort sort,
            @NonNull Order order
    ) {
        SolrQuery query = new SolrQuery();
        query.setQuery(term);
        // TODO: Sortierung
        for (Filter f : filter) {
            if (f instanceof MinimumRatingFilter) {
                // TODO: überdenken/überarbeiten
                query.addNumericRangeFacet("rating", ((MinimumRatingFilter) f).getMinimumRating(), 5, 1.0);
            } else if (f instanceof CategoryFilter) {
                // TODO: Tagfilterung
            }
        }
        return query;
    }

    public SolrQuery getQueryFindAll() {
        return new SolrQuery("*:*");
    }

}
