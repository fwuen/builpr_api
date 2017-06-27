package com.builpr.restapi.controller;

import com.builpr.database.bridge.printable.Printable;
import com.builpr.restapi.converter.PrintableReferenceToPrintableConverter;
import com.builpr.restapi.error.search.SearchError;
import com.builpr.restapi.model.Request.Search.SearchPayload;
import com.builpr.restapi.model.Response.Response;
import com.builpr.restapi.model.Response.Search.SearchResponse;
import com.builpr.restapi.utils.CategoryValidator;
import com.builpr.restapi.utils.PrintableSolrHelper;
import com.builpr.search.ORDER;
import com.builpr.search.SORT;
import com.builpr.search.SearchManagerException;
import com.builpr.search.filter.CategoryFilter;
import com.builpr.search.filter.Filter;
import com.builpr.search.filter.MinimumRatingFilter;
import com.builpr.search.model.PrintableReference;
import com.builpr.search.solr.SolrSearchManager;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.builpr.Constants.*;

/**
 * SearchController
 */
@RestController
public class SearchController {
    private CategoryValidator categoryValidator;
    private PrintableReferenceToPrintableConverter converter;

    public SearchController() {
        categoryValidator = new CategoryValidator();
        converter = new PrintableReferenceToPrintableConverter();
    }

    @CrossOrigin(origins = SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = URL_SEARCH, method = RequestMethod.GET)
    @ResponseBody
    public Response<SearchResponse> search(@RequestBody SearchPayload request) throws SearchManagerException {
        Response<SearchResponse> response = new Response<>();
        if (request.getQuery().isEmpty()) {
            response.setSuccess(false);
            response.addError(SearchError.INVALID_QUERY);
        }
        if (request.getMinimumRatingFilter() < 1 || request.getMinimumRatingFilter() > 5) {
            response.setSuccess(false);
            response.addError(SearchError.INVALID_RATING_FILTER);
        }
        request.setCategories(categoryValidator.checkCategories(request.getCategories()));
        if (!Objects.equals(request.getOrder(), "asc")
                && !Objects.equals(request.getOrder(), "desc")
                && request.getOrder() != null) {
            response.setSuccess(false);
            response.addError(SearchError.INVALID_ORDER);
        }
        if (!Objects.equals(request.getSort(), "relevance")
                && !Objects.equals(request.getSort(), "rating")
                && !Objects.equals(request.getSort(), "alphabetical")
                && !Objects.equals(request.getSort(), "downloads")
                && request.getSort() != null) {
            response.setSuccess(false);
            response.addError(SearchError.INVALID_SORT);
        }

        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setSort(request.getSort());
        searchResponse.setOrder(request.getOrder());
        searchResponse.setQuery(request.getQuery());
        searchResponse.setCategories(request.getCategories());
        searchResponse.setMinimumRatingFilter(request.getMinimumRatingFilter());

        if (!response.isSuccess()) {
            response.setPayload(searchResponse);
            return response;
        }

        PrintableSolrHelper printableSolrHelper = new PrintableSolrHelper();
        printableSolrHelper.indexPrintables();

        List<Filter> filter = new ArrayList<>();
        ORDER order = null;
        SORT sort = null;
        if (request.getOrder() != null) {
            order = ORDER.valueOf(request.getOrder().toUpperCase());
        }
        if (request.getSort() != null) {
            sort = SORT.valueOf(request.getSort().toUpperCase());
        }
        if (request.getMinimumRatingFilter() != 0) {
            Filter ratingFiler = new MinimumRatingFilter(request.getMinimumRatingFilter());
            filter.add(ratingFiler);
        }
        if (request.getCategories() != null || !request.getCategories().isEmpty()) {
            Filter categoryFilter = new CategoryFilter(request.getCategories());
            filter.add(categoryFilter);
        }
        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SOLR_BASE_URL);
        List<PrintableReference> foundPrintable;

        if (sort == null && order == null && filter.isEmpty()) {
            foundPrintable = solrSearchManager.search(request.getQuery());
        } else if (sort == null && order == null) {
            foundPrintable = solrSearchManager.search(request.getQuery(), filter);
        } else if (order == null && filter.isEmpty()) {
            foundPrintable = solrSearchManager.search(request.getQuery(), sort);
        } else if (filter.isEmpty()) {
            foundPrintable = solrSearchManager.search(request.getQuery(), sort, order);
        } else if (order == null) {
            foundPrintable = solrSearchManager.search(request.getQuery(), filter, sort);
        } else {
            foundPrintable = solrSearchManager.search(request.getQuery(), filter, sort, order);
        }


        if (foundPrintable.isEmpty()) {
            searchResponse.setResults(null);
        } else {

            List<Printable> printableList = converter.getPrintableList(foundPrintable);
            searchResponse.setResults(printableList);
        }
        response.setPayload(searchResponse);

        return response;
    }

}
