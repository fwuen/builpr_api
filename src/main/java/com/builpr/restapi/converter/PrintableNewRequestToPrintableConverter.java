package com.builpr.restapi.converter;

import com.builpr.database.bridge.printable.Printable;
import com.builpr.database.bridge.printable.PrintableImpl;
import com.builpr.restapi.model.Request.Printable.PrintableNewRequest;


/**
 * @author Markus Goller
 *
 * Converter
 */
public class PrintableNewRequestToPrintableConverter {


    /**
     * Converts a PrintableNewRequest to a Printable
     *
     * @param request PrintableNewRequest
     * @param userID int
     * @param path String
     * @return Printable
     */
    public static Printable from(PrintableNewRequest request, int userID, String path) {
        PrintableImpl printable = new PrintableImpl();
        printable.setTitle(request.getTitle());
        printable.setDescription(request.getDescription());
        printable.setUploaderId(userID);
        printable.setFilePath(path);

        return printable;
    }
}
