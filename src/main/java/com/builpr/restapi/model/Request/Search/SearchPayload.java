package com.builpr.restapi.model.Request.Search;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Search-payload
 */
public class SearchPayload {

    /**
     * query with search-term
     */
    @Getter
    @Setter
    private String query;

    /**
     * Value of the minimal rating
     */
    @Getter
    @Setter
    private int minimumRatingFilter;

    /**
     * list of categories
     */
    @Getter
    @Setter
    private List<String> categories;

    /**
     * value of the order of the printables
     */
    @Getter
    @Setter
    private String order;

    /**
     * value of the way the result will be sorted
     */
    @Getter
    @Setter
    private String sort;
}
