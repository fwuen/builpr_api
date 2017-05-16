package com.builpr.search.solr;

import com.builpr.search.Order;
import com.builpr.search.filter.*;
import lombok.NonNull;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.List;

public class SolrQueryFactory {

    /**
     *
     * @param term
     * @param filter
     * @param order
     * @return
     */
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
                query.addNumericRangeFacet("rating", ((MinimumRatingFilter) f).getMinimumRating(), 5, 0.5);
            } else if (f instanceof TagFilter) {
                // TODO: Tagfilterung
            } else if (f instanceof FileFilter) {
                // TODO: Dateiformatfilterung
            }
        }
        return query; /* TODO: Build tha Query here. */
    }

    /**
     *
     * @return
     */
    public SolrQuery getQueryFindAll() {
        return new SolrQuery("*:*");
    }

}
