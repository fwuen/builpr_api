package com.builpr.restapi.controller;

import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.user.User;
import com.builpr.database.service.DatabasePrintableManager;
import com.builpr.restapi.error.response.account.printable.PrintableDownloadError;
import com.builpr.restapi.error.response.account.printable.PrintableNewError;
import com.builpr.restapi.model.Request.Printable.PrintableNewRequest;
import com.builpr.restapi.model.Response.Response;
import com.builpr.restapi.model.Response.printable.PrintableNewResponse;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

/**
 *
 */
public class UploadController {
    private DatabasePrintableManager databasePrintableManager;

    public UploadController() {
        databasePrintableManager = new DatabasePrintableManager();
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
        Printable printable = databasePrintableManager.createPrintable(request, user.getUserId(), path);

        PrintableNewResponse printableNewResponse = PrintableToPrintableNewResponseConverter.from(printable);
        response.setPayload(printableNewResponse);
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
