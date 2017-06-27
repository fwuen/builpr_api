package com.builpr.restapi.model.Request.Search;


import com.google.common.collect.Lists;
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
    private String query = "";

    /**
     * Value of the minimal rating
     */
    @Getter
    @Setter
    private int minimumRatingFilter = 1;

    /**
     * list of categories
     */
    @Getter
    @Setter
    private List<String> categories = Lists.newArrayList();

    /**
     * value of the order of the printables
     */
    @Getter
    @Setter
    private String order = "desc";

    /**
     * value of the way the result will be sorted
     */
    @Getter
    @Setter
    private String sort = "relevance";


}
