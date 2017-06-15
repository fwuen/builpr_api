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
import com.builpr.restapi.error.exception.PrintableNotFoundException;
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

import static com.builpr.Constants.SECURITY_CROSS_ORIGIN;

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
     * @param principal   Principal
     * @param printableID int
     * @return response Response<PrintableResponse>
     * @throws PrintableNotFoundException Exception
     */
    @CrossOrigin(origins = SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = "/printable", method = RequestMethod.GET)
    @ResponseBody
    public Response<PrintableResponse> getPrintable(Principal principal, @RequestParam(
            value = "id",
            defaultValue = "0"
    ) int printableID) throws PrintableNotFoundException {
        Response<PrintableResponse> response = new Response<>();
        boolean isMine = false;

        if (printableID == 0 || !databasePrintableManager.checkPrintableId(printableID)) {
            response.setSuccess(false);
            response.addError(PrintableError.INVALID_PRINTABLEID);
            return response;
        }

        Printable printable = databasePrintableManager.getPrintableById(printableID);
        User user = databaseUserManager.getByUsername(principal.getName());
        if (user != null) {
            if (user.getUserId() == printable.getUploaderId()) {
                isMine = true;
            }
        }
        PrintableResponse printableResponse = PrintableToResponseConverter.from(printable, isMine);
        response.setSuccess(true);
        response.setPayload(printableResponse);
        return response;
    }

    /**
     * @param principal Principal
     * @param request   PrintableEditRequest
     * @return response Response<PrintableEditResponse>
     */
    @CrossOrigin(origins = SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = "/printable/edit", method = RequestMethod.PUT)
    @ResponseBody
    public Response<PrintableEditResponse> editPrintable(Principal principal, @RequestBody PrintableEditRequest request) {
        Response<PrintableEditResponse> response = new Response<>();


        if (request.getPrintableID() == 0 || !databasePrintableManager.checkPrintableId(request.getPrintableID())) {
            response.setSuccess(false);
            response.addError(PrintableEditError.PRINTABLE_NOT_EXISTING);
        }
        List<String> categories = databasePrintableManager.checkCategories(request.getCategories());

        if (categories.size() < 3) {
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

        User user = databaseUserManager.getByUsername(principal.getName());
        Printable printable = databasePrintableManager.getPrintableById(request.getPrintableID());
        if (user != null) {
            if (user.getUserId() == 0) {
                response.setSuccess(false);
                response.addError(PrintableEditError.USER_INVALID);
            }
            if (user.getUserId() != printable.getUploaderId()) {
                response.setSuccess(false);
                response.addError(PrintableEditError.NO_AUTHORIZATION);
            }
        }
        if (!response.isSuccess()) {
            return response;
        }

        // UPDATE the printable
        printable.setDescription(request.getDescription());
        printable.setTitle(request.getTitle());

        databasePrintableManager.update(printable);

        if (request.getCategories().size() > 0) {
            // UPDATE the CATEGORY-table
            databaseCategoryManager.update(categories);
            // DELETE ALL PRINTABLE_CATEGORIES WITH PRINTABLE_ID
            databasePrintableCategoryManager.deleteCategoriesForPrintable(request.getPrintableID());
            // GET CATEGORIES FOR PRINTABLE
            List<Category> categoryList = databaseCategoryManager.getCategoriesByList(categories);
            // CREATE NEW PRINTABLE_CATEGORIES
            databasePrintableCategoryManager.createCategories(categoryList, request.getPrintableID());
        }
        PrintableEditResponse printableEditResponse = PrintableEditRequestToResponseConverter.from(request);
        response.setPayload(printableEditResponse);
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
    public Response<PrintableNewResponse> createPrintable(Principal principal, @RequestBody PrintableNewRequest request) throws IOException {
        Response<PrintableNewResponse> response = new Response<>();
        User user = null;

        if (principal == null) {
            response.setSuccess(false);
            response.addError(PrintableNewError.USER_INVALID);
            return response;
        }
        if (databaseUserManager.isPresent(principal.getName())) {
            user = databaseUserManager.getByUsername(principal.getName());
            if (user.getUserId() == 0) {
                response.setSuccess(false);
                response.addError(PrintableNewError.USER_INVALID);
            }
        } else {
            response.setSuccess(false);
            response.addError(PrintableNewError.USER_INVALID);
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
        } else if (request.getFile().length < 1) {
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
        String path = databasePrintableManager.uploadFile(request.getFile());
        Printable printable = databasePrintableManager.createPrintable(request, userID, path);
        databaseCategoryManager.update(categoryList);
        List<Category> list = databaseCategoryManager.getCategoriesByList(categoryList);
        databasePrintableCategoryManager.createCategories(list, printable.getPrintableId());
        PrintableNewResponse printableNewResponse = PrintableToPrintableNewResponseConverter.from(printable);
        printableNewResponse.setCategories(CategoryToStringConverter.convertCategoriesToString(list));
        response.setPayload(printableNewResponse);
        return response;
    }

    /**
     * @param printableID int
     * @return response Respones<PrintableDownloadResponse>
     */
    @CrossOrigin(origins = SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = "/printable/download", method = RequestMethod.GET)
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

        MultipartFile multipartFile = databasePrintableManager.downloadFile(printableID);
        databasePrintableManager.updateDownloads(printableID);
        response.setPayload(multipartFile.getBytes());
        return response;
    }

    /**
     * @param principal Principal
     * @param request   PrintableDeleteRequest
     * @return response Response<PrintableDeleteResponse>
     */
    @CrossOrigin(origins = SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = "/printable/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public Response<PrintableDeleteResponse> delete(Principal principal, @RequestBody PrintableDeleteRequest request) {
        Response<PrintableDeleteResponse> response = new Response<>();

        if (request.getPrintableID() == 0 || !databasePrintableManager.checkPrintableId(request.getPrintableID())) {
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
        if (databaseUserManager.getByUsername(principal.getName()).getUserId() != databasePrintableManager.getPrintableById(request.getPrintableID()).getUploaderId()) {
            response.setSuccess(false);
            response.addError(PrintableDeleteError.NO_AUTHORIZATION);
        }

        if (!response.isSuccess()) {
            return response;
        }
        response.setPayload(PrintableDeleteRequestToPrintableDeleteResponseConverter.from(databasePrintableManager.getPrintableById(request.getPrintableID())));
        databasePrintableManager.deletePrintable(request.getPrintableID());
        return response;
    }
}
