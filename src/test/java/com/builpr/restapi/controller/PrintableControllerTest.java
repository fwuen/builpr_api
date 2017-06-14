package com.builpr.restapi.controller;

import com.builpr.Constants;
import com.builpr.database.bridge.printable.PrintableImpl;
import com.builpr.database.service.DatabasePrintableManager;
import com.builpr.database.service.DatabaseUserManager;
import com.builpr.restapi.error.printable.*;
import com.builpr.restapi.model.Request.Printable.PrintableDeleteRequest;
import com.builpr.restapi.model.Request.Printable.PrintableEditRequest;
import com.builpr.restapi.model.Request.Printable.PrintableNewRequest;
import com.builpr.restapi.model.Response.Response;
import com.builpr.restapi.model.Response.printable.PrintableDeleteResponse;
import com.builpr.restapi.model.Response.printable.PrintableEditResponse;
import com.builpr.restapi.model.Response.printable.PrintableNewResponse;
import com.builpr.restapi.model.Response.printable.PrintableResponse;
import com.builpr.restapi.utils.TokenGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.*;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * PrintableControllerTests
 */

public class PrintableControllerTest extends ControllerTest {
    private static final String DB_TEST_USER_NAME = "DB_test_user";

    private static final int TEST_PRINTABLE_ID = 999999999;
    private static final String TEST_PRINTABLE_ID_STRING = "999999999";
    private static final String INVALID_PRINTABLE_ID = "0";

    private static final String INVALID_TITLE = "test";
    private static TokenGenerator tokenGenerator = new TokenGenerator(510);

    private static final String INVALID_DESCRIPTION = tokenGenerator.generate() + tokenGenerator.generate();
    private static ObjectMapper mapper = new ObjectMapper();
    private static List<String> VALID_LIST = new ArrayList<>();
    private static List<String> INVALID_LIST = new ArrayList<>();

    private static String VALID_TITLE = "testTitle";
    private static byte[] VALID_FILE;
    private static String VALID_DESCRIPTION = "testDescription";

    public void fillFill() throws IOException {
        String path = "C:\\Users\\Markus\\Desktop\\Modells\\testFile.stl";
        Path p = FileSystems.getDefault().getPath(path);
        VALID_FILE = Files.readAllBytes(p);
    }

    public void setValidList() {
        VALID_LIST.add("Tag1");
        VALID_LIST.add("Tag2");
        VALID_LIST.add("Tag3");
        VALID_LIST.add("Tag4");
        VALID_LIST.add("NeuerTestTag");
    }

    public void setInvalidList() {
        INVALID_LIST.add("  b  ");
        INVALID_LIST.add(".ß3---___");
    }

    @Before
    public void createTestPrintable() {
        DatabasePrintableManager databasePrintableManager = new DatabasePrintableManager();
        DatabaseUserManager databaseUserManager = new DatabaseUserManager();

        PrintableImpl printable = new PrintableImpl();
        printable.setTitle("Printable only for testing");
        printable.setDescription("This printable is only for testing purpose");
        printable.setUploadDate(new Date(System.currentTimeMillis()));
        printable.setPrintableId(TEST_PRINTABLE_ID);
        printable.setUploaderId(databaseUserManager.getByUsername(DB_TEST_USER_NAME).getUserId());
        printable.setFilePath("C:\\Users\\Markus\\Desktop\\Modells\\testFile.stl");
        printable.setNumDownloads(99);
        databasePrintableManager.persist(printable);
    }

    @After
    public void deleteTestPrintable() {
        DatabasePrintableManager databasePrintableManager = new DatabasePrintableManager();
        databasePrintableManager.deletePrintable(TEST_PRINTABLE_ID);
    }
    //-----------------------------------------------------------------------------------------------------------//
    //        /printable/get                                                                                     //
    //-----------------------------------------------------------------------------------------------------------//

    @Test
    @WithMockUser(TEST_USER_NAME)
    public void getPrintable() throws Exception {
        // isMine-flag is false
        MvcResult result = mockMvc.perform(
                get(Constants.URL_GET_PRINTABLE)
                        .param("id", TEST_PRINTABLE_ID_STRING)
        )
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertNotNull(response.getPayload());
        Assert.assertTrue(response.isSuccess());
        Assert.assertTrue(response.getErrorMap().isEmpty());
    }

    @Test
    @WithMockUser(DB_TEST_USER_NAME)
    public void getPrintableIsMine() throws Exception {
        // isMine-flag is true
        MvcResult result = mockMvc.perform(
                get(Constants.URL_GET_PRINTABLE)
                        .param("id", TEST_PRINTABLE_ID_STRING)
        )
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().isEmpty());
    }

    @Test
    @WithMockUser(DB_TEST_USER_NAME)
    public void getInvalidPrintable() throws Exception {
        MvcResult result = mockMvc.perform(
                get(Constants.URL_GET_PRINTABLE)
                        .param("id", INVALID_PRINTABLE_ID)
        )
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertTrue(!response.isSuccess());
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(!response.getErrorMap().isEmpty());
        Assert.assertTrue(response.getErrorMap().containsKey(PrintableError.INVALID_PRINTABLEID.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(PrintableError.INVALID_PRINTABLEID.getDescription()));
    }

    //-----------------------------------------------------------------------------------------------------------//
    //        /printable/new                                                                                     //
    //-----------------------------------------------------------------------------------------------------------//
    @Test
    public void createPrintableWithNull() throws Exception {
        MvcResult result = mockMvc.perform(
                post(Constants.URL_NEW_PRINTABLE)
        )
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test
    @WithMockUser(DB_TEST_USER_NAME)
    public void createPrintableWithValidInput() throws Exception {
        setValidList();
        fillFill();
        PrintableNewRequest printableNewRequest = new PrintableNewRequest();
        printableNewRequest.setDescription(VALID_DESCRIPTION);
        printableNewRequest.setTitle(VALID_TITLE);
        printableNewRequest.setCategories(VALID_LIST);
        printableNewRequest.setFile(VALID_FILE);

        MvcResult result = mockMvc.perform(post(Constants.URL_NEW_PRINTABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(printableNewRequest))
        ).andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().isEmpty());
    }

    @Test
    @WithMockUser(DB_TEST_USER_NAME)
    public void createPrintableWithInvalidTitle() throws Exception {
        setValidList();
        fillFill();
        PrintableNewRequest printableNewRequest = new PrintableNewRequest();
        printableNewRequest.setDescription(VALID_DESCRIPTION);
        printableNewRequest.setTitle(INVALID_TITLE);
        printableNewRequest.setCategories(VALID_LIST);
        printableNewRequest.setFile(VALID_FILE);


        MvcResult result = mockMvc.perform(post(Constants.URL_NEW_PRINTABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(printableNewRequest))
        ).andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertTrue(!response.isSuccess());
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(!response.getErrorMap().isEmpty());
        Assert.assertTrue(response.getErrorMap().containsKey(PrintableNewError.TITLE_INVALID.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(PrintableNewError.TITLE_INVALID.getDescription()));
    }

    @Test
    @WithMockUser(DB_TEST_USER_NAME)
    public void createPrintableWithInvalidDescription() throws Exception {
        fillFill();
        setValidList();
        PrintableNewRequest printableNewRequest = new PrintableNewRequest();
        printableNewRequest.setTitle(VALID_TITLE);
        printableNewRequest.setDescription(INVALID_DESCRIPTION);
        printableNewRequest.setCategories(VALID_LIST);
        printableNewRequest.setFile(VALID_FILE);

        MvcResult result = mockMvc.perform(post(Constants.URL_NEW_PRINTABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(printableNewRequest))
        ).andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertTrue(!response.isSuccess());
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(!response.getErrorMap().isEmpty());
        Assert.assertTrue(response.getErrorMap().containsKey(PrintableNewError.DESCRIPTION_INVALID.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(PrintableNewError.DESCRIPTION_INVALID.getDescription()));
    }

    @Test
    @WithMockUser(DB_TEST_USER_NAME)
    public void createPrintableWithInvalidFile() throws Exception {
        setValidList();
        PrintableNewRequest printableNewRequest = new PrintableNewRequest();
        printableNewRequest.setTitle(VALID_TITLE);
        printableNewRequest.setDescription(VALID_DESCRIPTION);
        printableNewRequest.setCategories(VALID_LIST);
        printableNewRequest.setFile(null);

        MvcResult result = mockMvc.perform(post(Constants.URL_NEW_PRINTABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(printableNewRequest))
        ).andExpect(status().isOk())
                .andReturn();


        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertTrue(!response.isSuccess());
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(!response.getErrorMap().isEmpty());
        Assert.assertTrue(response.getErrorMap().containsKey(PrintableNewError.FILE_INVALID.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(PrintableNewError.FILE_INVALID.getDescription()));
    }

    @Test
    @WithMockUser("FakeUser")
    public void createPrintableWithInvalidUser() throws Exception {
        setValidList();
        fillFill();
        PrintableNewRequest printableNewRequest = new PrintableNewRequest();
        printableNewRequest.setCategories(VALID_LIST);
        printableNewRequest.setTitle(VALID_TITLE);
        printableNewRequest.setDescription(VALID_DESCRIPTION);
        printableNewRequest.setFile(VALID_FILE);
        MvcResult result = mockMvc.perform(post(Constants.URL_NEW_PRINTABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(printableNewRequest))
        ).andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertTrue(!response.isSuccess());
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(!response.getErrorMap().isEmpty());
        Assert.assertTrue(response.getErrorMap().containsKey(PrintableNewError.USER_INVALID.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(PrintableNewError.USER_INVALID.getDescription()));
    }

    @Test
    @WithMockUser(DB_TEST_USER_NAME)
    public void createPrintableWithInvalidCategories() throws Exception {
        setInvalidList();
        fillFill();
        PrintableNewRequest printableNewRequest = new PrintableNewRequest();
        printableNewRequest.setCategories(INVALID_LIST);
        printableNewRequest.setTitle(VALID_TITLE);
        printableNewRequest.setDescription(VALID_DESCRIPTION);
        printableNewRequest.setFile(VALID_FILE);

        MvcResult result = mockMvc.perform(post(Constants.URL_NEW_PRINTABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(printableNewRequest))
        ).andExpect(status().isOk())
                .andReturn();


        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertTrue(!response.isSuccess());
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(!response.getErrorMap().isEmpty());
        Assert.assertTrue(response.getErrorMap().containsKey(PrintableNewError.CATEGORIES_INVALID.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(PrintableNewError.CATEGORIES_INVALID.getDescription()));
    }

    @Test
    public void createPrintableWithoutAuthorization() throws Exception {
        setValidList();
        fillFill();
        PrintableNewRequest printableNewRequest = new PrintableNewRequest();
        printableNewRequest.setTitle(VALID_TITLE);
        printableNewRequest.setDescription(VALID_DESCRIPTION);
        printableNewRequest.setCategories(VALID_LIST);
        printableNewRequest.setFile(VALID_FILE);

        MvcResult result = mockMvc.perform(
                post(Constants.URL_NEW_PRINTABLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(printableNewRequest)))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    //-----------------------------------------------------------------------------------------------------------//
    //                      /printable/download                                                                  //
    //-----------------------------------------------------------------------------------------------------------//
    @Test
    public void downloadPrinitabeWithValidId() throws Exception {
        MvcResult result = mockMvc.perform(
                get(Constants.URL_DOWNLOAD)
                        .param("id", TEST_PRINTABLE_ID_STRING)
        )
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);
        Assert.assertNotNull(response.getPayload());
        Assert.assertTrue(response.isSuccess());
        Assert.assertTrue(response.getErrorMap().isEmpty());
    }

    @Test
    public void downloadWithInvalidId() throws Exception {
        MvcResult result = mockMvc.perform(
                get(Constants.URL_DOWNLOAD)
                        .param("id", INVALID_PRINTABLE_ID)
        )
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(!response.isSuccess());
        Assert.assertTrue(!response.getErrorMap().isEmpty());
        Assert.assertTrue(response.getErrorMap().containsKey(PrintableDownloadError.PRINTABLE_ID_INVALID.getCode()));
        Assert.assertTrue(response.getErrorMap().containsValue(PrintableDownloadError.PRINTABLE_ID_INVALID.getDescription()));
    }


    //-----------------------------------------------------------------------------------------------------------//
    //                      /printable/edit                                                                      //
    //-----------------------------------------------------------------------------------------------------------//
    @Test
    @WithMockUser(DB_TEST_USER_NAME)
    public void editPrintableWithValidInput() throws Exception {
        setValidList();
        PrintableEditRequest printableEditRequest = new PrintableEditRequest();
        printableEditRequest.setTitle("newnewTitle");
        printableEditRequest.setDescription("newDescription");
        printableEditRequest.setCategories(VALID_LIST);
        printableEditRequest.setPrintableID(1000000001);
        MvcResult result = mockMvc.perform(
                put(Constants.URL_EDIT_PRINTABLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(printableEditRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertTrue(response.isSuccess());
        Assert.assertNotNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().isEmpty());
    }

    //-----------------------------------------------------------------------------------------------------------//
    //                      /printable/delete                                                                    //
    //-----------------------------------------------------------------------------------------------------------//
    @Test
    @WithMockUser(DB_TEST_USER_NAME)
    public void deletePrintableWithValidInput() throws Exception {
        DatabasePrintableManager databasePrintableManager = new DatabasePrintableManager();
        DatabaseUserManager databaseUserManager = new DatabaseUserManager();

        PrintableImpl printable = new PrintableImpl();
        printable.setTitle("Printable only for testing");
        printable.setDescription("This printable is only for testing purpose");
        printable.setUploadDate(new Date(System.currentTimeMillis()));
        printable.setPrintableId(888888888);
        printable.setUploaderId(databaseUserManager.getByUsername(DB_TEST_USER_NAME).getUserId());
        printable.setFilePath("C:\\Users\\Markus\\Desktop\\Modells\\testFileDelete.stl");
        printable.setNumDownloads(99);
        databasePrintableManager.persist(printable);

        PrintableDeleteRequest request = new PrintableDeleteRequest();
        request.setConfirmation(true);
        request.setPrintableID(888888888);


        MvcResult result = mockMvc.perform(
                delete(Constants.URL_DELETE_PRINTABLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        Response response = getResponseBodyOf(result, Response.class);

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getPayload());
        Assert.assertTrue(response.getErrorMap().isEmpty());
    }

    @Test
    public void checkCategories() {
        setValidList();
        setInvalidList();
        DatabasePrintableManager databasePrintableManager = new DatabasePrintableManager();
        List<String> validList = databasePrintableManager.checkCategories(VALID_LIST);
        List<String> invalidList = databasePrintableManager.checkCategories(INVALID_LIST);
        Assert.assertTrue(invalidList.isEmpty());
        Assert.assertTrue(!validList.isEmpty());


    }
}
