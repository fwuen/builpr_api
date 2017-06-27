package com.builpr.restapi.controller;

import com.builpr.Constants;
import com.builpr.database.bridge.category.Category;
import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.user.User;
import com.builpr.database.service.DatabaseCategoryManager;
import com.builpr.database.service.DatabasePrintableManager;
import com.builpr.database.service.DatabaseUserManager;
import com.builpr.restapi.converter.*;
import com.builpr.restapi.error.printable.*;
import com.builpr.restapi.model.Request.Printable.PrintableNewRequest;
import com.builpr.restapi.model.Response.Response;
import com.builpr.restapi.model.Response.printable.PrintableNewResponse;
import com.builpr.restapi.model.Response.printable.PrintablePayload;
import com.builpr.restapi.utils.PrintableCategoryHelper;
import com.builpr.restapi.utils.CategoryValidator;
import com.builpr.restapi.utils.PrintableDownloader;
import com.builpr.restapi.utils.PrintableUploader;
import org.springframework.web.bind.annotation.*;

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
    private DatabaseUserManager databaseUserManager;
    private PrintableDownloader printableDownloader;
    private PrintableUploader printableUploader;
    private CategoryValidator categoryValidator;
    private PrintableCategoryHelper printableCategoryHelper;

    public PrintableController() {
        databasePrintableManager = new DatabasePrintableManager();
        databaseCategoryManager = new DatabaseCategoryManager();
        databaseUserManager = new DatabaseUserManager();
        printableDownloader = new PrintableDownloader();
        printableUploader = new PrintableUploader();
        categoryValidator = new CategoryValidator();
        printableCategoryHelper = new PrintableCategoryHelper();
    }


    /**
     * @param printableID int
     * @return response Response<PrintableResponse>
     */
    @CrossOrigin(origins = SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = URL_GET_PRINTABLE, method = RequestMethod.GET)
    @ResponseBody
    public Response<PrintablePayload> getPrintable(@RequestParam(
            value = "id",
            defaultValue = "0"
    ) int printableID) {

        Response<PrintablePayload> response = new Response<>();

        Printable printable = databasePrintableManager.getPrintableById(printableID);
        if (printable == null) {
            response.setSuccess(false);
            response.addError(PrintableError.PRINTABLE_NOT_FOUND);
            return response;
        }

        PrintablePayload printablePayload = PrintableModelToPrintablePayloadConverter.from(printable);

        response.setSuccess(true);
        response.setPayload(printablePayload);

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

        if (!databaseUserManager.isPresent(principal.getName())) {
            response.setSuccess(false);
            response.addError(PrintableNewError.USER_INVALID);
            return response;
        }
        List<String> categoryList = categoryValidator.checkCategories(request.getCategories());
        if (categoryList.size() < 3) {
            response.setSuccess(false);
            response.addError(PrintableNewError.CATEGORIES_INVALID);
        }
        if (request.getTitle().length() < 5 || request.getTitle().length() > 100) {
            response.setSuccess(false);
            response.addError(PrintableNewError.TITLE_INVALID);
        }
        if (request.getDescription().length() > 1000) {
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

        User user = databaseUserManager.getByUsername(principal.getName());
        String path;
        try {
            // UPLOADING FILE
            path = printableUploader.uploadFile(request.getFile());
        } catch (Exception e) {
            response.setSuccess(false);
            response.addError(PrintableNewError.FAILED_UPLOAD);
            return response;
        }
        // CREATING PRINTABLE
        Printable printable = PrintableNewRequestToPrintableConverter.from(request, user.getUserId(), path);
        databasePrintableManager.persist(printable);
        // UPDATE THE LIST OF CATEGORIES
        databaseCategoryManager.update(categoryList);
        // GETTING CATEGORIES
        List<Category> list = databaseCategoryManager.getCategoriesByList(categoryList);
        // CREATE CONNECTIONS BETWEEN PRINTABLE AND CATEGORIES
        printableCategoryHelper.createCategories(list, printable.getPrintableId());



        PrintableNewResponse printableNewResponse = PrintableToPrintableNewResponseConverter.from(printable, list);
        response.setPayload(printableNewResponse);

        return response;
    }

//    /**
//     * @param principal Principal
//     * @param request   PrintableEditRequest
//     * @return response Response<PrintableEditResponse>
//     */
//    @CrossOrigin(origins = SECURITY_CROSS_ORIGIN)
//    @RequestMapping(value = URL_EDIT_PRINTABLE, method = RequestMethod.PUT)
//    @ResponseBody
//    public Response<PrintablePayload> editPrintable(Principal principal, @RequestBody PrintableEditRequest request) {
//        Response<PrintablePayload> response = new Response<>();
//
//
//        if (databasePrintableManager.getPrintableById(request.getPrintableID()) == null) {
//            response.setSuccess(false);
//            response.addError(PrintableEditError.PRINTABLE_NOT_EXISTING);
//        }
//
//        List<String> categories = categoryValidator.checkCategories(request.getCategories());
//        if (categories.size() < 3) {
//            response.setSuccess(false);
//            response.addError(PrintableEditError.CATEGORIES_INVALID);
//        }
//        if (request.getDescription().length() > 1000) {
//            response.setSuccess(false);
//            response.addError(PrintableEditError.DESCRIPTION_INVALID);
//        }
//        if (request.getTitle().length() < 5 || request.getTitle().length() > 100) {
//            response.setSuccess(false);
//            response.addError(PrintableEditError.TITLE_INVALID);
//        }
//
//        User user = databaseUserManager.getByUsername(principal.getName());
//        Printable printable = databasePrintableManager.getPrintableById(request.getPrintableID());
//        if (user != null) {
//            if (user.getUserId() == 0) {
//                response.setSuccess(false);
//                response.addError(PrintableEditError.USER_INVALID);
//            }
//            if (user.getUserId() != printable.getUploaderId()) {
//                response.setSuccess(false);
//                response.addError(PrintableEditError.NO_AUTHORIZATION);
//            }
//        }
//        if (!response.isSuccess()) {
//            return response;
//        }
//
//        // UPDATE the printable
//        printable.setDescription(request.getDescription());
//        printable.setTitle(request.getTitle());
//
//        databasePrintableManager.update(printable);
//
//        if (request.getCategories().size() > 0) {
//            // UPDATE the CATEGORY-table
//            databaseCategoryManager.update(categories);
//            // DELETE ALL PRINTABLE_CATEGORIES WITH PRINTABLE_ID
//            printableCategoryHelper.deleteCategoriesForPrintable(request.getPrintableID());
//            // GET CATEGORIES FOR PRINTABLE
//            List<Category> categoryList = databaseCategoryManager.getCategoriesByList(categories);
//            // CREATE NEW PRINTABLE_CATEGORIES
//            //  databasePrintableCategoryManager.createCategories(categoryList, request.getPrintableID());
//        }
//        PrintablePayload printablePayload = PrintableModelToPrintablePayloadConverter.from(printable);
//        response.setPayload(printablePayload);
//        return response;
//    }

    /**
     * @param printableID int
     * @return response Response<PrintableDownloadResponse>
     */
    @CrossOrigin(origins = SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = URL_DOWNLOAD, method = RequestMethod.GET)
    @ResponseBody
    public Response<byte[]> downloadFile(@RequestParam(
            value = "id",
            defaultValue = "0"
    ) int printableID) throws IOException {

        Response<byte[]> response = new Response<>();

        if (databasePrintableManager.getPrintableById(printableID) == null) {
            response.setSuccess(false);
            response.addError(PrintableDownloadError.PRINTABLE_ID_INVALID);
        }

        if (!response.isSuccess()) {
            return response;
        }

        Printable printable = databasePrintableManager.getPrintableById(printableID);
        byte[] fileData;
        try {
            fileData = printableDownloader.downloadFile(printable.getFilePath());
        } catch (IOException e) {
            response.setSuccess(false);
            response.addError(PrintableDownloadError.DOWNLOAD_FAILED);
            return response;
        }

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
    public Response<PrintablePayload> delete(Principal principal, @RequestParam(
            value = "id",
            defaultValue = "0"
    ) int printableID) {
        Response<PrintablePayload> response = new Response<>();

        if (databasePrintableManager.getPrintableById(printableID) == null) {
            response.setSuccess(false);
            response.addError(PrintableDeleteError.PRINTABLE_NOT_EXISTING);
            return response;
        }
        if (databaseUserManager.getByUsername(principal.getName()).getUserId() != databasePrintableManager.getPrintableById(printableID).getUploaderId()) {
            response.setSuccess(false);
            response.addError(PrintableDeleteError.NO_AUTHORIZATION);
        }

        if (!response.isSuccess()) {
            return response;
        }
        response.setPayload(PrintableModelToPrintablePayloadConverter.from(databasePrintableManager.getPrintableById(printableID)));
        databasePrintableManager.deletePrintable(printableID);
        return response;
    }
}
