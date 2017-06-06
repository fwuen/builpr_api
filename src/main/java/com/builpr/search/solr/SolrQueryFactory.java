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
            @NonNull List<Filter> filters,
            @NonNull SORT sort,
            @NonNull ORDER order
    ) {
        SolrQuery query = new SolrQuery();

        query.setQuery(term);

        for (Filter filter : filters) {
            if (filter instanceof MinimumRatingFilter)
            {
                // TODO: überdenken/überarbeiten
                query.addNumericRangeFacet("rating", ((MinimumRatingFilter) filter).getMinimumRating(), 5, 1.0);
            }
            else if (filter instanceof CategoryFilter)
            {
                CategoryFilter concreteFilter = (CategoryFilter) filter;
                for(String category : concreteFilter.getCategories())
                query.addFilterQuery(SolrFields.PRINT_MODEL_CATEGORIES + ":" + category);
            }
        }
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
