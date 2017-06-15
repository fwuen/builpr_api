package com.builpr.restapi.controller;

import com.builpr.Constants;
import com.builpr.database.bridge.printable.PrintableImpl;
import com.builpr.database.bridge.rating.RatingImpl;
import com.builpr.database.bridge.user.User;
import com.builpr.database.bridge.user.UserImpl;
import com.builpr.database.service.DatabasePrintableManager;
import com.builpr.database.service.DatabaseRatingManager;
import com.builpr.database.service.DatabaseUserManager;
import com.builpr.restapi.error.rating.RatingDeleteError;
import com.builpr.restapi.error.rating.RatingNewError;
import com.builpr.restapi.model.Request.Rating.RatingDeleteRequest;
import com.builpr.restapi.model.Request.Rating.RatingNewRequest;
import com.builpr.restapi.model.Response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private static final String DB_TEST_USER = "DB_printable_test";
    private static ObjectMapper mapper = new ObjectMapper();
    private static DatabaseUserManager databaseUserManager = new DatabaseUserManager();

    @BeforeClass
    public static void setTestUp() {

        // User anlegen
        User user = new UserImpl()
                .setUsername(DB_TEST_USER)
                .setEmail("test_user@mail.de")
                .setPassword(new BCryptPasswordEncoder().encode("password"))
                .setBirthday(new Date(System.currentTimeMillis() - 1231231))
                .setFirstname("Test")
                .setLastname("User")
                .setShowBirthday(true)
                .setShowEmail(true)
                .setShowName(true);

        if (databaseUserManager.isPresent(DB_TEST_USER)) {
            databaseUserManager.deleteByUsername(DB_TEST_USER);
        }
        databaseUserManager.persist(user);

        DatabaseUserManager userManager = new DatabaseUserManager();
        DatabasePrintableManager databasePrintableManager = new DatabasePrintableManager();
        PrintableImpl printable = new PrintableImpl();
        printable.setFilePath("testPath");
        printable.setUploaderId(userManager.getByUsername(DB_TEST_USER).getUserId());
        printable.setDescription("testDescription");
        printable.setTitle("testTitle");
        printable.setPrintableId(123456789);
        databasePrintableManager.persist(printable);
    }

    @AfterClass
    public static void clearDatabase() {
        if (databaseUserManager.isPresent(DB_TEST_USER)) {
            databaseUserManager.deleteByUsername(DB_TEST_USER);
        }
    }

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

        if (databaseRatingManager.isPresent(123456789)) {
            databaseRatingManager.deleteRatingByID(123456789);
        }

        databaseRatingManager.persist(rating);
    }

    //-----------------------------------------------------------------------------------------------------------//
    //                      /rating/new                                                                          //
    //-----------------------------------------------------------------------------------------------------------//
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
        // TODO Rating wieder löschen
    }

    @Test
    @WithMockUser(DB_TEST_USER)
    public void createRatingWithInvalidRating() throws Exception {
        RatingNewRequest ratingNewRequest = new RatingNewRequest();
        ratingNewRequest.setPrintableID(123456789);
        ratingNewRequest.setText("TestbewertungInvalidRating");
        ratingNewRequest.setRating(10);
        MvcResult result = mockMvc.perform(
                post(Constants.URL_NEW_RATING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ratingNewRequest)))
                .andExpect(status().isOk())
                .andReturn();
        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertTrue(!response.isSuccess());
        Assert.assertTrue(!response.getErrorMap().isEmpty());
        Assert.assertTrue(response.getErrorMap().containsKey(RatingNewError.RATING_INVALID.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(RatingNewError.RATING_INVALID.getDescription()));
    }

    @Test
    @WithMockUser(DB_TEST_USER)
    public void createRatingWithInvaldiPrintable() throws Exception {
        RatingNewRequest ratingNewRequest = new RatingNewRequest();
        ratingNewRequest.setPrintableID(0);
        ratingNewRequest.setText("TestbewertungInvalidPrintable");
        ratingNewRequest.setRating(5);
        MvcResult result = mockMvc.perform(
                post(Constants.URL_NEW_RATING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ratingNewRequest)))
                .andExpect(status().isOk())
                .andReturn();
        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertTrue(!response.isSuccess());
        Assert.assertTrue(!response.getErrorMap().isEmpty());
        Assert.assertTrue(response.getErrorMap().containsKey(RatingNewError.PRINTABLE_NOT_EXISTING.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(RatingNewError.PRINTABLE_NOT_EXISTING.getDescription()));
    }

    @Test
    @WithMockUser(DB_TEST_USER)
    public void createRatingWithInvalidText() throws Exception {
        RatingNewRequest ratingNewRequest = new RatingNewRequest();
        ratingNewRequest.setPrintableID(123456789);
        ratingNewRequest.setText("");
        ratingNewRequest.setRating(5);
        MvcResult result = mockMvc.perform(
                post(Constants.URL_NEW_RATING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ratingNewRequest)))
                .andExpect(status().isOk())
                .andReturn();
        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertTrue(!response.isSuccess());
        Assert.assertTrue(!response.getErrorMap().isEmpty());
        Assert.assertTrue(response.getErrorMap().containsKey(RatingNewError.TEXT_INVALID.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(RatingNewError.TEXT_INVALID.getDescription()));
    }

    @Test
    @WithMockUser("fake")
    public void createRatingWithoutAuthorization() throws Exception {
        RatingNewRequest ratingNewRequest = new RatingNewRequest();
        ratingNewRequest.setPrintableID(123456789);
        ratingNewRequest.setText("TestbewertungNoAccess");
        ratingNewRequest.setRating(5);
        MvcResult result = mockMvc.perform(
                post(Constants.URL_NEW_RATING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ratingNewRequest)))
                .andExpect(status().isOk())
                .andReturn();
        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertTrue(!response.isSuccess());
        Assert.assertTrue(!response.getErrorMap().isEmpty());
        Assert.assertTrue(response.getErrorMap().containsKey(RatingNewError.NO_AUTHORIZATION.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(RatingNewError.NO_AUTHORIZATION.getDescription()));
    }

    @Test
    public void createRatingWithInvalidUser() throws Exception {
        RatingNewRequest ratingNewRequest = new RatingNewRequest();
        ratingNewRequest.setPrintableID(123456789);
        ratingNewRequest.setText("Testbewertung");
        ratingNewRequest.setRating(5);
        mockMvc.perform(
                post(Constants.URL_NEW_RATING)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(ratingNewRequest)))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(TEST_USER_NAME)
    public void createAlreadyExistingRating() throws Exception {
        setTestRatingUp();

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

        Assert.assertTrue(!response.isSuccess());
        Assert.assertTrue(!response.getErrorMap().isEmpty());
        Assert.assertTrue(response.getErrorMap().containsKey(RatingNewError.RATING_ALREADY_EXISTS.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(RatingNewError.RATING_ALREADY_EXISTS.getDescription()));
    }

    //-----------------------------------------------------------------------------------------------------------//
    //                      /rating/delete                                                                       //
    //-----------------------------------------------------------------------------------------------------------//
    @Test
    @WithMockUser(TEST_USER_NAME)
    public void deleteRating() throws Exception {
        setTestRatingUp();
        RatingDeleteRequest request = new RatingDeleteRequest();
        request.setRatingID(123456789);
        request.setConfirmation(true);

        MvcResult result = mockMvc.perform(
                delete(Constants.URL_DELETE_RATING).param("id", "123456789"))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertNotNull(response);
        Assert.assertTrue(response.isSuccess());
        Assert.assertTrue(response.getErrorMap().isEmpty());
    }

    @Test
    @WithMockUser(DB_TEST_USER)
    public void deleteRatingWithInvalidID() throws Exception {
        MvcResult result = mockMvc.perform(
                delete(Constants.URL_DELETE_RATING).param("id", "0"))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertNotNull(response);
        Assert.assertTrue(!response.isSuccess());
        Assert.assertTrue(!response.getErrorMap().isEmpty());
        Assert.assertTrue(response.getErrorMap().containsKey(RatingDeleteError.RATING_NOT_FOUND.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(RatingDeleteError.RATING_NOT_FOUND.getDescription()));
    }

    @Test
    public void deleteRatingWithoutUser() throws Exception {
        mockMvc.perform(
                delete(Constants.URL_DELETE_RATING).param("id", "123456789"))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @WithMockUser(DB_TEST_USER)
    public void deleteRatingWithoutAuthorization() throws Exception {
        setTestRatingUp();
         MvcResult result = mockMvc.perform(
                delete(Constants.URL_DELETE_RATING).param("id", "123456789"))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertNotNull(response);
        Assert.assertTrue(!response.isSuccess());
        Assert.assertTrue(!response.getErrorMap().isEmpty());
        Assert.assertTrue(response.getErrorMap().containsKey(RatingDeleteError.NO_AUTHORIZATION.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(RatingDeleteError.NO_AUTHORIZATION.getDescription()));
    }
}
