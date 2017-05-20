package com.builpr.search.solr;

import com.builpr.search.Order;
import com.builpr.search.filter.*;
import lombok.NonNull;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.List;

public class SolrQueryFactory {

    public SolrQuery getQueryWith(
            @NonNull String term,
            @NonNull List<Filter> filter,
            @NonNull Order order
    ) {
        SolrQuery query = new SolrQuery();
        query.setQuery(term);
        // TODO: Sortierung
        for (Filter f : filter) {
            if (f instanceof MinimumRatingFilter) {
                // TODO: überdenken/überarbeiten
                query.addNumericRangeFacet("rating", ((MinimumRatingFilter) f).getMinimumRating(), MinimumRatingFilter.HIGHEST_POSSIBLE_RATING, 1.0);
            } else if (f instanceof TagFilter) {
                // TODO: Tagfilterung
            } else if (f instanceof FileFilter) {
                // TODO: Dateiformatfilterung
            }
        }
        return query;
    }

    public SolrQuery getQueryFindAll() {
        return new SolrQuery("*:*");
    }

}
