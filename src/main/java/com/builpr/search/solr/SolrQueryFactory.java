package com.builpr.search.solr;

import com.builpr.search.ORDER;
import com.builpr.search.SORT;
import com.builpr.search.filter.*;
import lombok.NonNull;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.List;

public class SolrQueryFactory {

    public SolrQuery getQueryWith(
            @NonNull String term,
            @NonNull List<Filter> filter,
            @NonNull SORT sort,
            @NonNull ORDER order
    ) {
        SolrQuery query = new SolrQuery();

        query.setQuery(term);

        for (Filter f : filter) {
            if (f instanceof MinimumRatingFilter)
            {
                // TODO: überdenken/überarbeiten
                query.addNumericRangeFacet("rating", ((MinimumRatingFilter) f).getMinimumRating(), 5, 1.0);
            }
            else if (f instanceof CategoryFilter)
            {
                CategoryFilter concreteFilter = (CategoryFilter) f;

                for(String category : concreteFilter.getCategories())
                query.addFilterQuery(SolrFields.PRINT_MODEL_CATEGORIES + ":" + category);
            }
        }

        // TODO: Wie wird Sortieren nach Relevanz umgesetzt? Relevanz als SolrField? Ist für jede Abfrage anders!
        query.addSort(
                SolrEnumMapper.enumToSolrEnum(sort).toString(),
                SolrEnumMapper.enumToSolrEnum(order)
        );

        return query;
    }

    public SolrQuery getQueryFindAll() {
        return new SolrQuery("*:*");
    }

}
