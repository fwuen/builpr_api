package com.builpr.restapi.controller;

import com.builpr.Constants;
import com.builpr.restapi.error.rating.RatingDeleteError;
import com.builpr.restapi.error.rating.RatingNewError;
import com.builpr.restapi.model.Request.Rating.RatingDeleteRequest;
import com.builpr.restapi.model.Request.Rating.RatingNewRequest;
import com.builpr.restapi.model.Response.Response;
import com.builpr.restapi.model.Response.rating.RatingPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * RatingControllerTest
 */
public class RatingControllerTest extends ControllerTest {
    private static final String DB_TEST_USER = "DB_test_user";
    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    @WithMockUser(DB_TEST_USER)
    public void createRating() throws Exception {
        RatingNewRequest ratingNewRequest = new RatingNewRequest();
        ratingNewRequest.setPrintableID(236);
        ratingNewRequest.setText("Testbewertung");
        ratingNewRequest.setRating(5);
        MvcResult result = mockMvc.perform(
                post(Constants.URL_NEW_RATING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ratingNewRequest)))
                .andExpect(status().isOk())
                .andReturn();
        Response<RatingPayload, RatingNewError> resonse = getResponseBodyOf(result, Response.class);

        Assert.assertNotNull(resonse);
        Assert.assertTrue(resonse.isSuccess());
        Assert.assertTrue(resonse.getErrorList().isEmpty());

    }

    @Test
    @WithMockUser(DB_TEST_USER)
    public void deleteRating() throws Exception {
        RatingDeleteRequest request = new RatingDeleteRequest();
        request.setRatingID(5);
        request.setConfirmation(true);

        MvcResult result = mockMvc.perform(
                delete(Constants.URL_DELETE_RATING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();
        Response<RatingPayload, RatingDeleteError> resonse = getResponseBodyOf(result, Response.class);


    }
}
