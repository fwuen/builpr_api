package com.builpr.restapi.controller;

import com.builpr.restapi.error.search.SearchError;
import com.builpr.restapi.model.Response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;

import static com.builpr.Constants.URL_SEARCH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * SearchControllerTest
 */
public class SearchControllerTest extends ControllerTest {

    private static final String VALID_QUERY = "domi";
    private static final String INVALID_QUERY = "";

    private static final String VALID_RATING = "0";
    private static final String TOO_BIG_RATING = "10";
    private static final String TOO_SHORT_RATING = "-5";

    private static final String VALID_ORDER = "desc";
    private static final String INVALID_ORDER = "adesc";

    private static final String VALID_SORT = "relevance";
    private static final String INVALID_SORT = "abcdefg";


    @Test
    public void searchWithValidInput() throws Exception {
        MvcResult result = mockMvc.perform(
                get(URL_SEARCH)
                        .param("query", VALID_QUERY)
                        .param("minimumRatingFilter", VALID_RATING)
                        .param("order", VALID_ORDER)
                        .param("sort", VALID_SORT)
                        .param("categories", "test,neuertesttag")
        )
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().isEmpty());
    }

    @Test
    public void searchWithInvalidQuery() throws Exception {
        MvcResult result = mockMvc.perform(
                get(URL_SEARCH)
                        .param("query", INVALID_QUERY)
                        .param("minimumRatingFilter", VALID_RATING)
                        .param("order", VALID_ORDER)
                        .param("sort", VALID_SORT)
                        .param("categories", "test,neuertesttag")
        )
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
        MvcResult result = mockMvc.perform(
                get(URL_SEARCH)
                        .param("query", VALID_QUERY)
                        .param("minimumRatingFilter", TOO_BIG_RATING)
                        .param("order", VALID_ORDER)
                        .param("sort", VALID_SORT)
                        .param("categories", "test,neuertesttag")
        )
                .andExpect(status().isOk())
                .andReturn();

        Response responseTooBig = getResponseBodyOf(result, Response.class);

        Assert.assertTrue(!responseTooBig.isSuccess());
        Assert.assertNotNull(responseTooBig.getPayload());
        Assert.assertTrue(responseTooBig.getErrorMap().containsKey(SearchError.INVALID_RATING_FILTER.getCode()));
        Assert.assertTrue(responseTooBig.getErrorMap().containsValue(SearchError.INVALID_RATING_FILTER.getDescription()));
        Assert.assertEquals(1, responseTooBig.getErrorMap().size());


        MvcResult result2 = mockMvc.perform(
                get(URL_SEARCH)
                        .param("query", VALID_QUERY)
                        .param("minimumRatingFilter", TOO_SHORT_RATING)
                        .param("order", VALID_ORDER)
                        .param("sort", VALID_SORT)
                        .param("categories", "test,neuertesttag")
        )
                .andExpect(status().isOk())
                .andReturn();

        Response responseTooShort = getResponseBodyOf(result2, Response.class);

        Assert.assertTrue(!responseTooShort.isSuccess());
        Assert.assertNotNull(responseTooShort.getPayload());
        Assert.assertTrue(responseTooShort.getErrorMap().containsKey(SearchError.INVALID_RATING_FILTER.getCode()));
        Assert.assertTrue(responseTooShort.getErrorMap().containsValue(SearchError.INVALID_RATING_FILTER.getDescription()));
        Assert.assertEquals(1, responseTooShort.getErrorMap().size());
    }

    @Test
    public void searchWithInvalidCategoryFilter() throws Exception {
        MvcResult result = mockMvc.perform(
                get(URL_SEARCH)
                        .param("query", VALID_QUERY)
                        .param("minimumRatingFilter", VALID_RATING)
                        .param("order", VALID_ORDER)
                        .param("sort", VALID_SORT)
                        .param("categories", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa,test")
        )
                .andExpect(status().isOk())
                .andReturn();
        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getPayload());
        Assert.assertEquals(0, response.getErrorMap().size());
    }

    @Test
    public void searchWithInvalidOrder() throws Exception {
        MvcResult result = mockMvc.perform(
                get(URL_SEARCH)
                        .param("query", VALID_QUERY)
                        .param("minimumRatingFilter", VALID_RATING)
                        .param("order", INVALID_ORDER)
                        .param("sort", VALID_SORT)
                        .param("categories", "test,neuertesttag")
        )
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
        MvcResult result = mockMvc.perform(
                get(URL_SEARCH)
                        .param("query", VALID_QUERY)
                        .param("minimumRatingFilter", VALID_RATING)
                        .param("order", VALID_ORDER)
                        .param("sort", INVALID_SORT)
                        .param("categories", "test,neuertesttag")
        )
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
