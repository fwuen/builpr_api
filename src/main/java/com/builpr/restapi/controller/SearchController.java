package com.builpr.restapi.controller;

import com.builpr.restapi.error.search.SearchError;
import com.builpr.restapi.model.Request.Search.SearchRequest;
import com.builpr.restapi.model.Response.Response;
import com.builpr.restapi.model.Response.Search.SearchResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * SearchController
 */
@RestController
public class SearchController {

    public SearchController() {

    }

    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    @ResponseBody
    public Response<SearchResponse, SearchError> search(@RequestBody SearchRequest request) {
        Response<SearchResponse, SearchError> response = new Response<>();
        if (request.getQuery().isEmpty()) {
            response.setSuccess(false);

        }
        return response;
    }

}
