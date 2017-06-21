package com.builpr.restapi.controller;

import com.builpr.restapi.error.search.SearchError;
import com.builpr.restapi.model.Request.Search.SearchPayload;
import com.builpr.restapi.model.Response.Response;
import com.builpr.restapi.utils.PrintableSolrHelper;
import com.builpr.search.SearchManagerException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static com.builpr.Constants.URL_SEARCH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * SearchControllerTest
 */
public class SearchControllerTest extends ControllerTest {

    private static ObjectMapper mapper = new ObjectMapper();

    private static final String VALID_QUERY = "test";
    private static final String INVALID_QUERY = "";

    private static final int VALID_RATING = 5;
    private static final int TOO_BIG_RATING = 10;
    private static final int TOO_SHORT_RATING = 0;

    private List<String> VALID_CATEGORY_FILTER = new ArrayList<>();
    private List<String> INVALID_CATEGORY_FILTER = new ArrayList<>();

    private static final String VALID_ORDER = "desc";
    private static final String INVALID_ORDER = "adesc";

    private static final String VALID_SORT = "relevance";
    private static final String INVALID_SORT = "abcdefg";

    private SearchPayload searchRequest;
    private PrintableSolrHelper printableSolrHelper;

    public void fillCategoryFilter() {
        VALID_CATEGORY_FILTER.add("test");
        INVALID_CATEGORY_FILTER.add("");
    }

    public SearchControllerTest() throws SearchManagerException {
        fillCategoryFilter();
        searchRequest = new SearchPayload();
        searchRequest.setQuery(VALID_QUERY);
        searchRequest.setMinimumRatingFilter(VALID_RATING);
        searchRequest.setCategories(VALID_CATEGORY_FILTER);
        searchRequest.setOrder(VALID_ORDER);
        searchRequest.setSort(VALID_SORT);

        printableSolrHelper = new PrintableSolrHelper();
        printableSolrHelper.indexPrintables();
    }

    @Test
    public void searchWithValidInput() throws Exception {

        MvcResult result = mockMvc.perform(
                get(URL_SEARCH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().isEmpty());
    }

    @Test
    public void searchWithInvalidQuery() throws Exception {
        searchRequest.setQuery(INVALID_QUERY);

        MvcResult result = mockMvc.perform(
                get(URL_SEARCH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertTrue(!response.isSuccess());
        Assert.assertNotNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().containsKey(SearchError.INVALID_QUERY.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(SearchError.INVALID_QUERY.getDescription()));
        Assert.assertEquals(1, response.getErrorMap().size());
    }

    @Test
    public void searchWithInvalidRating() throws Exception {
        searchRequest.setMinimumRatingFilter(TOO_BIG_RATING);

        MvcResult resultTooBig = mockMvc.perform(
                get(URL_SEARCH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response responseTooBig = getResponseBodyOf(resultTooBig, Response.class);

        Assert.assertTrue(!responseTooBig.isSuccess());
        Assert.assertNotNull(responseTooBig.getPayload());
        Assert.assertTrue(responseTooBig.getErrorMap().containsKey(SearchError.INVALID_RATING_FILTER.getCode()));
        Assert.assertTrue(responseTooBig.getErrorMap().containsValue(SearchError.INVALID_RATING_FILTER.getDescription()));
        Assert.assertEquals(1, responseTooBig.getErrorMap().size());

        searchRequest.setMinimumRatingFilter(TOO_SHORT_RATING);

        MvcResult result = mockMvc.perform(
                get(URL_SEARCH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response responseTooShort = getResponseBodyOf(result, Response.class);

        Assert.assertTrue(!responseTooShort.isSuccess());
        Assert.assertNotNull(responseTooShort.getPayload());
        Assert.assertTrue(responseTooShort.getErrorMap().containsKey(SearchError.INVALID_RATING_FILTER.getCode()));
        Assert.assertTrue(responseTooShort.getErrorMap().containsValue(SearchError.INVALID_RATING_FILTER.getDescription()));
        Assert.assertEquals(1, responseTooShort.getErrorMap().size());
    }

    @Test
    public void searchWithInvalidCategoryFilter() throws Exception {
        searchRequest.setCategories(INVALID_CATEGORY_FILTER);

        MvcResult result = mockMvc.perform(
                get(URL_SEARCH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getPayload());
        Assert.assertEquals(0, response.getErrorMap().size());
    }

    @Test
    public void searchWithInvalidOrder() throws Exception {
        searchRequest.setOrder(INVALID_ORDER);

        MvcResult result = mockMvc.perform(
                get(URL_SEARCH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertTrue(!response.isSuccess());
        Assert.assertNotNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().containsKey(SearchError.INVALID_ORDER.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(SearchError.INVALID_ORDER.getDescription()));
        Assert.assertEquals(1, response.getErrorMap().size());
    }

    @Test
    public void searchWithInvalidSort() throws Exception {
        searchRequest.setSort(INVALID_SORT);

        MvcResult result = mockMvc.perform(
                get(URL_SEARCH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertTrue(!response.isSuccess());
        Assert.assertNotNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().containsKey(SearchError.INVALID_SORT.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(SearchError.INVALID_SORT.getDescription()));
        Assert.assertEquals(1, response.getErrorMap().size());
    }
}
