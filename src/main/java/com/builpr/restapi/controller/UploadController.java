package com.builpr.restapi.controller;

import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.user.User;
import com.builpr.database.service.DatabaseCategoryManager;
import com.builpr.database.service.DatabasePrintableCategoryManager;
import com.builpr.database.service.DatabasePrintableManager;
import com.builpr.database.service.DatabaseUserManager;
import com.builpr.restapi.error.response.account.printable.PrintableDownloadError;
import com.builpr.restapi.error.response.account.printable.PrintableNewError;
import com.builpr.restapi.model.Request.Printable.PrintableNewRequest;
import com.builpr.restapi.model.Response.Response;
import com.builpr.restapi.model.Response.printable.PrintableNewResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;

/**
 *
 */
public class UploadController {
    /**
     * Printable Service
     */
    private DatabasePrintableManager databasePrintableManager;
    private PrintableToResponseConverter printableToResponseConverter;
    private DatabaseCategoryManager databaseCategoryManager;
    private DatabasePrintableCategoryManager databasePrintableCategoryManager;
    private DatabaseUserManager databaseUserManager;


    public UploadController() {
        databasePrintableManager = new DatabasePrintableManager();
        printableToResponseConverter = new PrintableToResponseConverter();
        databaseCategoryManager = new DatabaseCategoryManager();
        databasePrintableCategoryManager = new DatabasePrintableCategoryManager();
        databaseUserManager = new DatabaseUserManager();
    }


    /**
     * @param principal Principal
     * @param request   PrintableRequest
     * @return response Response<PrintableResponse, PrintableError>
     * @throws PrintableNotFoundException Exception
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/printable", method = RequestMethod.GET)
    @ResponseBody
    public Response<PrintableResponse, PrintableError> getPrintable(Principal principal, @RequestBody PrintableRequest request) throws PrintableNotFoundException {
        Response<PrintableResponse, PrintableError> response = new Response<>();
        User user = databaseUserManager.getByUsername(principal.getName());
        int userID = user.getUserId();
        if (request.getPrintableID() == 0 || !databasePrintableManager.checkPrintableId(request.getPrintableID())) {
            response.setSuccess(false);
            response.addError(PrintableError.PRINTABLE_NOT_EXISTING);
        }

        Printable printable = databasePrintableManager.getPrintableById(request.getPrintableID());
        if (printable == null) {
            response.setSuccess(false);
            response.addError(PrintableError.PRINTABLE_NOT_EXISTING);
        }
        if (!response.isSuccess()) {
            return response;
        }
        boolean isMine = request.getPrintableID() == userID;
        PrintableResponse printableResponse = printableToResponseConverter.from(printable, isMine);
        response.setSuccess(true);
        response.setPayload(printableResponse);
        return response;
    }

    /**
     * @param principal Principal
     * @param request   PrintableEditRequest
     * @return response Response<PrintableEditResponse, PrintableEditError>
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/printable/edit", method = RequestMethod.PUT)
    @ResponseBody
    public Response<PrintableEditResponse, PrintableEditError> editPrintable(Principal principal, @RequestBody PrintableEditRequest request) {
        Response<PrintableEditResponse, PrintableEditError> response = new Response<>();

        String userName = principal.getName();
        User user = databaseUserManager.getByUsername(userName);
        if (user.getUserId() == 0) {
            response.setSuccess(false);
            response.addError(PrintableEditError.USER_INVALID);
        }
        if (user.getUserId() != request.getPrintableID()) {
            response.setSuccess(false);
            response.addError(PrintableEditError.NO_AUTHORIZATION);
        }
        if (request.getPrintableID() == 0 || !databasePrintableManager.checkPrintableId(request.getPrintableID())) {
            response.setSuccess(false);
            response.addError(PrintableEditError.PRINTABLE_NOT_EXISTING);
        }
        request.setCategories(databasePrintableManager.checkCategories(request.getCategories()));
        if (request.getCategories().size() > 3) {
            response.setSuccess(false);
            response.addError(PrintableEditError.CATEGORIES_INVALID);
        }
        if (!databasePrintableManager.checkDescription(request.getDescription())) {
            response.setSuccess(false);
            response.addError(PrintableEditError.DESCRIPTION_INVALID);
        }
        if (!databasePrintableManager.checkTitle(request.getTitle())) {
            response.setSuccess(false);
            response.addError(PrintableEditError.TITLE_INVALID);
        }
        if (!response.isSuccess()) {
            return response;
        }
        databasePrintableManager.update(request);
        if (request.getCategories().size() > 0) {
            databaseCategoryManager.update(request);
            databasePrintableCategoryManager.update(request);
        }
        PrintableEditResponse printableEditResponse = PrintableEditRequestToResponseConverter.from(request);
        response.setPayload(printableEditResponse);
        return response;
    }
    /**
     * @param principal Principal
     * @param request   PrintableNewRequest
     * @return response Response<PrintableNewResponse, PrintableNewError>
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/printable/new", method = RequestMethod.POST)
    @ResponseBody
    public Response<PrintableNewResponse, PrintableNewError> createPrintable(Principal principal, @RequestBody PrintableNewRequest request) throws IOException {
        User user = databaseUserManager.getByUsername(principal.getName());
        Response<PrintableNewResponse, PrintableNewError> response = new Response<>();
        request.setCategories(databasePrintableManager.checkCategories(request.getCategories()));
        if (user.getUserId() == 0) {
            response.setSuccess(false);
            response.addError(PrintableNewError.USER_INVALID);
        }
        if (request.getCategories().size() < 3) {
            response.setSuccess(false);
            response.addError(PrintableNewError.CATEGORIES_INVALID);
        }
        if (!databasePrintableManager.checkTitle(request.getTitle())) {
            response.setSuccess(false);
            response.addError(PrintableNewError.TITLE_INVALID);
        }
        if (!databasePrintableManager.checkDescription((request.getDescription()))) {
            response.setSuccess(false);
            response.addError(PrintableNewError.DESCRIPTION_INVALID);
        }
//        if (request.getFile().isEmpty() || request.getFile().getBytes().length < 1) {
//            response.setSuccess(false);
//            response.addError(PrintableNewError.FILE_INVALID);
//        }
        if (!response.isSuccess()) {
            return response;
        }
        String path = databasePrintableManager.uploadFile(request.getFile());
 //       Printable printable = databasePrintableManager.createPrintable(request, user.getUserId(), path);

 //       PrintableNewResponse printableNewResponse = PrintableToPrintableNewResponseConverter.from(printable);
 //       response.setPayload(printableNewResponse);
        return response;
    }

    /**
     * @param printableID int
     * @return response Respones<PrintableDownloadResponse, PrintableDownloadError>
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/printable/download", method = RequestMethod.GET)
    @ResponseBody
    public Response<MultipartFile, PrintableDownloadError> downloadFile(@RequestParam(
            value = "id",
            defaultValue = "0",
            required = true
    ) int printableID) throws IOException {

        Response<MultipartFile, PrintableDownloadError> response = new Response<>();

        if (printableID == 0 || !databasePrintableManager.checkPrintableId(printableID)) {
            response.setSuccess(false);
            response.addError(PrintableDownloadError.PRINTABLE_ID_INVALID);
        }

        if (!response.isSuccess()) {
            return response;
        }

        MultipartFile multipartFile = databasePrintableManager.downloadFile(printableID);
        response.setPayload(multipartFile);
        return response;
    }
}
