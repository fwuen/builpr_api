package com.builpr.restapi.controller;

import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.printable.PrintableImpl;
import com.builpr.database.bridge.rating.Rating;
import com.builpr.database.bridge.rating.RatingImpl;
import com.builpr.database.bridge.user.User;
import com.builpr.database.bridge.user.UserImpl;
import com.builpr.database.service.DatabasePrintableManager;
import com.builpr.database.service.DatabaseRatingManager;
import com.builpr.database.service.DatabaseUserManager;
import com.builpr.restapi.converter.UserModelToProfileResponseConverter;
import com.builpr.restapi.error.response.profile.ProfileEditError;
import com.builpr.restapi.model.Request.ProfileEditRequest;
import com.builpr.restapi.model.Response.Response;
import com.builpr.restapi.model.Response.profile.ProfilePayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import java.sql.Date;
import java.util.*;

import static com.builpr.Constants.URL_PROFILE;
import static com.builpr.Constants.URL_PROFILE_EDIT;
import static com.builpr.Constants.URL_REGISTER;
import static com.builpr.restapi.error.response.profile.ProfileEditError.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * tests the profileController
 */
public class ProfileControllerTest extends ControllerTest {

    private static User testUserNoPrintables;
    private static User testUserWithPrintables;

    private static DatabaseUserManager userManager = new DatabaseUserManager();
    private static DatabasePrintableManager printableManager = new DatabasePrintableManager();
    private static DatabaseRatingManager ratingManager = new DatabaseRatingManager();
    private static ObjectMapper mapper = new ObjectMapper();

    private static final String PROFILE_TEST_NO_PRINTABLES_USERNAME = "no_printables";
    private static final String PROFILE_TEST_PRINTABLES_USERNAME = "printables";
    private static final String PROFILE_TEST_RATING_USERNAME_1 = "ratingUser1";
    private static final String PROFILE_TEST_RATING_USERNAME_2 = "ratingUser2";

    private static final int NON_EXISTING_USER_ID = 1234567;

    private static final int PRINTABLE_1_ID = 1234;
    private static final int PRINTABLE_2_ID = 1235;

    private static final int RATING_1_ID = 5555;
    private static final int RATING_2_ID = 5556;

    private static final String KEY = "id";


    // attributes for editProfile()

    private static final String VALID_EMAIL_FOR_EDIT = "newProfileMail@mail.com";
    private static final String INVALID_EMAIL_FOR_EDIT = "newProfileMail@maio@d.com";

    private static final String PASSWORD = "password";
    private static final String INCORRECT_PASSWORD = "password123";

    private static final String CORRECT_NEW_PASSWORD = "password1";
    private static final String[] INCORRECT_NEW_PASSWORD_PAIR = {"password1", "password2"};
    private static final String TOO_SHORT_NEW_PASSWORD = "abc";

    private static final String NEW_FIRST_NAME = "Horst";
    private static final String NEW_LAST_NAME = "Horstner";

    private static final String NEW_DESCRIPTION = "new description";

    private ProfileEditRequest validProfileEditRequest;

    public ProfileControllerTest() {
        validProfileEditRequest = new ProfileEditRequest()
                .setEmail(VALID_EMAIL_FOR_EDIT)
                .setOldPassword(PASSWORD)
                .setPassword(CORRECT_NEW_PASSWORD)
                .setPassword2(CORRECT_NEW_PASSWORD)
                .setFirstName(NEW_FIRST_NAME)
                .setLastName(NEW_LAST_NAME)
                .setDescription(NEW_DESCRIPTION)
                .setShowName(false)
                .setShowEmail(false)
                .setShowBirthday(false);
    }

    @BeforeClass
    public static void setUpDatabase() {

        testUserNoPrintables = new UserImpl()
                .setUsername(PROFILE_TEST_NO_PRINTABLES_USERNAME)
                .setEmail("profile_test1@mail.de")
                .setPassword(new BCryptPasswordEncoder().encode(PASSWORD))
                .setBirthday(new Date(System.currentTimeMillis() - 1231231))
                .setFirstname("Test")
                .setLastname("User")
                .setShowBirthday(true)
                .setShowEmail(true)
                .setShowName(true);

        testUserWithPrintables = new UserImpl()
                .setUsername(PROFILE_TEST_PRINTABLES_USERNAME)
                .setEmail("profile_test2@mail.de")
                .setPassword(new BCryptPasswordEncoder().encode(PASSWORD))
                .setBirthday(new Date(System.currentTimeMillis() - 1123123))
                .setFirstname("Test")
                .setLastname("User2")
                .setDescription("description")
                .setShowBirthday(false)
                .setShowName(false)
                .setShowName(false);

        User ratingUser1 = new UserImpl()
                .setUsername(PROFILE_TEST_RATING_USERNAME_1)
                .setEmail("rating1@mail.de")
                .setPassword(new BCryptPasswordEncoder().encode("password"))
                .setBirthday(new Date(System.currentTimeMillis() - 1123123))
                .setFirstname("Test")
                .setLastname("User2")
                .setShowBirthday(false)
                .setShowName(false)
                .setShowName(false);

        User ratingUser2 = new UserImpl()
                .setUsername(PROFILE_TEST_RATING_USERNAME_2)
                .setEmail("rating2@mail.de")
                .setPassword(new BCryptPasswordEncoder().encode("password"))
                .setBirthday(new Date(System.currentTimeMillis() - 1123123))
                .setFirstname("Test")
                .setLastname("User2")
                .setShowBirthday(false)
                .setShowName(false)
                .setShowName(false);

        if (userManager.isPresent(PROFILE_TEST_NO_PRINTABLES_USERNAME)) {
            userManager.deleteByUsername(PROFILE_TEST_NO_PRINTABLES_USERNAME);
        }
        if (userManager.isPresent(PROFILE_TEST_PRINTABLES_USERNAME)) {
            userManager.deleteByUsername(PROFILE_TEST_PRINTABLES_USERNAME);
        }
        if (userManager.isPresent(PROFILE_TEST_RATING_USERNAME_1)) {
            userManager.deleteByUsername(PROFILE_TEST_RATING_USERNAME_1);
        }
        if (userManager.isPresent(PROFILE_TEST_RATING_USERNAME_2)) {
            userManager.deleteByUsername(PROFILE_TEST_RATING_USERNAME_2);
        }
        if (userManager.isPresent(NON_EXISTING_USER_ID)) {
            userManager.deleteByID(NON_EXISTING_USER_ID);
        }

        userManager.persist(testUserNoPrintables);
        testUserWithPrintables = userManager.persist(testUserWithPrintables);
        ratingUser1 = userManager.persist(ratingUser1);
        ratingUser2 = userManager.persist(ratingUser2);

        Printable printable1 = new PrintableImpl()
                .setPrintableId(PRINTABLE_1_ID)
                .setTitle("printable1")
                .setDescription("Printable")
                .setUploaderId(testUserWithPrintables.getUserId())
                .setFilePath("/path1");
        Printable printable2 = new PrintableImpl()
                .setPrintableId(PRINTABLE_2_ID)
                .setTitle("printable2")
                .setDescription("Printable")
                .setUploaderId(testUserWithPrintables.getUserId())
                .setFilePath("/path2");


        DatabasePrintableManager printableManager = new DatabasePrintableManager();
        printableManager.persist(printable1);
        printableManager.persist(printable2);

        Rating rating1 = new RatingImpl()
                .setRatingId(RATING_1_ID)
                .setRating(5)
                .setMsg("Ein suppa Ding")
                .setPrintableId(PRINTABLE_1_ID)
                .setUserId(ratingUser1.getUserId());
        Rating rating2 = new RatingImpl()
                .setRatingId(RATING_2_ID)
                .setRating(2)
                .setMsg("Ein suppa Ding")
                .setPrintableId(PRINTABLE_1_ID)
                .setUserId(ratingUser2.getUserId());

        if (ratingManager.isPresent(RATING_1_ID)) {
            ratingManager.deleteRatingByID(RATING_1_ID);
        }
        if (ratingManager.isPresent(RATING_2_ID)) {
            ratingManager.deleteRatingByID(RATING_1_ID);
        }

        ratingManager.persist(rating1);
        ratingManager.persist(rating2);
    }

    @AfterClass
    public static void tearDownDatabase() {

        if (userManager.isPresent(PROFILE_TEST_NO_PRINTABLES_USERNAME)) {
            userManager.deleteByUsername(PROFILE_TEST_NO_PRINTABLES_USERNAME);
        }
        if(userManager.isPresent(PROFILE_TEST_PRINTABLES_USERNAME)) {
            userManager.deleteByUsername(PROFILE_TEST_PRINTABLES_USERNAME);
        }
        if (userManager.isPresent(PROFILE_TEST_RATING_USERNAME_1)) {
            userManager.deleteByUsername(PROFILE_TEST_RATING_USERNAME_1);
        }
        if (userManager.isPresent(PROFILE_TEST_RATING_USERNAME_2)) {
            userManager.deleteByUsername(PROFILE_TEST_RATING_USERNAME_2);
        }

        if (printableManager.isPresent(PRINTABLE_1_ID)) {
            userManager.deleteByID(PRINTABLE_1_ID);
        }
        if (printableManager.isPresent(PRINTABLE_2_ID)) {
            userManager.deleteByID(PRINTABLE_2_ID);
        }

        if (ratingManager.isPresent(RATING_1_ID)) {
            ratingManager.deleteRatingByID(RATING_1_ID);
        }
        if (ratingManager.isPresent(RATING_2_ID)) {
            ratingManager.deleteRatingByID(RATING_2_ID);
        }
    }

    @Test
    @SuppressWarnings("Duplicates")
    public void showProfileForUserWithNoPrintables() throws Exception {
        MvcResult result = mockMvc.perform(
                get(URL_PROFILE).param(KEY, testUserNoPrintables.getUserId()+"")
        )
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);

        Map payload = (LinkedHashMap) response.getPayload();

        ProfilePayload profilePayload = mapper.convertValue(payload, ProfilePayload.class);

        // get the user directly from the database (for default fields)
        testUserNoPrintables = userManager.getByUsername(PROFILE_TEST_NO_PRINTABLES_USERNAME);
        ProfilePayload expectedPayload = UserModelToProfileResponseConverter.from(testUserNoPrintables);

        Assert.assertEquals(expectedPayload, profilePayload);
    }

    @Test
    @SuppressWarnings("Duplicates")
    public void showProfileForUserWithPrintables() throws Exception {
        MvcResult result = mockMvc.perform(
                get(URL_PROFILE).param(KEY, testUserWithPrintables.getUserId()+"")
        )
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);

        Map payload = (LinkedHashMap) response.getPayload();

        ProfilePayload profilePayload = mapper.convertValue(payload, ProfilePayload.class);

        // get the user directly from the database (for default fields)
        testUserWithPrintables = userManager.getByUsername(PROFILE_TEST_PRINTABLES_USERNAME);
        ProfilePayload expectedPayload = UserModelToProfileResponseConverter.from(testUserWithPrintables);

        Assert.assertEquals(expectedPayload, profilePayload);
    }

    @Test
    public void showProfileForUserForNonExistentUser() throws Exception {
        mockMvc.perform(
                get(URL_PROFILE).param(KEY, NON_EXISTING_USER_ID + "")
        )
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(PROFILE_TEST_NO_PRINTABLES_USERNAME)
    public void editProfile() throws Exception {
        MvcResult result = mockMvc.perform(
                put(URL_PROFILE_EDIT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(validProfileEditRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);
        Assert.assertTrue(response.isSuccess());
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().isEmpty());
    }

    @Test
    @WithMockUser(PROFILE_TEST_NO_PRINTABLES_USERNAME)
    public void editProfileWithInvalidEmail() throws Exception {
        validProfileEditRequest.setEmail(INVALID_EMAIL_FOR_EDIT);

        MvcResult result = mockMvc.perform(
                put(URL_PROFILE_EDIT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(validProfileEditRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().containsKey(INVALID_EMAIL.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(INVALID_EMAIL.getDescription()));
        Assert.assertEquals(1, response.getErrorMap().size());
    }

    @Test
    @WithMockUser(PROFILE_TEST_NO_PRINTABLES_USERNAME)
    public void editProfileWithIncorrectOldPassword() throws Exception {
        validProfileEditRequest.setOldPassword(INCORRECT_PASSWORD);

        MvcResult result = mockMvc.perform(
                put(URL_PROFILE_EDIT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(validProfileEditRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().containsKey(OLD_PASSWORD_NOT_CORRECT.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(OLD_PASSWORD_NOT_CORRECT.getDescription()));
        Assert.assertEquals(1, response.getErrorMap().size());
    }

    @Test
    @WithMockUser(PROFILE_TEST_NO_PRINTABLES_USERNAME)
    public void editProfileWithTooShortNewPassword() throws Exception {
        validProfileEditRequest.setPassword(TOO_SHORT_NEW_PASSWORD);
        validProfileEditRequest.setPassword2(TOO_SHORT_NEW_PASSWORD);

        MvcResult result = mockMvc.perform(
                put(URL_PROFILE_EDIT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(validProfileEditRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().containsKey(NEW_PASSWORD_TOO_SHORT.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(NEW_PASSWORD_TOO_SHORT.getDescription()));
        Assert.assertEquals(1, response.getErrorMap().size());
    }

    @Test
    @WithMockUser(PROFILE_TEST_NO_PRINTABLES_USERNAME)
    public void editProfileWithNewPasswordsNotMatching() throws Exception {
        validProfileEditRequest.setPassword(INCORRECT_NEW_PASSWORD_PAIR[0]);
        validProfileEditRequest.setPassword2(INCORRECT_NEW_PASSWORD_PAIR[1]);

        MvcResult result = mockMvc.perform(
                put(URL_PROFILE_EDIT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(validProfileEditRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().containsKey(NEW_PASSWORDS_NOT_MATCHING.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(NEW_PASSWORDS_NOT_MATCHING.getDescription()));
        Assert.assertEquals(1, response.getErrorMap().size());
    }

    @Test
    @WithMockUser(PROFILE_TEST_NO_PRINTABLES_USERNAME)
    public void editProfileWithOldPasswordNull() throws Exception {
        validProfileEditRequest.setOldPassword(null);

        MvcResult result = mockMvc.perform(
                put(URL_PROFILE_EDIT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(validProfileEditRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().containsKey(OLD_PASSWORD_NOT_CORRECT.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(OLD_PASSWORD_NOT_CORRECT.getDescription()));
        Assert.assertEquals(1, response.getErrorMap().size());
    }

    @Test
    @WithMockUser(PROFILE_TEST_NO_PRINTABLES_USERNAME)
    public void editProfileWithEmptyName() throws Exception {
        validProfileEditRequest.setFirstName("");
        validProfileEditRequest.setLastName("");

        MvcResult result = mockMvc.perform(
                put(URL_PROFILE_EDIT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(validProfileEditRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);
        Assert.assertFalse(response.isSuccess());
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().containsKey(FIRSTNAME_EMPTY.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(FIRSTNAME_EMPTY.getDescription()));
        Assert.assertTrue(response.getErrorMap().containsKey(LASTNAME_EMPTY.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(LASTNAME_EMPTY.getDescription()));
        Assert.assertEquals(2, response.getErrorMap().size());
    }
}
