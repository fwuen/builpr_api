package com.builpr.restapi.controller;

import com.builpr.restapi.converter.PrintableReferenceToPrintableConverter;
import com.builpr.restapi.error.search.SearchError;
import com.builpr.restapi.model.Response.Response;
import com.builpr.restapi.model.Response.Search.SearchResponse;
import com.builpr.restapi.model.Response.printable.PrintablePayload;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.builpr.Constants.*;

/**
 * @author Markus Goller
 *
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

    /**
     * Handle the input from the frontend for the solr search
     * Check if the input is valid and choose the right method
     * Handle and return the results given from the solr search
     *
     * @param query               String
     * @param minimumRatingFilter String
     * @param order               String
     * @param sort                String
     * @param categoriesAsString  String
     * @return {Response<SearchResponse>}
     * @throws SearchManagerException Exception
     */
    @CrossOrigin(origins = SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = URL_SEARCH, method = RequestMethod.GET)
    @ResponseBody
    public Response<SearchResponse> search(
            @RequestParam(value = "query", required = true) String query,
            @RequestParam(value = "minimumRatingFilter", defaultValue = "0", required = false) int minimumRatingFilter,
            @RequestParam(value = "order", required = false) String order,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "categories", required = false) String categoriesAsString) throws SearchManagerException {

        Response<SearchResponse> response = new Response<>();
        List<String> categoryList = null;

        if (categoriesAsString != null) {
            categoryList = new ArrayList<>(Arrays.asList(categoriesAsString.split(",")));
        }
        if (query.isEmpty()) {
            response.setSuccess(false);
            response.addError(SearchError.INVALID_QUERY);
        }
        if (minimumRatingFilter < 0 || minimumRatingFilter > 5) {
            response.setSuccess(false);
            response.addError(SearchError.INVALID_RATING_FILTER);
        }
        if (categoryList != null) {
            categoryList = categoryValidator.checkCategories(categoryList);
        }
        if (!Objects.equals(order, "asc")
                && !Objects.equals(order, "desc")
                && order != null) {
            response.setSuccess(false);
            response.addError(SearchError.INVALID_ORDER);
        }
        if (!Objects.equals(sort, "relevance")
                && !Objects.equals(sort, "rating")
                && !Objects.equals(sort, "alphabetical")
                && !Objects.equals(sort, "downloads")
                && sort != null) {
            response.setSuccess(false);
            response.addError(SearchError.INVALID_SORT);
        }

        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setSort(sort);
        searchResponse.setOrder(order);
        searchResponse.setQuery(query);
        searchResponse.setCategories(categoryList);
        searchResponse.setMinimumRatingFilter(minimumRatingFilter);

        if (!response.isSuccess()) {
            response.setPayload(searchResponse);
            return response;
        }

        PrintableSolrHelper printableSolrHelper = new PrintableSolrHelper();
        printableSolrHelper.indexPrintables();

        List<Filter> filter = new ArrayList<>();
        ORDER order_ = null;
        SORT sort_ = null;

        if (order != null) {
            order_ = ORDER.valueOf(order.toUpperCase());
        }
        if (sort != null) {
            sort_ = SORT.valueOf(sort.toUpperCase());
        }
        if (minimumRatingFilter != 0) {
            Filter ratingFiler = new MinimumRatingFilter(minimumRatingFilter);
            filter.add(ratingFiler);
        }
        if (categoryList != null) {
            Filter categoryFilter = new CategoryFilter(categoryList);
            filter.add(categoryFilter);
        }

        SolrSearchManager solrSearchManager = SolrSearchManager.createWithBaseURL(SOLR_BASE_URL);
        List<PrintableReference> foundPrintable;

        if (sort == null && order == null && filter.isEmpty()) {
            foundPrintable = solrSearchManager.search(query);
        } else if (sort == null && order == null) {
            foundPrintable = solrSearchManager.search(query, filter);
        } else if (order == null && filter.isEmpty()) {
            foundPrintable = solrSearchManager.search(query, sort_);
        } else if (filter.isEmpty()) {
            foundPrintable = solrSearchManager.search(query, sort_, order_);
        } else if (order == null) {
            foundPrintable = solrSearchManager.search(query, filter, sort_);
        } else {
            foundPrintable = solrSearchManager.search(query, filter, sort_, order_);
        }

        if (foundPrintable.isEmpty()) {
            searchResponse.setResults(null);
        } else {

            List<PrintablePayload> printableList = converter.getPrintableList(foundPrintable);
            searchResponse.setResults(printableList);
        }
        response.setPayload(searchResponse);

        return response;
    }

}
