package com.builpr.restapi.controller;

import com.builpr.Constants;
import com.builpr.restapi.error.printable.*;
import com.builpr.restapi.model.Request.Printable.PrintableDeleteRequest;
import com.builpr.restapi.model.Request.Printable.PrintableEditRequest;
import com.builpr.restapi.model.Request.Printable.PrintableNewRequest;
import com.builpr.restapi.model.Response.Response;
import com.builpr.restapi.model.Response.printable.PrintableDeleteResponse;
import com.builpr.restapi.model.Response.printable.PrintableEditResponse;
import com.builpr.restapi.model.Response.printable.PrintableNewResponse;
import com.builpr.restapi.model.Response.printable.PrintableResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * PrintableControllerTests
 */

public class PrintableControllerTest extends ControllerTest {
    protected static final String DB_TEST_USER_NAME = "DB_test_user";
    private static ObjectMapper mapper = new ObjectMapper();
    private static List<String> VALID_LIST = new ArrayList<>();
    private static String VALID_TITLE = "testTitle";
    private static String VALID_DESCRIPTION = "testDescription";
    private static String VALID_PRINTABLEID = "232";
    private static String INVALID_PRINTABLEID = "0";

    public void setValidList() {
        VALID_LIST.add("testTag1");
        VALID_LIST.add("testTag2");
        VALID_LIST.add("testTag3");
        VALID_LIST.add("testTag4");
    }

    @Test
    public void createPrintableWithNull() throws Exception {
        MvcResult result = mockMvc.perform(
                post(Constants.URL_NEW_PRINTABLE)
        )
                .andExpect(status().is4xxClientError())
                .andReturn();
    }

    @Test//(expected = NestedServletException.class)
    @WithMockUser(DB_TEST_USER_NAME)
    public void createPrintableWithValidInput() throws Exception {
        setValidList();
        Path p = FileSystems.getDefault().getPath("C:\\Users\\Markus\\Desktop\\Modells\\testFile.stl");
        byte[] fileData = Files.readAllBytes(p);
        MockMultipartFile file = new MockMultipartFile("newFile", fileData);
        PrintableNewRequest printableNewRequest = new PrintableNewRequest();
        printableNewRequest.setTitle(VALID_TITLE);
        printableNewRequest.setDescription(VALID_DESCRIPTION);
        printableNewRequest.setCategories(VALID_LIST);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.fileUpload(Constants.URL_NEW_PRINTABLE).file(file)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(printableNewRequest))
        ).andExpect(status().isOk())
                .andReturn();

        Response<PrintableNewResponse, PrintableNewError> response = getResponseBodyOf(result, Response.class);
        // TODO immer file = null warum?
    }

    @Test
    @WithMockUser(DB_TEST_USER_NAME)
    public void createPrintableWithInvalidInput() throws Exception {
        setValidList();
        PrintableNewRequest printableNewRequest = new PrintableNewRequest();
        printableNewRequest.setTitle(VALID_TITLE);
        printableNewRequest.setDescription(VALID_DESCRIPTION);
        printableNewRequest.setCategories(VALID_LIST);
        MockMultipartFile file = new MockMultipartFile("data", "testFile.stl".getBytes());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.fileUpload(Constants.URL_NEW_PRINTABLE).file(file)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(printableNewRequest))
        ).andExpect(status().isOk())
                .andReturn();

        Response<PrintableNewResponse, PrintableNewError> response = getResponseBodyOf(result, Response.class);

        Assert.assertTrue(!response.isSuccess());
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(!response.getErrorList().isEmpty());
        // TODO auf die einzelnen Fehler prüfen
    }

    @Test
    public void createPrintableWithoutAuthorization() throws Exception {
        setValidList();
        PrintableNewRequest printableNewRequest = new PrintableNewRequest();
        printableNewRequest.setTitle(VALID_TITLE);
        printableNewRequest.setDescription(VALID_DESCRIPTION);
        printableNewRequest.setCategories(VALID_LIST);

        MvcResult result = mockMvc.perform(
                post(Constants.URL_NEW_PRINTABLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(printableNewRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response<PrintableNewResponse, PrintableNewError> response = getResponseBodyOf(result, Response.class);

        Assert.assertTrue(!response.isSuccess());
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(!response.getErrorList().isEmpty());
        // TODO Errorlist auf USER_INVALID bzw NO_AUTHORIZATION prüfen
    }

    @Test
    public void downloadPrinitabeWithInvalidId() throws Exception {
        MvcResult result = mockMvc.perform(
                get(Constants.URL_DOWNLOAD)
                        .param("id", INVALID_PRINTABLEID)
        )
                .andExpect(status().isOk())
                .andReturn();

        Response<MultipartFile, PrintableDownloadError> response = getResponseBodyOf(result, Response.class);
        Assert.assertNull(response.getPayload());
        Assert.assertTrue(!response.isSuccess());
        Assert.assertTrue(!response.getErrorList().isEmpty());
    }

    @Test
    @WithMockUser(DB_TEST_USER_NAME)
    public void getPrintable() throws Exception {
        MvcResult result = mockMvc.perform(
                get(Constants.URL_GET_PRINTABLE)
                        .param("id", VALID_PRINTABLEID)
        )
                .andExpect(status().isOk())
                .andReturn();

        Response<PrintableResponse, PrintableError> response = getResponseBodyOf(result, Response.class);

        Assert.assertNotNull(response.getPayload());
        Assert.assertTrue(response.getErrorList().isEmpty());
    }

    @Test
    @WithMockUser(DB_TEST_USER_NAME)
    public void getInvalidPrintable() throws Exception {
        MvcResult result = mockMvc.perform(
                get(Constants.URL_GET_PRINTABLE)
                        .param("id", INVALID_PRINTABLEID)
        )
                .andExpect(status().isOk())
                .andReturn();

        Response<PrintableResponse, PrintableError> response = getResponseBodyOf(result, Response.class);

        Assert.assertNull(response.getPayload());
        Assert.assertTrue(!response.getErrorList().isEmpty());
    }

    @Test
    @WithMockUser(DB_TEST_USER_NAME)
    public void editPrintableWithValidInput() throws Exception {
        setValidList();
        PrintableEditRequest printableEditRequest = new PrintableEditRequest();
        printableEditRequest.setTitle("newnewTitle");
        printableEditRequest.setDescription("newDescription");
        printableEditRequest.setCategories(VALID_LIST);
        printableEditRequest.setPrintableID(232);
        MvcResult result = mockMvc.perform(
                put(Constants.URL_EDIT_PRINTABLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(printableEditRequest)))
                .andExpect(status().isOk())
                .andReturn();

        Response<PrintableEditResponse, PrintableEditError> response = getResponseBodyOf(result, Response.class);

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getPayload());
    }

    @Test
    @WithMockUser(DB_TEST_USER_NAME)
    public void deletePrintableWithValidInput() throws Exception {
        // TODO immer neues Printable angelegen das dann gelöscht wird
        PrintableDeleteRequest request = new PrintableDeleteRequest();
        request.setConfirmation(true);
        request.setPrintableID(235);


        MvcResult result = mockMvc.perform(
                delete(Constants.URL_DELETE_PRINTABLE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        Response<PrintableDeleteResponse, PrintableDeleteError> response = getResponseBodyOf(result, Response.class);

        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getPayload());
    }
}
