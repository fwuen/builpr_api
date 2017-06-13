package com.builpr.restapi.controller;

import com.builpr.database.service.DatabaseUserManager;
import com.builpr.restapi.error.response.account.RegisterError;
import com.builpr.restapi.model.Request.RegisterRequest;
import com.builpr.restapi.model.Response.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;


import static com.builpr.Constants.URL_REGISTER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * tests the RegisterController
 */
public class RegisterControllerTest extends ControllerTest {


    private static final String REGISTER_USERNAME = "test_user";
    private static final String REGISTER_INVALID_USERNAME = "test.user";

    private static final String REGISTER_EMAIL = "test_user@test.de";
    private static final String REGISTER_INVALID_EMAIL = "test_user@de";

    private static final String[] REGISTER_VALID_PASSWD_PAIR = {"test_passwd", "test_passwd"};
    private static final String[] REGISTER_INVALID_PASSWD_PAIR = {"test_passwd", "invalid_test_passwd_pair"};
    private static final String REGISTER_TOO_SHORT_PASSWORD = "short";

    private static final String REGISTER_VALID_DATE = "1990-02-01";
    private static final String REGISTER_INVALID_DATE = "03-10-1990";
    private static final String REGISTER_DATE_IN_FUTURE = "03-10-3000";
    private static final String REGISTER_IMPOSSIBLE_DATE = "41-13-2001";

    private static final String REGISTER_VALID_FIRSTNAME = "Tester";
    private static final String REGISTER_EMPTY_FIRSTNAME = "";

    private static final String REGISTER_VALID_LASTNAME = "Tester";
    private static final String REGISTER_EMPTY_LASTNAME = "";


    private static ObjectMapper mapper = new ObjectMapper();

    private RegisterRequest registerRequest;

    public RegisterControllerTest() {
        registerRequest = new RegisterRequest()
                .setUsername(REGISTER_USERNAME)
                .setEmail(REGISTER_EMAIL)
                .setPassword(REGISTER_VALID_PASSWD_PAIR[0])
                .setPassword2(REGISTER_VALID_PASSWD_PAIR[1])
                .setBirthday(REGISTER_VALID_DATE)
                .setFirstname(REGISTER_VALID_FIRSTNAME)
                .setLastname(REGISTER_VALID_LASTNAME)
                .setShowBirthday(true)
                .setShowName(true)
                .setShowEmail(true);
    }


    @After
    public void setUpDatabase() {
        DatabaseUserManager databaseUserManager = new DatabaseUserManager();

        if (databaseUserManager.isPresent(REGISTER_USERNAME)) {
            databaseUserManager.deleteByUsername(REGISTER_USERNAME);
        }
        if (databaseUserManager.isPresent(REGISTER_INVALID_USERNAME)) {
            databaseUserManager.deleteByUsername(REGISTER_INVALID_USERNAME);
        }
    }

    @Test
    public void registerWithValidInput() throws Exception {

        MvcResult result = mockMvc.perform(
                post(URL_REGISTER)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().isEmpty());
    }

    @Test
    public void registerWithInvalidUsername() throws Exception {

        registerRequest.setUsername(REGISTER_INVALID_USERNAME);

        MvcResult result = mockMvc.perform(
                post(URL_REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);


        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().containsKey(RegisterError.USERNAME_INVALID.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(RegisterError.USERNAME_INVALID.getDescription()));
        Assert.assertEquals(1, response.getErrorMap().size());
    }

    @Test
    public void registerWithInvalidEmail() throws Exception {

        registerRequest.setEmail(REGISTER_INVALID_EMAIL);

        MvcResult result = mockMvc.perform(
                post(URL_REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);


        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().containsKey(RegisterError.INVALID_EMAIL.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(RegisterError.INVALID_EMAIL.getDescription()));
        Assert.assertEquals(1, response.getErrorMap().size());
    }

    @Test
    public void registerWithNonMatchingPasswords() throws Exception {

        registerRequest
                .setPassword(REGISTER_INVALID_PASSWD_PAIR[0])
                .setPassword2(REGISTER_INVALID_PASSWD_PAIR[1]);

        MvcResult result = mockMvc.perform(
                post(URL_REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);


        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().containsKey(RegisterError.PASSWORDS_NOT_MATCHING.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(RegisterError.PASSWORDS_NOT_MATCHING.getDescription()));
        Assert.assertEquals(1, response.getErrorMap().size());
    }

    @Test
    public void registerWithTooShortPassword() throws Exception {

        registerRequest
                .setPassword(REGISTER_TOO_SHORT_PASSWORD)
                .setPassword2(REGISTER_TOO_SHORT_PASSWORD);

        MvcResult result = mockMvc.perform(
                post(URL_REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);


        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().containsKey(RegisterError.PASSWORD_TOO_SHORT.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(RegisterError.PASSWORD_TOO_SHORT.getDescription()));
        Assert.assertEquals(1, response.getErrorMap().size());
    }

    @Test
    @SuppressWarnings("Duplicates")
    public void registerWithInvalidBirthdayFormat() throws Exception {

        registerRequest.setBirthday(REGISTER_INVALID_DATE);

        MvcResult result = mockMvc.perform(
                post(URL_REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);


        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().containsKey(RegisterError.INVALID_DATE.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(RegisterError.INVALID_DATE.getDescription()));
        Assert.assertEquals(1, response.getErrorMap().size());
    }

    @Test
    @SuppressWarnings("Duplicates")
    public void registerWithDateAfterNow() throws Exception {

        registerRequest.setBirthday(REGISTER_DATE_IN_FUTURE);

        MvcResult result = mockMvc.perform(
                post(URL_REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);


        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().containsKey(RegisterError.INVALID_DATE.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(RegisterError.INVALID_DATE.getDescription()));
        Assert.assertEquals(1, response.getErrorMap().size());
    }

    @Test
    @SuppressWarnings("Duplicates")
    public void registerWithNonPossibleDate() throws Exception {

        registerRequest.setBirthday(REGISTER_IMPOSSIBLE_DATE);

        MvcResult result = mockMvc.perform(
                post(URL_REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);


        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().containsKey(RegisterError.INVALID_DATE.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(RegisterError.INVALID_DATE.getDescription()));
        Assert.assertEquals(1, response.getErrorMap().size());
    }

    @Test
    public void registerWithEmptyFirstname() throws Exception {

        registerRequest.setFirstname(REGISTER_EMPTY_FIRSTNAME);

        MvcResult result = mockMvc.perform(
                post(URL_REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);


        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().containsKey(RegisterError.EMPTY_FIRSTNAME.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(RegisterError.EMPTY_FIRSTNAME.getDescription()));
        Assert.assertEquals(1, response.getErrorMap().size());
    }

    @Test
    public void registerWithEmptyLastname() throws Exception {

        registerRequest.setLastname(REGISTER_EMPTY_LASTNAME);

        MvcResult result = mockMvc.perform(
                post(URL_REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);


        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().containsKey(RegisterError.EMPTY_LASTNAME.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(RegisterError.EMPTY_LASTNAME.getDescription()));
        Assert.assertEquals(1, response.getErrorMap().size());
    }

}
