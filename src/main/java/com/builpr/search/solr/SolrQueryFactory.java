package com.builpr.search.solr;

import com.builpr.search.Order;
import com.builpr.search.filter.Filter;
import com.builpr.search.filter.MinimumRatingFilter;
import com.builpr.search.filter.PriceRangeFilter;
import com.builpr.search.filter.TagFilter;
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
                query.addNumericRangeFacet("rating", ((MinimumRatingFilter) f).getMinimumRating(), 5, 0.5);
            } else if (f instanceof PriceRangeFilter) {
                query.addNumericRangeFacet("price", ((PriceRangeFilter) f).getFromPrice(), ((PriceRangeFilter) f).getToPrice(), 5);
            } else if (f instanceof TagFilter) {
            
            }
        }
        return query; /* TODO: Build tha Query here. */
    }

    public SolrQuery getQueryFindAll() {
        return new SolrQuery("*:*");
    }

}
