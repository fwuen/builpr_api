package com.builpr.restapi.model.Response.Search;

import com.builpr.restapi.model.Request.Search.SearchPayload;
import com.builpr.search.model.Printable;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Search Response
 */
public class SearchResponse extends SearchPayload {

    /**
     * serach results
     */
    @Getter
    @Setter
    private List<Printable> results;
}
