package com.builpr.restapi.controller;

import com.builpr.Constants;
import com.builpr.database.bridge.category.Category;
import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.user.User;
import com.builpr.database.service.DatabaseCategoryManager;
import com.builpr.database.service.DatabasePrintableCategoryManager;
import com.builpr.database.service.DatabasePrintableManager;
import com.builpr.database.service.DatabaseUserManager;
import com.builpr.restapi.converter.*;
import com.builpr.restapi.error.printable.*;
import com.builpr.restapi.model.Request.Printable.PrintableDeleteRequest;
import com.builpr.restapi.model.Request.Printable.PrintableEditRequest;
import com.builpr.restapi.model.Request.Printable.PrintableNewRequest;
import com.builpr.restapi.model.Response.Response;
import com.builpr.restapi.model.Response.printable.PrintableDeleteResponse;
import com.builpr.restapi.model.Response.printable.PrintableEditResponse;
import com.builpr.restapi.model.Response.printable.PrintableNewResponse;
import com.builpr.restapi.model.Response.printable.PrintableResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import static com.builpr.Constants.*;

/**
 * PrintableController
 */
@RestController
public class PrintableController {

    private DatabasePrintableManager databasePrintableManager;
    private DatabaseCategoryManager databaseCategoryManager;
    private DatabasePrintableCategoryManager databasePrintableCategoryManager;
    private DatabaseUserManager databaseUserManager;


    public PrintableController() {
        databasePrintableManager = new DatabasePrintableManager();
        databaseCategoryManager = new DatabaseCategoryManager();
        databasePrintableCategoryManager = new DatabasePrintableCategoryManager();
        databaseUserManager = new DatabaseUserManager();
    }


    /**
     * @param printableID int
     * @return response Response<PrintableResponse>
     */
    @CrossOrigin(origins = SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = URL_GET_PRINTABLE, method = RequestMethod.GET)
    @ResponseBody
    public Response<PrintableResponse> getPrintable(@RequestParam(
            value = "id",
            defaultValue = "0"
    ) int printableID) {
        Response<PrintableResponse> response = new Response<>();
        boolean isMine = false;

        if (printableID == 0 || !databasePrintableManager.checkPrintableId(printableID)) {
            response.setSuccess(false);
            response.addError(PrintableError.INVALID_PRINTABLEID);
            return response;
        }

        Printable printable = databasePrintableManager.getPrintableById(printableID);

        PrintableResponse printableResponse = PrintableToResponseConverter.from(printable);

        response.setSuccess(true);
        response.setPayload(printableResponse);

        return response;
    }

    /**
     * @param principal Principal
     * @param request   PrintableNewRequest
     * @return response Response<PrintableNewResponse>
     */
    @CrossOrigin(origins = Constants.SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = Constants.URL_NEW_PRINTABLE, method = RequestMethod.POST)
    @ResponseBody
    public Response<PrintableNewResponse> createPrintable(
            Principal principal, @RequestBody PrintableNewRequest request) throws IOException {
        Response<PrintableNewResponse> response = new Response<>();
        User user = null;

        if (principal == null) {
            response.setSuccess(false);
            response.addError(PrintableNewError.USER_INVALID);
            return response;
        }
        if (!databaseUserManager.isPresent(principal.getName())) {
            response.setSuccess(false);
            response.addError(PrintableNewError.USER_INVALID);
        } else {
            user = databaseUserManager.getByUsername(principal.getName());
        }
        List<String> categoryList = databasePrintableManager.checkCategories(request.getCategories());
        if (categoryList.size() < 3) {
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
        if (request.getFile() == null) {
            response.setSuccess(false);
            response.addError(PrintableNewError.FILE_INVALID);
        }

        if (!response.isSuccess()) {
            return response;
        }
        int userID = 0;
        if (user != null) {
            userID = user.getUserId();
        }

        // UPLOADING FILE
        String path = databasePrintableManager.uploadFile(request.getFile());
        // CREATING PRINTABLE
        Printable printable = databasePrintableManager.createPrintable(request, userID, path);
        // UPDATE THE LIST OF CATEGORIES
        databaseCategoryManager.update(categoryList);
        // GETTING CATEGORIES
        List<Category> list = databaseCategoryManager.getCategoriesByList(categoryList);
        // CREATE CONNECTIONS BETWEEN PRINTABLE AND CATEGORIES
        databasePrintableCategoryManager.createCategories(list, printable.getPrintableId());


        PrintableNewResponse printableNewResponse = PrintableToPrintableNewResponseConverter.from(printable, list);
        response.setPayload(printableNewResponse);

        return response;
    }

    /**
     * @param printableID int
     * @return response Respones<PrintableDownloadResponse>
     */
    @CrossOrigin(origins = SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = URL_DOWNLOAD, method = RequestMethod.GET)
    @ResponseBody
    public Response<byte[]> downloadFile(@RequestParam(
            value = "id",
            defaultValue = "0"
    ) int printableID) throws IOException {

        Response<byte[]> response = new Response<>();

        if (printableID == 0 || !databasePrintableManager.checkPrintableId(printableID)) {
            response.setSuccess(false);
            response.addError(PrintableDownloadError.PRINTABLE_ID_INVALID);
        }

        if (!response.isSuccess()) {
            return response;
        }

        byte[] fileData = databasePrintableManager.downloadFile(printableID);
        databasePrintableManager.updateDownloads(printableID);

        response.setPayload(fileData);

        return response;
    }

    /**
     * @param principal Principal
     * @return response Response<PrintableDeleteResponse>
     */
    @CrossOrigin(origins = SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = URL_DELETE_PRINTABLE, method = RequestMethod.DELETE)
    @ResponseBody
    public Response<PrintableDeleteResponse> delete(Principal principal, @RequestParam(
            value = "id",
            defaultValue = "0"
    ) int printableID) {
        Response<PrintableDeleteResponse> response = new Response<>();

        if (printableID == 0 || !databasePrintableManager.checkPrintableId(printableID)) {
            response.setSuccess(false);
            response.addError(PrintableDeleteError.PRINTABLE_NOT_EXISTING);
            return response;
        }
        if (principal == null) {
            response.setSuccess(false);
            response.addError(PrintableDeleteError.USER_INVALID);
            return response;
        }
        if (databaseUserManager.getByUsername(principal.getName()) == null) {
            response.setSuccess(false);
            response.addError(PrintableDeleteError.USER_INVALID);
            return response;
        }
        if (databaseUserManager.getByUsername(principal.getName()).getUserId() != databasePrintableManager.getPrintableById(printableID).getUploaderId()) {
            response.setSuccess(false);
            response.addError(PrintableDeleteError.NO_AUTHORIZATION);
        }

        if (!response.isSuccess()) {
            return response;
        }
        response.setPayload(PrintableDeleteRequestToPrintableDeleteResponseConverter.from(databasePrintableManager.getPrintableById(printableID)));
        databasePrintableManager.deletePrintable(printableID);
        return response;
    }
}
