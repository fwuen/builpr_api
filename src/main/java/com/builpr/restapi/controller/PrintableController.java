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
     * @return response Response<PrintableResponse, PrintableError>
     * @throws PrintableNotFoundException Exception
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/printable", method = RequestMethod.GET)
    @ResponseBody
    public Response<PrintableResponse, PrintableError> getPrintable(Principal principal, @RequestParam(
            value = "id",
            defaultValue = "0"
    ) int printableID) throws PrintableNotFoundException {
        Response<PrintableResponse, PrintableError> response = new Response<>();
        boolean isMine = false;

        if (printableID == 0 || !databasePrintableManager.checkPrintableId(printableID)) {
            response.setSuccess(false);
            response.addError(PrintableError.PRINTABLE_NOT_EXISTING);
            return response;
        }

        Printable printable = databasePrintableManager.getPrintableById(printableID);
        if (printable == null) {
            response.setSuccess(false);
            response.addError(PrintableError.PRINTABLE_NOT_EXISTING);
            return response;
        }
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
     * @return response Response<PrintableEditResponse, PrintableEditError>
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/printable/edit", method = RequestMethod.PUT)
    @ResponseBody
    public Response<PrintableEditResponse, PrintableEditError> editPrintable(Principal principal, @RequestBody PrintableEditRequest request) {
        Response<PrintableEditResponse, PrintableEditError> response = new Response<>();


        if (request.getPrintableID() == 0 || !databasePrintableManager.checkPrintableId(request.getPrintableID())) {
            response.setSuccess(false);
            response.addError(PrintableEditError.PRINTABLE_NOT_EXISTING);
        }
        request.setCategories(databasePrintableManager.checkCategories(request.getCategories()));
        if (request.getCategories().size() < 3) {
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
        databasePrintableManager.update(request);
        if (request.getCategories().size() > 0) {
            databaseCategoryManager.update(request.getCategories());
            databasePrintableCategoryManager.update(request);
        }
        PrintableEditResponse printableEditResponse = PrintableEditRequestToResponseConverter.from(request);
        response.setPayload(printableEditResponse);
        return response;
    }

    /**
     * @param principal Principal
     * @param request   PrintableNewRequest
     * @param file      MultipartFile
     * @return response Response<PrintableNewResponse, PrintableNewError>
     */
    @CrossOrigin(origins = Constants.SECURITY_CROSS_ORIGIN)
    @RequestMapping(value = Constants.URL_NEW_PRINTABLE, method = RequestMethod.POST)
    @ResponseBody
    public Response<PrintableNewResponse, PrintableNewError> createPrintable(Principal principal, @RequestBody MultipartFile file, @RequestBody PrintableNewRequest request) throws IOException {
        Response<PrintableNewResponse, PrintableNewError> response = new Response<>();
        User user = null;
//        Path p = FileSystems.getDefault().getPath("C:\\Users\\Markus\\Desktop\\Modells\\testFile.stl");
//        byte[] fileData = Files.readAllBytes(p);
//        file = new CustomMultipartFile(fileData, "C:\\Users\\Markus\\Desktop\\Modells\\testFile.stl");
        if (principal == null) {
            response.setSuccess(false);
            response.addError(PrintableNewError.NO_AUTHORIZATION);
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
        request.setCategories(databasePrintableManager.checkCategories(request.getCategories()));
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
        if (file == null) {
            response.setSuccess(false);
            response.addError(PrintableNewError.FILE_NOT_EXISTING);
        } else if (file.isEmpty() || file.getBytes().length < 1) {
            response.setSuccess(false);
            response.addError(PrintableNewError.FILE_INVALID);
        }
        if (!response.isSuccess()) {
            return response;
        }

        String path = databasePrintableManager.uploadFile(file);
        assert user != null;
        Printable printable = databasePrintableManager.createPrintable(request, user.getUserId(), path);
        databaseCategoryManager.update(request.getCategories());
        List<Category> list = databaseCategoryManager.getCategoriesByList(request.getCategories());
        databasePrintableCategoryManager.createCategories(list, printable.getPrintableId());
        PrintableNewResponse printableNewResponse = PrintableToPrintableNewResponseConverter.from(printable);
        printableNewResponse.setCategories(CategoryToStringConverter.convertCategoriesToString(list));
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
            defaultValue = "0"
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

    /**
     * @param principal Principal
     * @param request   PrintableDeleteRequest
     * @return response Response<PrintableDeleteResponse, PrintableDeleteError>
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @RequestMapping(value = "/printable/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public Response<PrintableDeleteResponse, PrintableDeleteError> delete(Principal principal, @RequestBody PrintableDeleteRequest request) {
        Response<PrintableDeleteResponse, PrintableDeleteError> response = new Response<>();

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
