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
import com.builpr.restapi.converter.PrintableModelToPrintablePayloadConverter;
import com.builpr.restapi.model.Response.Response;
import com.builpr.restapi.model.Response.printable.PrintablePayload;
import com.builpr.restapi.model.Response.profile.ProfilePayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;
import java.sql.Date;
import java.util.*;

import static com.builpr.Constants.URL_PROFILE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * tests the profileController
 */
public class ProfileControllerTest extends ControllerTest {

    private static User testUserNoPrintables;
    private static User testUserWithPrintables;

    private static Rating rating1;
    private static Rating rating2;

    private static Printable printable1;
    private static Printable printable2;

    private static DatabaseUserManager userManager = new DatabaseUserManager();
    private static DatabasePrintableManager printableManager = new DatabasePrintableManager();
    private static DatabaseRatingManager ratingManager = new DatabaseRatingManager();
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static final String PROFILE_TEST_NO_PRINTABLES_USERNAME = "no_printables";
    private static final String PROFILE_TEST_PRINTABLES_USERNAME = "printables";
    private static final String PROFILE_TEST_RATING_USERNAME_1 = "ratingUser1";
    private static final String PROFILE_TEST_RATING_USERNAME_2 = "ratingUser2";

    private static final int PRINTABLE_1_ID = 1234;
    private static final int PRINTABLE_2_ID = 1235;

    private static final int RATING_1_ID = 5555;
    private static final int RATING_2_ID = 5556;

    private static final String KEY = "id";

    @BeforeClass
    public static void setUpDatabase() {

        testUserNoPrintables = new UserImpl()
                .setUsername(PROFILE_TEST_NO_PRINTABLES_USERNAME)
                .setEmail("profile_test1@mail.de")
                .setPassword(new BCryptPasswordEncoder().encode("password"))
                .setBirthday(new Date(System.currentTimeMillis() - 1231231))
                .setFirstname("Test")
                .setLastname("User")
                .setShowBirthday(true)
                .setShowEmail(true)
                .setShowName(true);

        testUserWithPrintables = new UserImpl()
                .setUsername(PROFILE_TEST_PRINTABLES_USERNAME)
                .setEmail("profile_test2@mail.de")
                .setPassword(new BCryptPasswordEncoder().encode("password"))
                .setBirthday(new Date(System.currentTimeMillis() - 1123123))
                .setFirstname("Test")
                .setLastname("User2")
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

        userManager.persist(testUserNoPrintables);
        testUserWithPrintables = userManager.persist(testUserWithPrintables);
        ratingUser1 = userManager.persist(ratingUser1);
        ratingUser2 = userManager.persist(ratingUser2);

         printable1 = new PrintableImpl()
                .setPrintableId(PRINTABLE_1_ID)
                .setTitle("printable1")
                .setDescription("Printable")
                .setUploaderId(testUserWithPrintables.getUserId())
                .setFilePath("/path1");
         printable2 = new PrintableImpl()
                .setPrintableId(PRINTABLE_2_ID)
                .setTitle("printable2")
                .setDescription("Printable")
                .setUploaderId(testUserWithPrintables.getUserId())
                .setFilePath("/path2");


        DatabasePrintableManager printableManager = new DatabasePrintableManager();
        printableManager.persist(printable1);
        printableManager.persist(printable2);

        rating1 = new RatingImpl()
                .setRatingId(RATING_1_ID)
                .setRating(5)
                .setMsg("Ein suppa Ding")
                .setPrintableId(PRINTABLE_1_ID)
                .setUserId(ratingUser1.getUserId());
        rating2 = new RatingImpl()
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

        rating1 = ratingManager.persist(rating1);
        rating2 = ratingManager.persist(rating2);
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
        Assert.assertEquals(testUserNoPrintables.getDescription().isPresent() ? testUserNoPrintables.getDescription().get() : null
                , profilePayload.getDescription());
        Assert.assertEquals(new ArrayList<Printable>(), profilePayload.getPrintables());
        Assert.assertEquals(0, profilePayload.getRating(), 0.001);
        Assert.assertEquals(0, profilePayload.getRatingCount());
    }

    @Test
    public void testShowProfileForUserWithPrintables() throws Exception {
        MvcResult result = mockMvc.perform(
                get(URL_PROFILE).param(KEY, testUserWithPrintables.getUserId()+"")
        )
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);

        Map payload = (LinkedHashMap) response.getPayload();

        ProfilePayload profilePayload = objectMapper.convertValue(payload, ProfilePayload.class);

        Assert.assertEquals(testUserWithPrintables.getUsername(), profilePayload.getUsername());
        Assert.assertNull(profilePayload.getEmail());
        Assert.assertNull(profilePayload.getBirthday());
        Assert.assertNull(profilePayload.getFirstname());
        Assert.assertNull(profilePayload.getLastname());
        Assert.assertEquals(testUserWithPrintables.getDescription().isPresent() ? testUserNoPrintables.getDescription().get() : null
                , profilePayload.getDescription());
        List<PrintablePayload> expectedPrintableList = Lists.newArrayList();
        // @todo vll mal rausfinden, wieso man hier nochmal neu auslsesn muss
        expectedPrintableList.add(PrintableModelToPrintablePayloadConverter.from(printableManager.getPrintableById(PRINTABLE_1_ID)));
        expectedPrintableList.add(PrintableModelToPrintablePayloadConverter.from(printableManager.getPrintableById(PRINTABLE_2_ID)));

        List<PrintablePayload> actualPrintableList = Lists.newArrayList(profilePayload.getPrintables());
        Assert.assertEquals(expectedPrintableList, actualPrintableList);
        Assert.assertEquals(((rating1.getRating() + rating2.getRating())/2.0), profilePayload.getRating(), 0.001);
        Assert.assertEquals(2, profilePayload.getRatingCount());
    }
}
