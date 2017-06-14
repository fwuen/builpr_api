package com.builpr.restapi.controller;

import com.builpr.Constants;
import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.printable.PrintableImpl;
import com.builpr.database.bridge.rating.RatingImpl;
import com.builpr.database.service.DatabasePrintableManager;
import com.builpr.database.service.DatabaseRatingManager;
import com.builpr.database.service.DatabaseUserManager;
import com.builpr.restapi.model.Request.Rating.RatingDeleteRequest;
import com.builpr.restapi.model.Request.Rating.RatingNewRequest;
import com.builpr.restapi.model.Response.Response;
import com.builpr.restapi.model.Response.rating.RatingPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import java.sql.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * RatingControllerTest
 */
public class RatingControllerTest extends ControllerTest {
    private static final String DB_TEST_USER = "DB_test_user";
    private static ObjectMapper mapper = new ObjectMapper();


    @BeforeClass
    public static void setTestUp() {
        DatabaseUserManager userManager = new DatabaseUserManager();
        DatabasePrintableManager databasePrintableManager = new DatabasePrintableManager();
        PrintableImpl printable = new PrintableImpl();
        printable.setUploadDate(new Date(System.currentTimeMillis()));
        printable.setFilePath("testPath");
        printable.setUploaderId(userManager.getByUsername(DB_TEST_USER).getUserId());
        printable.setDescription("testDescription");
        printable.setTitle("testTitle");
        printable.setPrintableId(123456789);
        databasePrintableManager.persist(printable);
    }

    @AfterClass
    public static void clearDatabase() {
        DatabasePrintableManager databasePrintableManager = new DatabasePrintableManager();
        databasePrintableManager.deletePrintable(123456789);
    }

    @BeforeClass
    public static void setTestRatingUp() {
        DatabaseUserManager userManager = new DatabaseUserManager();
        DatabaseRatingManager databaseRatingManager = new DatabaseRatingManager();
        RatingImpl rating = new RatingImpl();
        rating.setUserId(userManager.getByUsername("test").getUserId());
        rating.setRatingTime(new Date(System.currentTimeMillis()));
        rating.setRating(5);
        rating.setPrintableId(123456789);
        rating.setMsg("testRating");
        rating.setRatingId(123456789);
        databaseRatingManager.persist(rating);
    }


    @Test
    @WithMockUser(DB_TEST_USER)
    public void createRating() throws Exception {
        RatingNewRequest ratingNewRequest = new RatingNewRequest();
        ratingNewRequest.setPrintableID(123456789);
        ratingNewRequest.setText("Testbewertung");
        ratingNewRequest.setRating(5);
        MvcResult result = mockMvc.perform(
                post(Constants.URL_NEW_RATING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ratingNewRequest)))
                .andExpect(status().isOk())
                .andReturn();
        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertNotNull(response);
        Assert.assertTrue(response.isSuccess());
        Assert.assertTrue(response.getErrorMap().isEmpty());
    }

    @Test
    @WithMockUser(DB_TEST_USER)
    public void deleteRating() throws Exception {
        setTestRatingUp();
        RatingDeleteRequest request = new RatingDeleteRequest();
        request.setRatingID(123456789);
        request.setConfirmation(true);

        MvcResult result = mockMvc.perform(
                delete(Constants.URL_DELETE_RATING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertNotNull(response);
        Assert.assertTrue(response.isSuccess());
        Assert.assertTrue(response.getErrorMap().isEmpty());
    }
}
