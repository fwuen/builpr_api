package com.builpr.restapi.controller;

import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.user.User;
import com.builpr.database.bridge.user.UserImpl;
import com.builpr.database.service.DatabaseUserManager;
import com.builpr.restapi.model.Response.Response;
import com.builpr.restapi.model.Response.profile.ProfilePayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.builpr.Constants.URL_PROFILE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * tests the profileController
 * @todo fertigstellen
 */
public class ProfileControllerTest extends ControllerTest {

    private static User testUserNoPrintables;
    private static DatabaseUserManager userManager = new DatabaseUserManager();
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static final String PROFILE_TEST_NO_PRINTABLES_USERNAME = "no_printables";

    private static final String KEY = "id";

    @BeforeClass
    public static void setUpDatabase() {

        testUserNoPrintables = new UserImpl()
                .setUsername(PROFILE_TEST_NO_PRINTABLES_USERNAME)
                .setEmail("test_user@mail.de")
                .setPassword(new BCryptPasswordEncoder().encode("password"))
                .setBirthday(new Date(System.currentTimeMillis()-1231231))
                .setFirstname("Test")
                .setLastname("User")
                .setShowBirthday(true)
                .setShowEmail(true)
                .setShowName(true);

        if (userManager.isPresent(PROFILE_TEST_NO_PRINTABLES_USERNAME)) {
            userManager.deleteByUsername(PROFILE_TEST_NO_PRINTABLES_USERNAME);
        }

        userManager.persist(testUserNoPrintables);
    }

    @AfterClass
    public static void tearDownDatabse() {

        if (userManager.isPresent(PROFILE_TEST_NO_PRINTABLES_USERNAME)) {
            userManager.deleteByUsername(PROFILE_TEST_NO_PRINTABLES_USERNAME);
        }

    }

    @Test
    public void testShowProfileForUserWithNoPrintables() throws Exception {
        MvcResult result = mockMvc.perform(
                get(URL_PROFILE).param(KEY, testUserNoPrintables.getUserId()+"")
        )
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);

        Map payload = (LinkedHashMap) response.getPayload();

        ProfilePayload profilePayload = objectMapper.convertValue(payload, ProfilePayload.class);

        Assert.assertEquals(testUserNoPrintables.getUsername(), profilePayload.getUsername());
        Assert.assertEquals(testUserNoPrintables.getEmail(), profilePayload.getEmail());
        Assert.assertEquals(testUserNoPrintables.getBirthday().toString(), profilePayload.getBirthday());
        Assert.assertEquals(testUserNoPrintables.getFirstname(), profilePayload.getFirstname());
        Assert.assertEquals(testUserNoPrintables.getLastname(), profilePayload.getLastname());
        // @todo avater-url nicht nullable machen
        Assert.assertEquals(testUserNoPrintables.getDescription().isPresent() ? testUserNoPrintables.getDescription().get() : null
                , profilePayload.getDescription());
        Assert.assertEquals(new ArrayList<Printable>(), profilePayload.getPrintables());
        Assert.assertEquals(0, profilePayload.getRating(), 0.001);
        Assert.assertEquals(0, profilePayload.getRatingCount());
    }
}
