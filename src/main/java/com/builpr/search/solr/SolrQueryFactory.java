package com.builpr.search.solr;

import com.builpr.search.ORDER;
import com.builpr.search.SORT;
import com.builpr.search.filter.*;
import lombok.NonNull;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SolrQueryFactory {

    public SolrQuery getQueryWith(
            @NonNull String term,
            @NonNull List<Filter> filters,
            @NonNull SORT sort,
            @NonNull ORDER order
    ) {
        SolrQuery query = new SolrQuery();

        query.setQuery(buildQueryString(term));

        for (Filter filter : filters) {
            if (filter instanceof MinimumRatingFilter) {
                // TODO MinimumRatingFilter geht noch nicht
                query.addNumericRangeFacet("rating", ((MinimumRatingFilter) filter).getMinimumRating(), MinimumRatingFilter.HIGHEST_POSSIBLE_RATING, 1.0);
            } else if (filter instanceof CategoryFilter) {
                CategoryFilter concreteFilter = (CategoryFilter) filter;
                for (String category : concreteFilter.getCategories())
                    query.addFilterQuery(SolrFields.PRINT_MODEL_CATEGORIES + ":" + category);
            }
        }

        // Solr automatically sorts by relevance if no other field is determined
        if (sort != SORT.RELEVANCE) {
            query.addSort(
                    SolrEnumMapper.enumToSolrEnum(sort).toString(),
                    SolrEnumMapper.enumToSolrEnum(order)
            );
        }

        query.setFields("id");

        System.err.println(query.toQueryString());
        return query;
    }

    private String buildQueryString(String term) {
        StringBuilder builder = new StringBuilder();
        term = term.toLowerCase();
        term = term.trim();
        term = term.replaceAll("(\\s)(\\s)+", " ");
        ArrayList<String> terms = new ArrayList(Arrays.asList(term.split(" ")));

        for(String t : terms)
        {
            builder.append(SolrFields.PRINT_MODEL_TITLE + ":*" + t + "*");
            builder.append(" OR ");
            builder.append(SolrFields.PRINT_MODEL_DESCRIPTION + ":*" + t + "*");

            if (terms.indexOf(t) < terms.size() - 1)
            {
                builder.append(" OR ");
            }
        }

        return builder.toString();
    }

    public SolrQuery getQueryFindAll() {
        return new SolrQuery("*:*");
    }

}
